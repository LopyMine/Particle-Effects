package net.lopymine.pe.client;

import org.slf4j.*;

import net.lopymine.pe.ParticleEffects;

public class ParticleEffectsClient {

	public static Logger LOGGER = LoggerFactory.getLogger(ParticleEffects.MOD_NAME + "/Client");

	public static void onInitializeClient() {
		LOGGER.info("{} Client Initialized", ParticleEffects.MOD_NAME);
	}

}
