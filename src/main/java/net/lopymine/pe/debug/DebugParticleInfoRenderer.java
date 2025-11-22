package net.lopymine.pe.debug;

import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.particle.TexturedParticle;
import net.lopymine.pe.utils.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.particle.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DebugParticleInfoRenderer {

	public static void renderDebugInfo(PoseStack matrices, Camera camera, float tickProgress, Iterable<? extends Particle> particles) {
		if (!ParticleEffects.getConfig().isDebugLogEnabled()) {
			return;
		}

		for (Particle particle : particles) {
			Vec3 vec3d = camera.getPosition();
			float x = (float) (Mth.lerp(tickProgress, particle./*? if >=1.21.5 {*/ xo /*?} else {*/ /*prevPosX *//*?}*/, particle.x) - vec3d.x());
			float y = (float) (Mth.lerp(tickProgress, particle./*? if >=1.21.5 {*/ yo /*?} else {*/ /*prevPosY *//*?}*/, particle.y) - vec3d.y());
			float z = (float) (Mth.lerp(tickProgress, particle./*? if >=1.21.5 {*/ zo /*?} else {*/ /*prevPosZ *//*?}*/, particle.z) - vec3d.z());
			Font textRenderer = Minecraft.getInstance().font;

			matrices.pushPose();
			matrices.translate(x, y + 0.5D, z);
			//? if <=1.21.8 {
			/*matrices.multiply(camera.getRotation());
			matrices.scale(/^? if =1.20.1 {^//^ - ^//^?}^/0.015F, -0.015F, 0.015F);
			int opacity = (int) (MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;
			*///?}

			String[] text = getDebugInfo(particle);

			int yOffset = 0;
			for (String line : text) {
				//? if <=1.21.8 {
				/*float f = (float) (-textRenderer.getWidth(line)) / 2.0F;
				*///?}
				//? if >=1.21.9 {
				Minecraft.getInstance().levelRenderer.featureRenderDispatcher.getSubmitNodeStorage().submitNameTag(matrices, Vec3.ZERO, -yOffset, Component.literal(line), true, LightTexture.FULL_BRIGHT, 10, Minecraft.getInstance().levelRenderer.levelRenderState.cameraRenderState);
				//?} else {
				/*textRenderer.draw(line, f, (float) -yOffset, -1, false, matrices.peek().getPositionMatrix(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers(), net.minecraft.client.font.TextRenderer.TextLayerType.NORMAL, opacity, LightmapTextureManager.MAX_LIGHT_COORDINATE);
				*///?}
				yOffset += textRenderer.lineHeight + 1;
			}

			matrices.popPose();
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
			/*alpha = (int) (particle.alpha * 255);
			red   = (int) (particle.red * 255);
			green = (int) (particle.green * 255);
			blue  = (int) (particle.blue * 255);
			color = ArgbUtils.getArgb(alpha, red, green, blue);
			*///?} else {
			if (particle instanceof SingleQuadParticle billboard) {
				alpha = (int) (billboard.alpha * 255);
				red   = (int) (billboard.rCol * 255);
				green = (int) (billboard.gCol * 255);
				blue  = (int) (billboard.bCol * 255);
				color = ArgbUtils.getArgb(alpha, red, green, blue);
			} else {
				alpha = 255;
				red   = 255;
				green = 255;
				blue  = 255;
				color = -1;
			}
			//?}
		}

		return new String[]{
				"Original Effect Color",
				"ARGB: " + color,
				"A: %s R: %s G: %s B: %s".formatted(alpha, red, green, blue),
				"Debug Data: " + ((PEDebugParticle) particle).particleEffects$getDebugData(),
		};

	}

}
