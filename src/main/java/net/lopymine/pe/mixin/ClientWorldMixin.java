package net.lopymine.pe.mixin;

//? if >=1.21.9 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import java.util.function.Function;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;"), method = "addParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)V")
	private Particle swapParticle(ParticleManager instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Particle> original) {
		Function<ParticleEffect, Particle> function = (effect) -> original.call(instance, effect, x, y, z, velocityX, velocityY, velocityZ);
		return ParticleEffectsManager.swapParticle(((World) (Object) this), parameters, function, () -> function.apply(parameters));
	}

}
*///?}
