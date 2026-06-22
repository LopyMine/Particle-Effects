package net.lopymine.pe.entrypoint;

//? if neoforge {
/*import net.lopymine.pe.ParticleEffects;

import net.lopymine.pe.client.ParticleEffectsClient;
import net.lopymine.pe.modmenu.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.*;
import net.neoforged.fml.common.Mod;

@Mod(value = ParticleEffects.MOD_ID, dist = Dist.CLIENT)
public class PENeoForgeClientEntrypoint {

	public PENeoForgeClientEntrypoint(ModContainer container) {
		ParticleEffectsClient.onInitializeClient();
		PEModMenuIntegration integration = new PEModMenuIntegration();
		integration.register(container);
	}

}

*///?}

