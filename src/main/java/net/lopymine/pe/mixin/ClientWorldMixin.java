package net.lopymine.pe.mixin;

//? if >=1.21.9 {
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import java.util.function.Function;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientLevel.class)
public class ClientWorldMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleEngine;createParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)Lnet/minecraft/client/particle/Particle;"), method = "doAddParticle(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)V")
	private Particle swapParticle(ParticleEngine instance, ParticleOptions parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Particle> original) {
		Function<ParticleOptions, Particle> function = (effect) -> original.call(instance, effect, x, y, z, velocityX, velocityY, velocityZ);
		return ParticleEffectsManager.swapParticle(((Level) (Object) this), parameters, function, () -> function.apply(parameters));
	}

}
//?}
