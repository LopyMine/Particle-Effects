package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.*;
import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.capture.ParticleCaptures;
import net.lopymine.pe.debug.DebugParticleInfoRenderer;
import net.lopymine.pe.utils.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

	//? if =1.20.1 {
	/*@Shadow
	@Final
	private Map<ParticleTextureSheet, Queue<Particle>> particles;
	*///?}

	//? if >=1.21.4 && <=1.21.8 {
	@Inject(at = @At(value = "TAIL"), method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V")
	private static void renderDebugInfo(Camera camera, float tickProgress, Immediate vertexConsumers, ParticleTextureSheet sheet, Queue<Particle> particles, CallbackInfo ci) {
		DebugParticleInfoRenderer.renderDebugInfo(new MatrixStack(), camera, tickProgress, particles);
	}
	//?} elif >=1.21 && <=1.21.8 {
	/*@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferRenderer;drawWithGlobalProgram(Lnet/minecraft/client/render/BuiltBuffer;)V", shift = Shift.AFTER), method = "renderParticles")
	private void renderDebugInfo(LightmapTextureManager lightmapTextureManager, Camera camera, float tickDelta, CallbackInfo ci, @Local Queue<Particle> particles) {
		DebugParticleInfoRenderer.renderDebugInfo(new MatrixStack(), camera, tickDelta, particles);
	}
	*///?} elif <=1.21.8 {
	/*@Inject(at = @At(value = "TAIL"), method = "renderParticles")
	private void renderDebugInfo(MatrixStack matrices, Immediate vertexConsumers, LightmapTextureManager lightmapTextureManager, Camera camera, float tickDelta, CallbackInfo ci, @Local(ordinal = 1) MatrixStack stack) {
		for (Queue<Particle> value : this.particles.values()) {
			DebugParticleInfoRenderer.renderDebugInfo(matrices, camera, tickDelta, value);
		}
	}
	*///?}

	//? if >=1.21.9 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleFactory;createParticle(Lnet/minecraft/particle/ParticleEffect;Lnet/minecraft/client/world/ClientWorld;DDDDDDLnet/minecraft/util/math/random/Random;)Lnet/minecraft/client/particle/Particle;"), method = "createParticle")
	private Particle markParticle(ParticleFactory<?> instance, ParticleEffect t, ClientWorld clientWorld, double a, double b, double c, double d, double e, double v, Random random, Operation<Particle> original) {
		Particle particle = original.call(instance, t, clientWorld, a, b, c, d, e, v, random);
	*///?} else {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleFactory;createParticle(Lnet/minecraft/particle/ParticleEffect;Lnet/minecraft/client/world/ClientWorld;DDDDDD)Lnet/minecraft/client/particle/Particle;"), method = "createParticle")
	private Particle markParticle(ParticleFactory<?> instance, ParticleEffect t, ClientWorld clientWorld, double a, double b, double c, double d, double e, double v, Operation<Particle> original) {
		Particle particle = original.call(instance, t, clientWorld, a, b, c, d, e, v);
	//?}
		Integer debugData = ParticleCaptures.getDebugData();
		if (debugData != null && particle != null) {
			((PEDebugParticle) particle).particleEffects$setDebugData(debugData);
		}
		return particle;
	}

}
