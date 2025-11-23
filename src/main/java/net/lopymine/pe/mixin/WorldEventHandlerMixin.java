package net.lopymine.pe.mixin;

//? >=1.21.2 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.pe.manager.ParticleEffectsManager;

import java.util.List;

@Debug(export = true)
@Mixin(LevelEventHandler.class)
public class WorldEventHandlerMixin {

	//? if >=1.21.9 {
	@Shadow
	@Final
	private ClientLevel level;
	//?} else {
	/*@Shadow
	@Final
	private Level level;
	*///?}

	// SPLASH POTION
	@Inject(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;atBottomCenterOf(Lnet/minecraft/core/Vec3i;)Lnet/minecraft/world/phys/Vec3;"))
	private void modifyParticleEffect(int eventId, BlockPos pos, int data, CallbackInfo ci, @Share("tp_effects") LocalRef<List<ParticleOptions>> localParticleEffects) {
		ParticleEffectsManager.processSplashPotionStageOne(localParticleEffects, data);
	}

	// SPLASH POTION
	//? if >=1.21.9 {
	@WrapOperation(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", ordinal = 4))
	private void swapParticles(ClientLevel instance, ParticleOptions parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original, @Share("tp_effects") LocalRef<List<ParticleOptions>> localParticleEffects, @Local(argsOnly = true, ordinal = 1) int color) {
		@SuppressWarnings("unused")
		Particle particle = ParticleEffectsManager.processSplashPotionStageTwo(
				this.level,
				parameters,
				(particleEffect) -> {
					original.call(instance, particleEffect, x, y, z, velocityX, velocityY, velocityZ);
					return null;
				},
				localParticleEffects,
				color
		);
	}
	//?} else {
	/*@WrapOperation(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;addParticleInternal(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)Lnet/minecraft/client/particle/Particle;", ordinal = 0))
	private Particle swapParticles(LevelRenderer instance, ParticleOptions parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Particle> original, @Share("tp_effects") LocalRef<List<ParticleOptions>> localParticleEffects, @Local(argsOnly = true, ordinal = 1) int color) {
		return ParticleEffectsManager.processSplashPotionStageTwo(
				this.level,
				parameters,
				(particleEffect) -> original.call(instance, particleEffect, alwaysSpawn, x, y, z, velocityX, velocityY, velocityZ),
				localParticleEffects,
				color
		);
	}
	*///?}
}

//?}
