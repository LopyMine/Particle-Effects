package net.lopymine.pe.debug;

import java.util.Queue;
import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.mixin.ParticleEngineMixin;
import net.lopymine.pe.particle.TexturedParticle;
import net.lopymine.pe.utils.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.particle.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class DebugParticleInfoRenderer {

	public static void renderAll(PoseStack matrices, Vec3 camera, Quaternionf cameraRotation, float tickProgress) {
		//? if >=1.21.9 {
		for (ParticleGroup<?> value : ((ParticleEngineMixin) Minecraft.getInstance().particleEngine).getParticles().values()) {
			for (Particle particle : value.getAll()) {
				renderDebugInfo(matrices, camera, cameraRotation, tickProgress, particle);
			}
		}
		//?} else {
		/*for (Queue<Particle> value : ((ParticleEngineMixin) Minecraft.getInstance().particleEngine).getParticles().values()) {
			for (Particle particle : value) {
				renderDebugInfo(matrices, camera, cameraRotation, tickProgress, particle);
			}
		}
		*///?}
	}

	public static void renderDebugInfo(PoseStack matrices, Vec3 camera, Quaternionf cameraRotation, float tickProgress, Particle particle) {
		if (!ParticleEffects.getConfig().isDebugLogEnabled()) {
			return;
		}

		float x = (float) (Mth.lerp(tickProgress, particle.xo, particle.x) - camera.x());
		float y = (float) (Mth.lerp(tickProgress, particle.yo, particle.y) - camera.y());
		float z = (float) (Mth.lerp(tickProgress, particle.zo, particle.z) - camera.z());
		Font textRenderer = Minecraft.getInstance().font;

		matrices.pushPose();
		matrices.translate(x, y + 0.5D, z);
		//? if <=1.21.8 {
		/*matrices.mulPose(cameraRotation);
		matrices.scale(/^? if =1.20.1 {^/ /^- ^//^?}^/0.015F, -0.015F, 0.015F);
		*///?}

		String[] text = getDebugInfo(particle);

		int yOffset = 0;
		for (String line : text) {
			//? if >=1.21.9 {
			Minecraft.getInstance().levelRenderer.featureRenderDispatcher.getSubmitNodeStorage().submitNameTag(matrices, Vec3.ZERO, -yOffset, Component.literal(line), false, LightTexture.FULL_BRIGHT, 10, Minecraft.getInstance().levelRenderer.levelRenderState.cameraRenderState);
			//?} else {
			/*float f = (float) (-textRenderer.width(line)) / 2.0F;
			textRenderer.drawInBatch(line, f, (float) -yOffset, -1, false, matrices.last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT);
			*///?}
			yOffset += textRenderer.lineHeight + 1;
		}

		matrices.popPose();
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
			red   = (int) (particle.rCol * 255);
			green = (int) (particle.gCol * 255);
			blue  = (int) (particle.bCol * 255);
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
