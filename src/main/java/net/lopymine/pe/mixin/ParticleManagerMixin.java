package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.*;
import java.util.function.Predicate;
import net.lopymine.pe.capture.ParticleCaptures;
import net.lopymine.pe.debug.DebugParticleInfoRenderer;
import net.lopymine.pe.utils.*;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class ParticleManagerMixin {

	//? if >=1.21.9 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleProvider;createParticle(Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDDLnet/minecraft/util/RandomSource;)Lnet/minecraft/client/particle/Particle;"), method = "makeParticle")
	private Particle markParticle(ParticleProvider<?> instance, ParticleOptions t, ClientLevel clientWorld, double a, double b, double c, double d, double e, double v, RandomSource random, Operation<Particle> original) {
		Particle particle = original.call(instance, t, clientWorld, a, b, c, d, e, v, random);
	//?} else {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleProvider;createParticle(Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;"), method = "makeParticle")
	private Particle markParticle(ParticleProvider<?> instance, ParticleOptions particleOptions, ClientLevel clientLevel, double a, double b, double c, double d, double e, double v, Operation<Particle> original) {
		Particle particle = original.call(instance, particleOptions, clientLevel, a, b, c, d, e, v);
	*///?}
		Integer debugData = ParticleCaptures.getDebugData();
		if (debugData != null && particle != null) {
			((PEDebugParticle) particle).particleEffects$setDebugData(debugData);
		}
		return particle;
	}

}
