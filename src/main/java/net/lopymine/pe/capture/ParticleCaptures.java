package net.lopymine.pe.capture;

import net.minecraft.particle.ParticleEffect;
import org.jetbrains.annotations.Nullable;

public class ParticleCaptures {

	private final static ThreadLocal<ParticleEffect> MAIN = new ThreadLocal<>();

	public static void setParticle(ParticleEffect entity) {
		MAIN.set(entity);
	}

	@Nullable
	public static ParticleEffect getParticle() {
		return MAIN.get();
	}
}
