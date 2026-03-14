package net.lopymine.pe.mixin.ip;

import net.lopymine.ip.config.particle.ParticleConfig;
import net.lopymine.ip.element.mod.InventoryParticle;
import net.lopymine.ip.element.mod.spawner.context.ParticleSpawnContext;
import net.lopymine.pe.compat.ip.IPotionParticleThing;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(InventoryParticle.class)
public class InventoryParticleMixin implements IPotionParticleThing {

	@Unique
	private boolean particleEffects$bl = false;

	@Inject(at = @At("RETURN"), method = "create", remap = false)
	private static void syncValue(ParticleConfig config, ParticleSpawnContext context, CallbackInfoReturnable<InventoryParticle> cir) {
		if (!(config instanceof IPotionParticleThing thing) || !thing.particleEffects$get()) {
			return;
		}
		InventoryParticle particle = cir.getReturnValue();
		((IPotionParticleThing) particle).particleEffects$set(true);
	}

	@Override
	public boolean particleEffects$get() {
		return this.particleEffects$bl;
	}

	@Override
	public void particleEffects$set(boolean bl) {
		this.particleEffects$bl = bl;
	}

}
