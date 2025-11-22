package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.lopymine.pe.capture.ParticleCaptures;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.lopymine.pe.utils.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Arrow.class)
public abstract class ArrowEntityMixin extends Entity {

	public ArrowEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = /*? if >=1.21.5 {*/ "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V" /*?} else {*/ /*"Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V" *//*?}*/), method = "makeParticle")
	private void markParticleFromArrow(Level instance, ParticleOptions parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original, @Local(ordinal = 1) int color) {
		Runnable originalCall = () -> original.call(instance, parameters, x, y, z, velocityX, velocityY, velocityZ);

		List<ParticleOptions> list = ParticleEffectsManager.getParticleEffects(ArgbUtils.getColorWithoutAlpha(color));
		if (list == null) {
			this.markDebugData(41, originalCall);
			return;
		}
		if (list.isEmpty()) {
			this.markDebugData(43, originalCall);
			return;
		}

		ParticleOptions particleEffect = ListUtils.getRandomElement(list, this./*? if >=1.21.9 {*/ level /*?} else {*/ /*getWorld *//*?}*/().getRandom());
		if (particleEffect == null) {
			this.markDebugData(44, originalCall);
			return;
		}

		((PEType) particleEffect).particleEffects$setColor(color);
		ParticleCaptures.setParticle(particleEffect);
		original.call(instance, particleEffect, x, y, z, velocityX, velocityY, velocityZ);
		ParticleCaptures.setParticle(null);
	}

	@Unique
	private void markDebugData(int data, Runnable originalCall) {
		ParticleCaptures.setDebugData(data);
		originalCall.run();
		ParticleCaptures.setDebugData(null);
	}

}
