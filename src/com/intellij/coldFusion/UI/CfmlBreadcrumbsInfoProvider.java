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
package com.intellij.coldFusion.UI;

import org.jetbrains.annotations.NotNull;
import org.mustbe.consulo.RequiredReadAction;
import com.intellij.coldFusion.model.CfmlLanguage;
import com.intellij.coldFusion.model.psi.CfmlTag;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xml.breadcrumbs.BreadcrumbsInfoProvider;

/**
 * Created by Lera Nikolaenko
 * Date: 09.02.2009
 */
public class CfmlBreadcrumbsInfoProvider extends BreadcrumbsInfoProvider
{
	@NotNull
	@Override
	public Language getLanguage()
	{
		return CfmlLanguage.INSTANCE;
	}

	@RequiredReadAction
	public boolean acceptElement(@NotNull final PsiElement e)
	{
		return e instanceof CfmlTag /*|| e instanceof XmlTag*/;
	}

	@RequiredReadAction
	public PsiElement getParent(@NotNull final PsiElement e)
	{
		return e instanceof CfmlTag ? PsiTreeUtil.getParentOfType(e, CfmlTag.class) : null /*: PsiTreeUtil.getParentOfType(e, XmlTag.class)*/;
	}

	@RequiredReadAction
	@NotNull
	public String getElementInfo(@NotNull final PsiElement e)
	{
		String result = e instanceof CfmlTag ? ((CfmlTag) e).getTagName() : null/*: e instanceof XmlTag ? ((XmlTag) e).getName() : ""*/;
		return result != null ? result : "";
	}

	@RequiredReadAction
	public String getElementTooltip(@NotNull final PsiElement e)
	{
		return null;
	}
}

