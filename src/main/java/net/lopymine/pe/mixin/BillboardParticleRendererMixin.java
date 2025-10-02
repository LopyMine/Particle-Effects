package net.lopymine.pe.mixin;

//? if >=1.21.9 {

/*import net.lopymine.pe.debug.DebugParticleInfoRenderer;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BillboardParticleRenderer.class)
public abstract class BillboardParticleRendererMixin extends ParticleRenderer<BillboardParticle> {

	public BillboardParticleRendererMixin(ParticleManager particleManager) {
		super(particleManager);
	}

	@Inject(at = @At("HEAD"), method = "render")
	private void renderDebugInfo(Frustum frustum, Camera camera, float tickProgress, CallbackInfoReturnable<Submittable> cir) {
		DebugParticleInfoRenderer.renderDebugInfo(new MatrixStack(), camera, tickProgress, this.particles);
	}

}
*///?}
