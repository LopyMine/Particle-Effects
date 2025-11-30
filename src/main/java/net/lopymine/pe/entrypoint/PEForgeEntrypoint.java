package net.lopymine.pe.entrypoint;

//? if forge {
/*import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(ParticleEffects.MOD_ID)
public class PEForgeEntrypoint {

	public PEForgeEntrypoint() {
		ParticleEffects.onInitialize();
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ParticleEffectsManager.PARTICLES_REGISTER.register(eventBus);
		eventBus.addListener(ParticleEffectsManager::onCommonSetup);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			PEForgeClientEntrypoint.onInitializeClient(eventBus);
		});

		ParticleEffectsManager.registerParticleTypes();
	}

}

*///?}
