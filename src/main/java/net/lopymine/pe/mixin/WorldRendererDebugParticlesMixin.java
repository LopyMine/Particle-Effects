package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.lopymine.pe.debug.DebugParticleInfoRenderer;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.9 {
import net.minecraft.client.renderer.state.LevelRenderState;
//?}

@Mixin(LevelRenderer.class)
public class WorldRendererDebugParticlesMixin {

	//? if =1.20.1 {
	/*@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V"
			),
			method = "renderLevel"
	)
	private void renderDebugParticles(CallbackInfo ci, @Local PoseStack stack, @Local(argsOnly = true) Camera camera, @Local(argsOnly = true) float delta) {
		DebugParticleInfoRenderer.renderAll(stack, camera.getPosition(), camera.rotation(), delta);
	}
	*///?}

	//? if >=1.21.9 {
	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V"
			),
			//? if fabric {
			method = "method_62214"
			//?} elif neoforge {
			/*method = "lambda$addMainPass$1"
			*///?}
	)
	private void renderDebugParticles(CallbackInfo ci, @Local PoseStack stack, @Local(argsOnly = true) LevelRenderState state) {
		DebugParticleInfoRenderer.renderAll(stack, state.cameraRenderState.pos, state.cameraRenderState.orientation, Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false));
	}
	//?} elif >=1.21 {
	/*@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V"
			),
			//? if <=1.21.1 {
			method = "renderLevel"
			//?} else {
			/^method = "method_62214"
			^///?}
	)
	private void renderDebugParticles(CallbackInfo ci, @Local PoseStack stack, @Local(argsOnly = true) Camera camera, @Local(argsOnly = true) DeltaTracker tracker) {
		DebugParticleInfoRenderer.renderAll(stack, camera.getPosition(), camera.rotation(), tracker.getGameTimeDeltaPartialTick(false));
	}
	*///?}

}
