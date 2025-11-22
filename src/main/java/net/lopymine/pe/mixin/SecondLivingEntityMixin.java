package net.lopymine.pe.mixin;

//? if !1.20.1 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.pe.capture.ParticleCaptures;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class SecondLivingEntityMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = /*? if >=1.21.5 {*/ "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V" /*?} else {*/
			/*"Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V" *//*?}*/), method = "tickEffects")
	private void markParticle(Level instance, ParticleOptions parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original) {
		ParticleCaptures.setParticle(parameters);
		original.call(instance, parameters, x, y, z, velocityX, velocityY, velocityZ);
		ParticleCaptures.setParticle(null);
	}
}
//?}
