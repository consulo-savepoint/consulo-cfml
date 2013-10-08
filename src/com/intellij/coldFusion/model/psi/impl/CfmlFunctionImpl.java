/*
 * Copyright 2000-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.coldFusion.model.psi.impl;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.coldFusion.UI.CfmlLookUpItemUtil;
import com.intellij.coldFusion.model.info.CfmlFunctionDescription;
import com.intellij.coldFusion.model.lexer.CfscriptTokenTypes;
import com.intellij.coldFusion.model.parsers.CfmlElementTypes;
import com.intellij.coldFusion.model.psi.CfmlComponentType;
import com.intellij.coldFusion.model.psi.CfmlCompositeElement;
import com.intellij.coldFusion.model.psi.CfmlFunction;
import com.intellij.coldFusion.model.psi.CfmlParameter;
import com.intellij.coldFusion.model.psi.CfmlParametersList;
import com.intellij.coldFusion.model.psi.CfmlPsiUtil;
import com.intellij.coldFusion.model.psi.CfmlRecursiveElementVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.CheckUtil;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;

/**
 * User: vnikolaenko
 * Date: 29.04.2009
 */
public class CfmlFunctionImpl extends CfmlCompositeElement implements CfmlFunction, PsiNameIdentifierOwner {
  public CfmlFunctionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public PsiElement setName(@NotNull @NonNls String name) throws IncorrectOperationException {
    CheckUtil.checkWritable(this);
    final PsiElement newElement = CfmlPsiUtil.createReferenceExpression(name, getProject());
    //noinspection ConstantConditions
    getNode().replaceChild(getReferenceElement().getNode(), newElement.getNode());
    return this;
  }

  @Nullable
  public PsiElement getReferenceElement() {
    return findChildByType(CfscriptTokenTypes.IDENTIFIER);
  }

  @NotNull
  public String getFunctionName() {
    PsiElement element = getReferenceElement();
    return element != null ? element.getText() : "";
  }

  @NotNull
  @Override
  public String getName() {
    return getFunctionName();
  }

  @NotNull
  @Override
  public PsiElement getNavigationElement() {
    PsiElement element = getReferenceElement();
    return element != null ? element : this;
  }

  @NotNull
  public String getParametersAsString() {
    return getFunctionInfo().getParametersListPresentableText();
  }

  @NotNull
  public CfmlParameter[] getParameters() {
    final CfmlParametersList parametersList = findChildByClass(CfmlParametersList.class);
    if (parametersList != null) {
      return parametersList.getParameters();
    }
    return CfmlFunctionParameterImpl.EMPTY_ARRAY;
  }

  @Nullable
  public CfmlParametersList getParametersList() {
    return findChildByClass(CfmlParametersList.class);
  }

  @Nullable
  public PsiType getReturnType() {
    final PsiElement type = findChildByType(CfmlElementTypes.TYPE);
    return type != null ?
           new CfmlComponentType(type.getText(), getContainingFile(), getProject()) : null;
  }

  @Override
  public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                     @NotNull ResolveState state,
                                     PsiElement lastParent,
                                     @NotNull PsiElement place) {
    processor.execute(this, state);
    final CfmlParametersList params = findChildByClass(CfmlParametersList.class);
    if (params != null) {
      if (!params.processDeclarations(processor, state, null, params)) {
        return false;
      }
    }
    // FUNCTIONBODY element contains all other declarations
    return true;
  }

  public PsiElement getNameIdentifier() {
    return getReferenceElement();
  }

  @Override
  public int getTextOffset() {
    final PsiElement element = getNavigationElement();
    return element.getTextRange().getStartOffset();
  }

  public boolean isTrulyDeclaration() {
    return true;
  }

  @NotNull
  public CfmlFunctionDescription getFunctionInfo() {
    return CfmlLookUpItemUtil.getFunctionDescription(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CfmlRecursiveElementVisitor) {
      ((CfmlRecursiveElementVisitor)visitor).visitCfmlFunction(this);
    }
    else {
      super.accept(visitor);
    }
  }
}
