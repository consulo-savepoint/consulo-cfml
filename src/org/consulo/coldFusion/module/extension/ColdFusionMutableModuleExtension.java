package org.consulo.coldFusion.module.extension;

import javax.swing.JComponent;

import org.consulo.module.extension.MutableModuleExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModifiableRootModel;

/**
 * @author VISTALL
 * @since 08.10.13.
 */
public class ColdFusionMutableModuleExtension extends ColdFusionModuleExtension implements MutableModuleExtension<ColdFusionModuleExtension>
{
	public ColdFusionMutableModuleExtension(@NotNull String id, @NotNull Module module)
	{
		super(id, module);
	}

	@Nullable
	@Override
	public JComponent createConfigurablePanel(@NotNull ModifiableRootModel modifiableRootModel, @Nullable Runnable runnable)
	{
		return null;
	}

	@Override
	public void setEnabled(boolean b)
	{
		myIsEnabled = b;
	}

	@Override
	public boolean isModified(@NotNull ColdFusionModuleExtension moduleExtension)
	{
		return moduleExtension.isEnabled() != isEnabled();
	}
}
