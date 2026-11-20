package net.lopymine.pe.compat;

import net.minecraft.resources.Identifier;

public class PotionParticleConfigs {

	public static boolean isPotionConfig(Identifier id, String modId) {
		if (!id.getNamespace().equals(modId)) {
			return false;
		}
		String path = id.getPath();
		return path.startsWith("iparticles/vanilla/potion/potion") || path.startsWith(modId + "/particles/vanilla/potion/potion");
	}
}
