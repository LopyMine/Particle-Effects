package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.lopymine.pe.capture.ParticleCaptures;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.lopymine.pe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends Entity {

	public ArrowEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = /*? if >=1.21.5 {*/ /*"Lnet/minecraft/world/World;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V" *//*?} else {*/ "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V" /*?}*/), method = "spawnParticles")
	private void markParticleFromArrow(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original, @Local(ordinal = 1) int color) {
		Runnable originalCall = () -> original.call(instance, parameters, x, y, z, velocityX, velocityY, velocityZ);

		List<ParticleEffect> list = ParticleEffectsManager.getParticleEffects(ArgbUtils.getColorWithoutAlpha(color));
		if (list == null) {
			this.markDebugData(41, originalCall);
			return;
		}
		if (list.isEmpty()) {
			this.markDebugData(43, originalCall);
			return;
		}

		ParticleEffect particleEffect = ListUtils.getRandomElement(list, this.getWorld().getRandom());
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
