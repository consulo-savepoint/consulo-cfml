package org.consulo.coldFusion;

import org.jetbrains.annotations.NotNull;
import com.intellij.coldFusion.model.psi.CfmlPsiUtil;
import com.intellij.coldFusion.model.psi.CfmlTag;
import com.intellij.coldFusion.model.psi.impl.CfmlFunctionImpl;
import com.intellij.coldFusion.model.psi.impl.CfmlTagFunctionImpl;
import com.intellij.icons.AllIcons;
import com.intellij.ide.IconDescriptor;
import com.intellij.ide.IconDescriptorUpdater;
import com.intellij.psi.PsiElement;
import icons.CFMLIcons;

/**
 * @author VISTALL
 * @since 08.10.13.
 */
public class CfmlIconDescriptorUpdater implements IconDescriptorUpdater
{
	@Override
	public void updateIcon(@NotNull IconDescriptor iconDescriptor, @NotNull PsiElement element, int i)
	{
		if(element instanceof CfmlFunctionImpl)
		{
			iconDescriptor.setMainIcon(AllIcons.Nodes.Function);
		}
		else if(element instanceof CfmlTagFunctionImpl)
		{
			iconDescriptor.setMainIcon(AllIcons.Nodes.Function);

			String access = CfmlPsiUtil.getPureAttributeValue((CfmlTag) element, "access");
			if(access != null)
			{
				access = access.toLowerCase();
				if("private".equals(access))
				{
					iconDescriptor.setRightIcon(AllIcons.Nodes.C_private);
				}
				else if("package".equals(access))
				{
					iconDescriptor.setRightIcon(AllIcons.Nodes.C_plocal);
				}
				else if("public".equals(access))
				{
					iconDescriptor.setRightIcon(AllIcons.Nodes.C_public);
				}
				else if("remote".equals(access))
				{
					iconDescriptor.setRightIcon(CFMLIcons.Remote_access);
				}
			}
		}
	}
}
