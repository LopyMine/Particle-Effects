package net.lopymine.pe.utils;

import net.lopymine.pe.loader.MossyLoader;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.*;

//? if =1.20.1 {

/*import net.minecraft.world.item.alchemy.PotionUtils;
import java.util.Collection;

*///?}

public class StatusEffectUtils {

	public static void swapParticle(MobEffect statusEffect, ParticleOptions particleEffect) {
		((PEStatusEffect) statusEffect).particleEffects$setParticleEffect(particleEffect);
	}

	//? if =1.20.1 {

	/*// Yeah, I literally copied this thing from the original PotionUtils class to make this mod compatible with Alex Caves
	// IDK why, for what, just did
	public static int getColor(Collection<MobEffectInstance> collection) {
		if (!MossyLoader.isModLoaded("alexscaves", false)) {
			return PotionUtils.getColor(collection);
		}

		if (collection.isEmpty()) {
			return 3694022;
		} else {
			float f = 0.0F;
			float g = 0.0F;
			float h = 0.0F;
			int j = 0;

			for (MobEffectInstance mobEffectInstance : collection) {
				if (mobEffectInstance.isVisible()) {
					int k = mobEffectInstance.getEffect().getColor();
					int l = mobEffectInstance.getAmplifier() + 1;
					f += l * (k >> 16 & 0xFF) / 255.0F;
					g += l * (k >> 8 & 0xFF) / 255.0F;
					h += l * (k & 0xFF) / 255.0F;
					j += l;
				}
			}

			if (j == 0) {
				return 0;
			} else {
				f = f / j * 255.0F;
				g = g / j * 255.0F;
				h = h / j * 255.0F;
				return (int) f << 16 | (int) g << 8 | (int) h;
			}
		}
	}
	*///?}

}
