package net.lopymine.pe.mixin;
//? =1.20.1 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.pe.capture.ParticleCaptures;
import net.lopymine.pe.utils.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.pe.ParticleEffects;

import java.util.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow public abstract Map<MobEffect, MobEffectInstance> getActiveEffectsMap();

	@Shadow public abstract RandomSource getRandom();

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"), method = "tickEffects")
	private void swapParticle(Level instance, ParticleOptions parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original) {
		Runnable originalCall = () -> original.call(instance, parameters, x, y, z, velocityX, velocityY, velocityZ);
		if (!ParticleEffects.getConfig().isModEnabled()) {
			this.particleEffects$markDebugData(51, originalCall);
			return;
		}

		Set<MobEffect> effects = this.getActiveEffectsMap().keySet();
		if (!effects.isEmpty()) {
			MobEffect statusEffect = ListUtils.getRandomElement(effects.stream().toList(), this.getRandom());
			if (statusEffect == null) {
				this.particleEffects$markDebugData(52, originalCall);
				return;
			}

			int color = statusEffect.getColor();
			double red = ArgbUtils.getRed(color) / 255.0;
			double green = ArgbUtils.getGreen(color) / 255.0;
			double blue = ArgbUtils.getBlue(color) / 255.0;

			ParticleCaptures.setParticle(parameters);
			original.call(instance, parameters, x, y, z, red, green, blue);
			ParticleCaptures.setParticle(null);
		} else {
			ParticleCaptures.setParticle(parameters);
			originalCall.run();
			ParticleCaptures.setParticle(null);
		}
	}

	@Unique
	private void particleEffects$markDebugData(int data, Runnable originalCall) {
		ParticleCaptures.setDebugData(data);
		originalCall.run();
		ParticleCaptures.setDebugData(null);
	}

}
*///?}
