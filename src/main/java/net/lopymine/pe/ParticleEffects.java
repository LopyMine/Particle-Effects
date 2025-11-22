package net.lopymine.pe;

import lombok.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.*;

import net.fabricmc.api.ModInitializer;

import net.lopymine.pe.config.ParticleEffectsConfig;
import net.lopymine.pe.manager.ParticleEffectsManager;

public class ParticleEffects implements ModInitializer {

	public static final String MOD_NAME = /*$ mod_name*/ "Particle Effects";
	public static final String MOD_ID = /*$ mod_id*/ "particle-effects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static Component text(String path, Object... args) {
		return Component.translatable(String.format("%s.%s", MOD_ID, path), args);
	}

	@Setter
	@Getter
	private static ParticleEffectsConfig config;

	@Override
	public void onInitialize() {
		LOGGER.info("{} Initialized", MOD_NAME);
		ParticleEffects.config = ParticleEffectsConfig.getInstance();
		ParticleEffectsManager.onInitialize();
	}
}