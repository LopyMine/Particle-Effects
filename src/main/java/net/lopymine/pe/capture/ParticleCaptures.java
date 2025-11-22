package net.lopymine.pe.capture;

import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.Nullable;

public class ParticleCaptures {

	private final static ThreadLocal<ParticleOptions> MAIN = new ThreadLocal<>();
	private final static ThreadLocal<Integer> DEBUG_DATA = new ThreadLocal<>();

	public static void setParticle(ParticleOptions entity) {
		MAIN.set(entity);
	}

	@Nullable
	public static ParticleOptions getParticle() {
		return MAIN.get();
	}

	public static void setDebugData(Integer data) {
		DEBUG_DATA.set(data);
	}

	public static Integer getDebugData() {
		return DEBUG_DATA.get();
	}
}
