package org.consulo.coldFusion.module.extension;

import javax.swing.Icon;

import org.consulo.module.extension.ModuleExtensionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.module.Module;
import icons.CFMLIcons;

/**
 * @author VISTALL
 * @since 08.10.13.
 */
public class ColdFusionModuleExtensionProvider implements ModuleExtensionProvider<ColdFusionModuleExtension, ColdFusionMutableModuleExtension>
{
	@Nullable
	@Override
	public Icon getIcon()
	{
		return CFMLIcons.Cfml;
	}

	@NotNull
	@Override
	public String getName()
	{
		return "ColdFusion";
	}

	@NotNull
	@Override
	public ColdFusionModuleExtension createImmutable(@NotNull String s, @NotNull Module module)
	{
		return new ColdFusionModuleExtension(s, module);
	}

	@NotNull
	@Override
	public ColdFusionMutableModuleExtension createMutable(@NotNull String s, @NotNull Module module, @NotNull ColdFusionModuleExtension moduleExtension)
	{
		return new ColdFusionMutableModuleExtension(s, module, moduleExtension);
	}
}
