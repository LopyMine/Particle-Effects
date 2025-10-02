package net.lopymine.pe.mixin;

//? >=1.21.2 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import java.util.function.Function;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.pe.manager.ParticleEffectsManager;

import java.util.List;

@Debug(export = true)
@Mixin(WorldEventHandler.class)
public class WorldEventHandlerMixin {

	//? if >=1.21.9 {
	/*@Shadow
	@Final
	private ClientWorld world;
	*///?} else {
	@Shadow
	@Final
	private World world;
	//?}

	// SPLASH POTION
	@Inject(method = "processWorldEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;ofBottomCenter(Lnet/minecraft/util/math/Vec3i;)Lnet/minecraft/util/math/Vec3d;"))
	private void modifyParticleEffect(int eventId, BlockPos pos, int data, CallbackInfo ci, @Share("tp_effects") LocalRef<List<ParticleEffect>> localParticleEffects) {
		ParticleEffectsManager.processSplashPotionStageOne(localParticleEffects, data);
	}

	// SPLASH POTION
	//? if >=1.21.9 {
	/*@WrapOperation(method = "processWorldEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", ordinal = 4))
	private void swapParticles(ClientWorld instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original, @Share("tp_effects") LocalRef<List<ParticleEffect>> localParticleEffects, @Local(argsOnly = true, ordinal = 1) int color) {
		@SuppressWarnings("unused")
		Particle particle = ParticleEffectsManager.processSplashPotionStageTwo(
				this.world,
				parameters,
				(particleEffect) -> {
					original.call(instance, particleEffect, x, y, z, velocityX, velocityY, velocityZ);
					return null;
				},
				localParticleEffects,
				color
		);
	}
	*///?} else {
	@WrapOperation(method = "processWorldEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZDDDDDD)Lnet/minecraft/client/particle/Particle;", ordinal = 0))
	private Particle swapParticles(net.minecraft.client.render.WorldRenderer instance, ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Particle> original, @Share("tp_effects") LocalRef<List<ParticleEffect>> localParticleEffects, @Local(argsOnly = true, ordinal = 1) int color) {
		return ParticleEffectsManager.processSplashPotionStageTwo(
				this.world,
				parameters,
				(particleEffect) -> original.call(instance, particleEffect, alwaysSpawn, x, y, z, velocityX, velocityY, velocityZ),
				localParticleEffects,
				color
		);
	}
	//?}
}

//?}
