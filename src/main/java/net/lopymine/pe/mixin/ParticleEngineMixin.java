package net.lopymine.pe.mixin;

import java.util.*;
import net.minecraft.client.particle.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ParticleEngine.class)
public interface ParticleEngineMixin {

	//? if <=1.21.8 {
	/*@Accessor("particles")
	Map<ParticleRenderType, Queue<Particle>> getParticles();
	*///?} else {
	@Accessor("particles")
	Map<ParticleRenderType, ParticleGroup<?>> getParticles();
	//?}

}
