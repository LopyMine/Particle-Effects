package net.lopymine.pe.capture;

import net.minecraft.particle.ParticleEffect;
import org.jetbrains.annotations.Nullable;

public class ParticleCaptures {

	private final static ThreadLocal<ParticleEffect> MAIN = new ThreadLocal<>();
	private final static ThreadLocal<Integer> DEBUG_DATA = new ThreadLocal<>();

	public static void setParticle(ParticleEffect entity) {
		MAIN.set(entity);
	}

	@Nullable
	public static ParticleEffect getParticle() {
		return MAIN.get();
	}

	public static void setDebugData(Integer data) {
		DEBUG_DATA.set(data);
	}

	public static Integer getDebugData() {
		return DEBUG_DATA.get();
	}
}
