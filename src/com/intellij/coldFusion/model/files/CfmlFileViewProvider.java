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
package com.intellij.coldFusion.model.files;

import gnu.trove.THashSet;

import java.util.Arrays;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.language.sql.SQLLanguage;
import com.intellij.coldFusion.model.CfmlLanguage;
import com.intellij.coldFusion.model.parsers.CfmlElementTypes;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider;

/**
 * Created by Lera Nikolaenko
 * Date: 06.10.2008
 */
public class CfmlFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider implements TemplateLanguageFileViewProvider
{
	private static final THashSet<Language> ourRelevantLanguages = new THashSet<Language>(Arrays.asList(HTMLLanguage.INSTANCE,
			CfmlLanguage.INSTANCE, SQLLanguage.INSTANCE));


	public CfmlFileViewProvider(final PsiManager manager, final VirtualFile virtualFile, final boolean physical)
	{
		super(manager, virtualFile, physical);
	}

	@Override
	@NotNull
	public Language getBaseLanguage()
	{
		return CfmlLanguage.INSTANCE;
	}

	@Override
	@NotNull
	public Set<Language> getLanguages()
	{
		return ourRelevantLanguages;
	}

	@Override
	@Nullable
	protected PsiFile createFile(@NotNull final Language lang)
	{
		if(lang == getTemplateDataLanguage())
		{
			// final PsiFileImpl file = (PsiFileImpl)LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this);

			final PsiFileImpl file = (PsiFileImpl) LanguageParserDefinitions.INSTANCE.forLanguage(HTMLLanguage.INSTANCE).createFile(this);
			file.setContentElementType(CfmlElementTypes.TEMPLATE_DATA);
			return file;
		}
		if(lang == SQLLanguage.INSTANCE)
		{
			final PsiFileImpl file = (PsiFileImpl) LanguageParserDefinitions.INSTANCE.forLanguage(SQLLanguage.INSTANCE).createFile(this);
			file.setContentElementType(CfmlElementTypes.SQL_DATA);
			return file;
		}

		if(lang == getBaseLanguage())
		{
			return LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this);
		}
		return null;
	}

	@Override
	protected CfmlFileViewProvider cloneInner(final VirtualFile copy)
	{
		return new CfmlFileViewProvider(getManager(), copy, false);
	}

	@Override
	@NotNull
	public Language getTemplateDataLanguage()
	{
		return HTMLLanguage.INSTANCE;
	}
}

