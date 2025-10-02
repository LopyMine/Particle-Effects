package net.lopymine.pe.debug;

import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.particle.TexturedParticle;
import net.lopymine.pe.utils.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.NotNull;

public class DebugParticleInfoRenderer {

	public static void renderDebugInfo(MatrixStack matrices, Camera camera, float tickProgress, Iterable<? extends Particle> particles) {
		if (!ParticleEffects.getConfig().isDebugLogEnabled()) {
			return;
		}

		for (Particle particle : particles) {
			Vec3d vec3d = camera.getPos();
			float x = (float) (MathHelper.lerp(tickProgress, particle./*? if >=1.21.5 {*/ lastX /*?} else {*/ /*prevPosX *//*?}*/, particle.x) - vec3d.getX());
			float y = (float) (MathHelper.lerp(tickProgress, particle./*? if >=1.21.5 {*/ lastY /*?} else {*/ /*prevPosY *//*?}*/, particle.y) - vec3d.getY());
			float z = (float) (MathHelper.lerp(tickProgress, particle./*? if >=1.21.5 {*/ lastZ /*?} else {*/ /*prevPosZ *//*?}*/, particle.z) - vec3d.getZ());
			TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

			matrices.push();
			matrices.translate(x, y + 0.5D, z);
			//? if <=1.21.8 {
			matrices.multiply(camera.getRotation());
			matrices.scale(/*? if =1.20.1 {*//* - *//*?}*/0.015F, -0.015F, 0.015F);
			int opacity = (int) (MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;
			//?}

			String[] text = getDebugInfo(particle);

			int yOffset = 0;
			for (String line : text) {
				//? if <=1.21.8 {
				float f = (float) (-textRenderer.getWidth(line)) / 2.0F;
				//?}
				//? if >=1.21.9 {
				/*MinecraftClient.getInstance().worldRenderer.entityRenderDispatcher.getQueue().submitLabel(matrices, Vec3d.ZERO, -yOffset, Text.literal(line), true, LightmapTextureManager.MAX_LIGHT_COORDINATE, 10, MinecraftClient.getInstance().worldRenderer.worldRenderState.cameraRenderState);
				*///?} else {
				textRenderer.draw(line, f, (float) -yOffset, -1, false, matrices.peek().getPositionMatrix(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers(), net.minecraft.client.font.TextRenderer.TextLayerType.NORMAL, opacity, LightmapTextureManager.MAX_LIGHT_COORDINATE);
				//?}
				yOffset += textRenderer.fontHeight + 1;
			}

			matrices.pop();
		}
	}

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
			//? if <=1.21.8 {
			alpha = (int) (particle.alpha * 255);
			red   = (int) (particle.red * 255);
			green = (int) (particle.green * 255);
			blue  = (int) (particle.blue * 255);
			color = ArgbUtils.getArgb(alpha, red, green, blue);
			//?} else {
			/*if (particle instanceof BillboardParticle billboard) {
				alpha = (int) (billboard.alpha * 255);
				red   = (int) (billboard.red * 255);
				green = (int) (billboard.green * 255);
				blue  = (int) (billboard.blue * 255);
				color = ArgbUtils.getArgb(alpha, red, green, blue);
			} else {
				alpha = 255;
				red   = 255;
				green = 255;
				blue  = 255;
				color = -1;
			}
			*///?}
		}

		return new String[]{
				"Original Effect Color",
				"ARGB: " + color,
				"A: %s R: %s G: %s B: %s".formatted(alpha, red, green, blue),
				"Debug Data: " + ((PEDebugParticle) particle).particleEffects$getDebugData(),
		};

	}

}
