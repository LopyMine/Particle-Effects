package net.lopymine.pe.mixin.ip;

import net.lopymine.ip.element.controller.color.ColorController;
import net.lopymine.ip.element.mod.InventoryParticle;
import net.lopymine.ip.element.mod.spawner.ParticleSpawner;
import net.lopymine.ip.element.mod.spawner.context.ParticleSpawnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleSpawner.class)
public class ParticleSpawnerMixin {

	@Inject(at = @At("TAIL"), method = "setParticleColorController", remap = false)
	private void updateTextureRightNow(InventoryParticle particle, ParticleSpawnContext context, CallbackInfo ci) {
		ColorController<InventoryParticle> controller = particle.getColorController();
		if (controller == null) {
			return;
		}
		controller.tick(particle);
	}

}
