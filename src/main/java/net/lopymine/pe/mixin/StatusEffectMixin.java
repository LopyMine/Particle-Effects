package net.lopymine.pe.mixin;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.*;

import net.lopymine.pe.utils.PEStatusEffect;

@Mixin(MobEffect.class)
public class StatusEffectMixin implements PEStatusEffect {

	@Unique
	private ParticleOptions particleEffect;

	@Override
	public void particleEffects$setParticleEffect(ParticleOptions particleEffect) {
		this.particleEffect = particleEffect;
	}

	public ParticleOptions particleEffects$getParticleEffect() {
		return this.particleEffect;
	}
}
