package net.lopymine.pe.mixin;

import net.lopymine.pe.utils.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.spongepowered.asm.mixin.*;

@Mixin(SimpleParticleType.class)
public class SimpleParticleTypeMixin implements PEType {

	@Unique
	private int color;

	@Override
	public int particleEffects$getColor() {
		return this.color;
	}

	@Override
	public void particleEffects$setColor(int color) {
		// alpha not supported at <1.21
		//? if <1.21 {
		/*this.color = ArgbUtils.getArgb(255, ArgbUtils.getRed(color), ArgbUtils.getGreen(color), ArgbUtils.getBlue(color));
		*///?} else {
		this.color = color;
		//?}
	}
}
