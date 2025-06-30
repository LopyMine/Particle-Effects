package net.lopymine.pe.mixin;

//? if !1.20.1 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.pe.capture.ParticleCaptures;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class SecondLivingEntityMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), method = "tickStatusEffects")
	private void markParticle(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original) {
		ParticleCaptures.setParticle(parameters);
		original.call(instance, parameters, x, y, z, velocityX, velocityY, velocityZ);
		ParticleCaptures.setParticle(null);
	}
}
//?}
