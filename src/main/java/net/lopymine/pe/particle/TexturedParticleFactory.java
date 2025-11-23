package net.lopymine.pe.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.lopymine.pe.utils.*;
import net.minecraft.util.RandomSource;

public class TexturedParticleFactory implements ParticleProvider<SimpleParticleType> {

	private final SpriteSet spriteProvider;

	public TexturedParticleFactory(SpriteSet spriteProvider) {
		this.spriteProvider = spriteProvider;
	}

	@Override
	public Particle createParticle(SimpleParticleType effect, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i/*? if >=1.21.9 {*/, RandomSource random /*?}*/) {
		TexturedParticle texturedParticle = new TexturedParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider, /*? if >=1.21.9 {*/random /*?} else {*/ /*RandomSource.create() *//*?}*/);
		int color = ((PEType) effect).particleEffects$getColor();
		texturedParticle.setAlpha((float) ArgbUtils.getAlpha(color) / 255F);
		texturedParticle.setHolderColor(color);
		return texturedParticle;
	}
}
