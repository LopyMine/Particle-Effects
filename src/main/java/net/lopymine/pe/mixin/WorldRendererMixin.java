package net.lopymine.pe.mixin;

//? if <=1.21.8 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;

import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import java.util.List;
import java.util.function.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.pe.manager.ParticleEffectsManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

	@Shadow
	@Nullable
	private ClientLevel level;

	//? <=1.21.1 {
	/^// SPLASH POTION
	@Inject(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;atBottomCenterOf(Lnet/minecraft/core/Vec3i;)Lnet/minecraft/world/phys/Vec3;"))
	private void modifyParticleEffect(int eventId, BlockPos pos, int data, CallbackInfo ci, @Share("tp_effects") LocalRef<List<ParticleOptions>> localParticleEffects) {
		ParticleEffectsManager.processSplashPotionStageOne(localParticleEffects, data);
	}

	// SPLASH POTION
	@WrapOperation(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;addParticleInternal(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)Lnet/minecraft/client/particle/Particle;", ordinal = 0))
	private Particle swapParticles(LevelRenderer instance, ParticleOptions parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Particle> original, @Share("tp_effects") LocalRef<List<ParticleOptions>> localParticleEffects, @Local(argsOnly = true, ordinal = 1) int color) {
		return ParticleEffectsManager.processSplashPotionStageTwo(
				this.level,
				parameters,
				(particleEffect) -> original.call(instance, particleEffect, alwaysSpawn, x, y, z, velocityX, velocityY, velocityZ),
				localParticleEffects,
				color);
	}
	^///?}


	// ENTITY PARTICLES
	@WrapOperation(method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;addParticleInternal(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;"))
	private Particle swapParticle(LevelRenderer instance, ParticleOptions particleOptions, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double vx, double vy, double vz, Operation<Particle> original) {
		Function<ParticleOptions, Particle> function = (effect) -> original.call(instance, effect, alwaysSpawn, canSpawnOnMinimal, x, y, z, vx, vy, vz);
		return ParticleEffectsManager.swapParticle(
				this.level,
				particleOptions,
				function,
				() -> function.apply(particleOptions)
				/^? if =1.20.1 {^//^, vx, vy, vz ^//^?}^/
		);
	}

}

*///?}
