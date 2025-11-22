package net.lopymine.pe.mixin;

//? if >=1.21.9 {

import net.lopymine.pe.debug.DebugParticleInfoRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.ParticleGroupRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(QuadParticleGroup.class)
public abstract class BillboardParticleRendererMixin extends ParticleGroup<SingleQuadParticle> {

	public BillboardParticleRendererMixin(ParticleEngine particleManager) {
		super(particleManager);
	}

	@Inject(at = @At("HEAD"), method = "extractRenderState")
	private void renderDebugInfo(Frustum frustum, Camera camera, float tickProgress, CallbackInfoReturnable<ParticleGroupRenderState> cir) {
		DebugParticleInfoRenderer.renderDebugInfo(new PoseStack(), camera, tickProgress, this.particles);
	}

}
//?}
