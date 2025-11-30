package net.lopymine.pe.entrypoint;

//? if neoforge {
/*import net.lopymine.pe.ParticleEffects;

import net.lopymine.pe.manager.ParticleEffectsManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ParticleEffects.MOD_ID)
public class MossyNeoForgeEntrypoint {

	public MossyNeoForgeEntrypoint(IEventBus eventBus) {
		ParticleEffectsManager.PARTICLES_REGISTER.register(eventBus);
		eventBus.addListener(ParticleEffectsManager::onCommonSetup);
		eventBus.addListener(ParticleEffectsManager::registerParticleFactories);
		ParticleEffects.onInitialize();
		ParticleEffectsManager.registerParticleTypes();
	}

}

*///?}
