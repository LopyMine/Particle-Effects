package net.lopymine.pe.entrypoint;

//? if forge {

/*import net.lopymine.pe.client.ParticleEffectsClient;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.lopymine.pe.modmenu.PEModMenuIntegration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;

public class PEForgeClientEntrypoint {

	public static void onInitializeClient(IEventBus eventBus) {
		ParticleEffectsClient.onInitializeClient();
		eventBus.addListener(ParticleEffectsManager::onRegisterParticleProviders);
		PEModMenuIntegration integration = new PEModMenuIntegration();
		integration.register(ModLoadingContext.get().getActiveContainer());
	}

}

*///?}

