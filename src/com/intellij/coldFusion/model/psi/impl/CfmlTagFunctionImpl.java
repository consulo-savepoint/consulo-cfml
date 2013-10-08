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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.coldFusion.UI.CfmlLookUpItemUtil;
import com.intellij.coldFusion.model.info.CfmlFunctionDescription;
import com.intellij.coldFusion.model.psi.CfmlComponentType;
import com.intellij.coldFusion.model.psi.CfmlFunction;
import com.intellij.coldFusion.model.psi.CfmlParameter;
import com.intellij.coldFusion.model.psi.CfmlPsiUtil;
import com.intellij.coldFusion.model.psi.CfmlRecursiveElementVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiType;

/**
 * User: vnikolaenko
 * Date: 29.04.2009
 */
public class CfmlTagFunctionImpl extends CfmlNamedTagImpl implements CfmlFunction {
  public static final String TAG_NAME = "cffunction";

  public CfmlTagFunctionImpl(@NotNull ASTNode node) {
    super(node);
  }

  @NotNull
  public String getParametersAsString() {
    return getFunctionInfo().getParametersListPresentableText();
  }

  @NotNull
  public CfmlParameter[] getParameters() {
    return findChildrenByClass(CfmlParameter.class);
  }

  @Nullable
  public PsiType getReturnType() {
    final String returnTypeString = CfmlPsiUtil.getPureAttributeValue(this, "returntype");
    return returnTypeString != null ?
           new CfmlComponentType(returnTypeString, getContainingFile(), getProject()) : null;
  }

  public String getTagName() {
    return TAG_NAME;
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
