package net.lopymine.pe.compat.ip;

import lombok.*;
import net.lopymine.ip.element.texture.ITexture;
import net.lopymine.ip.element.texture.provider.*;
import net.minecraft.util.RandomSource;

@Getter
@Setter
public class DynamicPotionTextureProvider extends RandomStaticTextureProvider {

	private final OneTimeTextureProvider oneTimeTextureProvider;
	private boolean enabled;

	public DynamicPotionTextureProvider(OneTimeTextureProvider provider) {
		super(provider.getTextures(), provider.getAnimationSpeed(), provider.getLifeTime());
		this.oneTimeTextureProvider = provider;
		this.setTicks(provider.getTicks());
	}

	@Override
	protected ITexture getTextureFromNotEmptyTextures(RandomSource random) {
		if (!this.enabled) {
			this.oneTimeTextureProvider.setTicks(this.getTicks());
			return this.oneTimeTextureProvider.getTexture(random);
		}
		return super.getTextureFromNotEmptyTextures(random);
	}

	@Override
	protected ITexture getInitializationTextureFromNotEmptyTextures(RandomSource random) {
		if (!this.enabled) {
			this.oneTimeTextureProvider.setTicks(this.getTicks());
			return this.oneTimeTextureProvider.getTexture(random);
		}
		return super.getInitializationTextureFromNotEmptyTextures(random);
	}

	@Override
	public boolean isShouldDead() {
		if (!this.enabled) {
			return this.oneTimeTextureProvider.isShouldDead();
		}
		return super.isShouldDead();
	}
}
