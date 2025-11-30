package net.lopymine.pe.modmenu;

import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.modmenu.yacl.YACLConfigurationScreen;
import net.minecraft.client.gui.screens.Screen;

public class PEModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected String getModId() {
		return ParticleEffects.MOD_ID;
	}

	@Override
	protected Screen createConfigScreen(Screen parent) {
		return YACLConfigurationScreen.createScreen(parent);
	}
}

