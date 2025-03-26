package net.lopymine.pe.utils;

import net.minecraft.entity.effect.*;
import net.minecraft.particle.ParticleEffect;

import net.fabricmc.loader.api.FabricLoader;

import java.util.Collection;

public class StatusEffectUtils {

	public static void swapParticle(StatusEffect statusEffect, ParticleEffect particleEffect) {
		((PEStatusEffect) statusEffect).particleEffects$setParticleEffect(particleEffect);
	}

	//? if =1.20.1 {
	/*// Yeah, I literally copied this thing from the original PotionUtils class to make this mod compatible with Alex Caves
	// IDK why, for what, just did
	public static int getColor(Collection<StatusEffectInstance> effects) {
		if (!FabricLoader.getInstance().isModLoaded("alexscaves")) {
			return net.minecraft.potion.PotionUtil.getColor(effects);
		}

		if (effects.isEmpty()) {
			return 3694022;
		} else {
			float f = 0.0F;
			float g = 0.0F;
			float h = 0.0F;
			int j = 0;

			for (StatusEffectInstance statusEffectInstance : effects) {
				if (statusEffectInstance.shouldShowParticles()) {
					int k = statusEffectInstance.getEffectType().getColor();
					int l = statusEffectInstance.getAmplifier() + 1;
					f += (float) (l * (k >> 16 & 255)) / 255.0F;
					g += (float) (l * (k >> 8 & 255)) / 255.0F;
					h += (float) (l * (k & 255)) / 255.0F;
					j += l;
				}
			}

			if (j == 0) {
				return 0;
			} else {
				f = f / (float) j * 255.0F;
				g = g / (float) j * 255.0F;
				h = h / (float) j * 255.0F;
				return (int) f << 16 | (int) g << 8 | (int) h;
			}
		}
	}
	*///?}

}
