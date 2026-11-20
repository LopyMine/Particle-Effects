package net.lopymine.pe.mixin.itp;

//? if item-particles {

import net.lopymine.itp.config.particle.ParticleConfig;
import net.lopymine.pe.compat.IPotionParticleThing;
import org.spongepowered.asm.mixin.*;

@Mixin(ParticleConfig.class)
public class ParticleConfigMixin implements IPotionParticleThing {

	@Unique
	private boolean particleEffects$bl = false;

	@Override
	public boolean particleEffects$get() {
		return this.particleEffects$bl;
	}

	@Override
	public void particleEffects$set(boolean bl) {
		this.particleEffects$bl = bl;
	}
}
//?}
