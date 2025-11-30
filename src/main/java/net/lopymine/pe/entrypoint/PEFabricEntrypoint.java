package net.lopymine.pe.entrypoint;

//? if fabric {

import net.lopymine.pe.ParticleEffects;

import net.fabricmc.api.ModInitializer;

public class PEFabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		ParticleEffects.onInitialize();
	}
}

//?}
