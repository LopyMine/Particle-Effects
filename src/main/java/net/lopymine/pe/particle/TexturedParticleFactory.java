package net.lopymine.pe.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle./*? >=1.21 {*/SimpleParticleType/*?} else {*//*DefaultParticleType*//*?}*/;

import net.lopymine.pe.utils.*;
import net.minecraft.util.math.random.Random;

public class TexturedParticleFactory implements ParticleFactory</*? >=1.21 {*/SimpleParticleType/*?} else {*//*DefaultParticleType*//*?}*/> {

	private final SpriteProvider spriteProvider;

	public TexturedParticleFactory(SpriteProvider spriteProvider) {
		this.spriteProvider = spriteProvider;
	}

	@Override
	public Particle createParticle(/*? >=1.21 {*/SimpleParticleType/*?} else {*//*DefaultParticleType*//*?}*/ effect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i/*? if >=1.21.9 {*//*, Random random *//*?}*/) {
		TexturedParticle texturedParticle = new TexturedParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider, /*? if >=1.21.9 {*//*random *//*?} else {*/ Random.create() /*?}*/);
		int color = ((PEType) effect).particleEffects$getColor();
		texturedParticle.setAlpha((float) ArgbUtils.getAlpha(color) / 255F);
		texturedParticle.setHolderColor(color);
		return texturedParticle;
	}
}
