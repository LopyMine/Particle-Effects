package net.lopymine.pe.particle;

import lombok.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle./*? >=1.21 {*/SimpleParticleType/*?} else {*//*DefaultParticleType*//*?}*/;

import net.lopymine.pe.utils.*;
import net.minecraft.util.math.random.Random;

@Setter
@Getter
public class TexturedParticle extends SpellParticle {

	private int holderColor;

	protected TexturedParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider, Random random) {
		super(clientWorld, d, e, f, g, h, i, spriteProvider);
		super.setSprite(spriteProvider/*? if >=1.21.9 {*//*.getSprite(random) *//*?}*/);
	}

	@Override
	public void setColor(float red, float green, float blue) {

	}

	//? if <=1.21.8 {
	@Override
	public void setSpriteForAge(SpriteProvider spriteProvider) {

	}
	//?}

	@Override
	public void setAlpha(float alpha) {
		super.setAlpha(alpha);
	}

	@Override
	public void tick() {
		super.tick();
	}
}
