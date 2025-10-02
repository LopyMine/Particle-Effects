package net.lopymine.pe.mixin;

import net.lopymine.pe.utils.*;
import net.minecraft.particle./*? >=1.21 {*/SimpleParticleType/*?} else {*//*DefaultParticleType*//*?}*/;
import org.spongepowered.asm.mixin.*;

@Mixin(/*? >=1.21 {*/SimpleParticleType/*?} else {*//*DefaultParticleType*//*?}*/.class)
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
