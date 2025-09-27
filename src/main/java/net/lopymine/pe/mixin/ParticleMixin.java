package net.lopymine.pe.mixin;

import net.lopymine.pe.utils.PEDebugParticle;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.*;

@Mixin(Particle.class)
public class ParticleMixin implements PEDebugParticle {

	@Unique
	private int debugData = 0;

	@Override
	public void particleEffects$setDebugData(int i) {
		this.debugData = i;
	}

	@Override
	public int particleEffects$getDebugData() {
		return this.debugData;
	}
}
