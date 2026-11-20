package net.lopymine.pe.mixin.itp;

//? if item-particles {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import java.util.List;
import net.lopymine.itp.element.base.IRepaintable;
import net.lopymine.itp.element.controller.color.ColorController;
import net.lopymine.itp.element.texture.AtlasTexture;
import net.lopymine.itp.element.texture.provider.OneTimeTextureProvider;
import net.lopymine.itp.particle.ItemParticle;
import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.compat.itp.*;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.lopymine.pe.utils.ArgbUtils;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ColorController.class)
public class ColorControllerMixin {

	@Unique
	private static final Identifier PARTICLES_ATLAS_ID = ParticleEffects.parseId("minecraft:particles");

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/lopymine/itp/element/base/IRepaintable;setColor(I)V", remap = false), method = "tick(Lnet/lopymine/itp/element/base/IRepaintable;)V", remap = false)
	private void swapColorAndTextureForPotionParticle(IRepaintable element, int color, Operation<Void> original) {
		if (!(element instanceof ItemParticle particle) || !(particle instanceof IItemPotionParticle potionParticle) || !potionParticle.particleEffects$get()) {
			original.call(element, color);
			return;
		}

		Integer lastColor = potionParticle.particleEffects$getLastColor();
		if (lastColor == null || lastColor != color) {
			potionParticle.particleEffects$setLastColor(color);
			particleEffects$updateTextureForColor(particle, color);
		}

		boolean hasEffectTexture = particle.getTextureProvider() instanceof DynamicPotionTextureProvider provider && provider.isEnabled();
		original.call(element, hasEffectTexture ? ArgbUtils.getArgb(ArgbUtils.getAlpha(color), 255, 255, 255) : color);
	}

	@Unique
	private static void particleEffects$updateTextureForColor(ItemParticle particle, int color) {
		if (particle.getTextureProvider() instanceof OneTimeTextureProvider provider) {
			particle.setTextureProvider(new DynamicPotionTextureProvider(provider));
		}

		if (!(particle.getTextureProvider() instanceof DynamicPotionTextureProvider textureProvider)) {
			return;
		}

		List<String> effects = ParticleEffectsManager.getParticleMobEffects(color);
		if (effects == null || effects.isEmpty()) {
			textureProvider.setEnabled(false);
			return;
		}
		textureProvider.setEnabled(true);

		String effectId = effects.get(particle.getRandom().nextInt(0, effects.size()));
		Identifier id = ParticleEffects.id("%s/%s".formatted(effectId, particle.getRandom().nextInt(1, 3)));
		AtlasTexture texture = new AtlasTexture(id, PARTICLES_ATLAS_ID);
		texture.initialize();

		TextureAtlasSprite sprite = texture.getAtlasSprite();
		if (sprite != null) {
			texture.setAtlas(sprite.atlasLocation());
		}

		textureProvider.setCurrentTexture(texture);
		particle.setElementTexture(texture);
	}

}
//?}
