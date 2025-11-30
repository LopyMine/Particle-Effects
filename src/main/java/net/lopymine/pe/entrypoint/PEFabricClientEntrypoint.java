package net.lopymine.pe.entrypoint;

//? if fabric {

import net.fabricmc.api.ClientModInitializer;
import net.lopymine.pe.client.ParticleEffectsClient;
import net.lopymine.pe.manager.ParticleEffectsManager;

public class PEFabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ParticleEffectsClient.onInitializeClient();
		ParticleEffectsManager.registerParticleTypes();
		ParticleEffectsManager.swapParticleTypes();
		ParticleEffectsManager.registerParticleColorsForTypes();
		ParticleEffectsManager.registerParticleFactories();
	}
}

//?}
