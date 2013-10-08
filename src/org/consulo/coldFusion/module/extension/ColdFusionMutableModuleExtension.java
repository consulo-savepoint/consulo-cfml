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
	private ColdFusionModuleExtension myModuleExtension;

	public ColdFusionMutableModuleExtension(@NotNull String id, @NotNull Module module, @NotNull ColdFusionModuleExtension moduleExtension)
	{
		super(id, module);
		myModuleExtension = moduleExtension;
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
	public boolean isModified()
	{
		return myModuleExtension.isEnabled() != isEnabled();
	}

	@Override
	public void commit()
	{
		myModuleExtension.commit(this);
	}
}
