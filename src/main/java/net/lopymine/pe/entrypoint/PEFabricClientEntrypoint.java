package net.lopymine.pe.entrypoint;

//? if fabric {

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.lopymine.pe.client.ParticleEffectsClient;
import net.lopymine.pe.compat.LoadedMods;
import net.lopymine.pe.manager.ParticleEffectsManager;

public class PEFabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ParticleEffectsClient.onInitializeClient();
		ParticleEffectsManager.registerParticleTypes();
		ParticleEffectsManager.swapParticleTypes();
		if (LoadedMods.isAnyOldPotionsModLoaded()) {
			ClientLifecycleEvents.CLIENT_STARTED.register((mc) -> {
				ParticleEffectsManager.redirectEnabled = true;
				ParticleEffectsManager.redirectToVanillaEffectColors = true;
				ParticleEffectsManager.registerParticleColorsForTypes();
				ParticleEffectsManager.redirectToVanillaEffectColors = false;
				ParticleEffectsManager.registerParticleColorsForTypes();
				ParticleEffectsManager.redirectEnabled = false;
			});
		} else {
			ParticleEffectsManager.registerParticleColorsForTypes();
		}
		ParticleEffectsManager.registerParticleFactories();
	}

}

//?}
