package net.lopymine.pe.mixin;

//? if <=1.21.8 {
import com.llamalad7.mixinextras.injector.wrapoperation.*;

import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import java.util.function.*;
import net.lopymine.pe.capture.ParticleCaptures;
import net.lopymine.pe.client.ParticleEffectsClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.*;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.lopymine.pe.utils.*;
import java.util.List;
import org.jetbrains.annotations.Nullable;

//? >=1.21
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? =1.20.1
/*import net.minecraft.util.math.ColorHelper.Argb;*/

@Debug(export = true)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	@Shadow
	@Nullable
	private ClientWorld world;

	//? <=1.21.1 {
	/*// SPLASH POTION
	@Inject(method = "processWorldEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;ofBottomCenter(Lnet/minecraft/util/math/Vec3i;)Lnet/minecraft/util/math/Vec3d;"))
	private void modifyParticleEffect(int eventId, BlockPos pos, int data, CallbackInfo ci, @Share("tp_effects") LocalRef<List<ParticleEffect>> localParticleEffects) {
		ParticleEffectsManager.processSplashPotionStageOne(localParticleEffects, data);
	}

	// SPLASH POTION
	@WrapOperation(method = "processWorldEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZDDDDDD)Lnet/minecraft/client/particle/Particle;", ordinal = 0))
	private Particle swapParticles(WorldRenderer instance, ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Particle> original, @Share("tp_effects") LocalRef<List<ParticleEffect>> localParticleEffects, @Local(argsOnly = true, ordinal = 1) int color) {
		return ParticleEffectsManager.processSplashPotionStageTwo(
				this.world,
				parameters,
				(particleEffect) -> original.call(instance, particleEffect, alwaysSpawn, x, y, z, velocityX, velocityY, velocityZ),
				localParticleEffects,
				color);
	}
	*///?}


	// ENTITY PARTICLES
	@WrapOperation(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;"))
	private Particle swapParticle(WorldRenderer instance, ParticleEffect parameters, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Particle> original) {
		Function<ParticleEffect, Particle> function = (effect) -> original.call(instance, effect, alwaysSpawn, canSpawnOnMinimal, x, y, z, velocityX, velocityY, velocityZ);
		return ParticleEffectsManager.swapParticle(
				this.world,
				parameters,
				function,
				() -> function.apply(parameters)
				/*? if =1.20.1 {*/, velocityX, velocityY, velocityZ /*?}*/
		);
	}

}

//?}
