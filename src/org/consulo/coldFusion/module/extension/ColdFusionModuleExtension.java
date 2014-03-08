package org.consulo.coldFusion.module.extension;

import org.consulo.module.extension.impl.ModuleExtensionImpl;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.roots.ModifiableRootModel;

/**
 * @author VISTALL
 * @since 08.10.13.
 */
public class ColdFusionModuleExtension extends ModuleExtensionImpl<ColdFusionModuleExtension>
{
	public ColdFusionModuleExtension(@NotNull String id, @NotNull ModifiableRootModel module)
	{
		super(id, module);
	}
}
