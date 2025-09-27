package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.*;
import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.capture.ParticleCaptures;
import net.lopymine.pe.particle.TexturedParticle;
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
import net.minecraft.util.math.*;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

	@Shadow @Final private Map<ParticleTextureSheet, Queue<Particle>> particles;

	//? if >=1.21.4 {
	/*@Inject(at = @At(value = "TAIL"), method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V")
	private static void renderDebugInfo(Camera camera, float tickProgress, Immediate vertexConsumers, ParticleTextureSheet sheet, Queue<Particle> particles, CallbackInfo ci) {
		renderDebugInfo(new MatrixStack(), camera, tickProgress, particles);
	}
	*///?} elif >=1.21 {
	/*@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferRenderer;drawWithGlobalProgram(Lnet/minecraft/client/render/BuiltBuffer;)V", shift = Shift.AFTER), method = "renderParticles")
	private void renderDebugInfo(LightmapTextureManager lightmapTextureManager, Camera camera, float tickDelta, CallbackInfo ci, @Local Queue<Particle> particles) {
		renderDebugInfo(new MatrixStack(), camera, tickDelta, particles);
	}
	*///?} else {
	@Inject(at = @At(value = "TAIL"), method = "renderParticles")
	private void renderDebugInfo(MatrixStack matrices, Immediate vertexConsumers, LightmapTextureManager lightmapTextureManager, Camera camera, float tickDelta, CallbackInfo ci, @Local(ordinal = 1) MatrixStack stack) {
		for (Queue<Particle> value : this.particles.values()) {
			renderDebugInfo(matrices, camera, tickDelta, value);
		}
	}
	//?}

	@Unique
	private static void renderDebugInfo(MatrixStack matrices, Camera camera, float tickProgress, Iterable<Particle> particles) {
		if (!ParticleEffects.getConfig().isDebugLogEnabled()) {
			return;
		}

		for (Particle particle : particles) {
			Vec3d vec3d = camera.getPos();
			float x = (float) (MathHelper.lerp(tickProgress, particle./*? if >=1.21.5 {*/ /*lastX *//*?} else {*/ prevPosX /*?}*/, particle.x) - vec3d.getX());
			float y = (float) (MathHelper.lerp(tickProgress, particle./*? if >=1.21.5 {*/ /*lastY *//*?} else {*/ prevPosY /*?}*/, particle.y) - vec3d.getY());
			float z = (float) (MathHelper.lerp(tickProgress, particle./*? if >=1.21.5 {*/ /*lastZ *//*?} else {*/ prevPosZ /*?}*/, particle.z) - vec3d.getZ());

			matrices.push();
			matrices.translate(x, y + 0.5D, z);
			matrices.multiply(camera.getRotation());
			matrices.scale(-0.015F, -0.015F, 0.015F);
			Matrix4f matrix4f = matrices.peek().getPositionMatrix();
			TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
			int opacity = (int) (MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;

			String[] text = getDebugInfo(particle);

			int yOffset = 0;
			for (String string : text) {
				float f = (float) (-textRenderer.getWidth(string)) / 2.0F;
				Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
				textRenderer.draw(string, f, (float) -yOffset, -1, false, matrix4f, immediate, TextLayerType.NORMAL, opacity, LightmapTextureManager.MAX_LIGHT_COORDINATE);
				yOffset += textRenderer.fontHeight + 1;
			}

			matrices.pop();
		}
	}

	@Unique
	private static String @NotNull [] getDebugInfo(Particle particle) {
		int color;
		int alpha;
		int red;
		int green;
		int blue;

		if (particle instanceof TexturedParticle texturedParticle) {
			color = texturedParticle.getHolderColor();
			alpha = ArgbUtils.getAlpha(color);
			red   = ArgbUtils.getRed(color);
			green = ArgbUtils.getGreen(color);
			blue  = ArgbUtils.getBlue(color);
		} else {
			alpha = (int) (particle.alpha * 255);
			red   = (int) (particle.red * 255);
			green = (int) (particle.green * 255);
			blue  = (int) (particle.blue * 255);
			color = ArgbUtils.getArgb(alpha, red, green, blue);
		}

		return new String[]{
				"Original Effect Color",
				"ARGB: " + color,
				"A: %s R: %s G: %s B: %s".formatted(alpha, red, green, blue),
				"Debug Data: " + ((PEDebugParticle) particle).particleEffects$getDebugData(),
		};
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleFactory;createParticle(Lnet/minecraft/particle/ParticleEffect;Lnet/minecraft/client/world/ClientWorld;DDDDDD)Lnet/minecraft/client/particle/Particle;"), method = "createParticle")
	private Particle markParticle(ParticleFactory<?> instance, ParticleEffect t, ClientWorld clientWorld, double a, double b, double c, double d, double e, double v, Operation<Particle> original) {
		Particle particle = original.call(instance, t, clientWorld, a, b, c, d, e, v);
		Integer debugData = ParticleCaptures.getDebugData();
		if (debugData != null) {
			((PEDebugParticle) particle).particleEffects$setDebugData(debugData);
		}
		return particle;
	}

}
