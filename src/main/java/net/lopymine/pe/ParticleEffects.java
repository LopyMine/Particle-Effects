package net.lopymine.pe;

import lombok.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.*;

import net.lopymine.pe.config.ParticleEffectsConfig;
import net.lopymine.pe.manager.ParticleEffectsManager;

public class ParticleEffects  {

	public static final String MOD_NAME = /*$ mod_name*/ "Particle Effects";
	public static final String MOD_ID = /*$ mod_id*/ "particle_effects";
	public static final String YACL_DEPEND_VERSION = /*$ yacl*/ "3.8.0+1.21.9-fabric";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation id(String path) {
		//? if >=1.21 {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
		//?} else {
		/*return ResourceLocation.tryBuild(MOD_ID, path);
		 *///?}
	}

	public static MutableComponent text(String path, Object... args) {
		return Component.translatable(String.format("%s.%s", MOD_ID, path), args);
	}

	@Setter
	@Getter
	private static ParticleEffectsConfig config;

	public static void onInitialize() {
		LOGGER.info("{} Initialized", MOD_NAME);
		ParticleEffects.config = ParticleEffectsConfig.getInstance();
	}
}