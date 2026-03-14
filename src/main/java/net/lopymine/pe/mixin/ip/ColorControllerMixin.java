package net.lopymine.pe.mixin.ip;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import java.util.List;
import net.lopymine.ip.element.base.*;
import net.lopymine.ip.element.controller.color.ColorController;
import net.lopymine.ip.element.inventory.AbstractInventoryElement;
import net.lopymine.ip.element.texture.AtlasTexture;
import net.lopymine.ip.element.texture.provider.OneTimeTextureProvider;
import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.compat.ip.*;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ColorController.class)
public class ColorControllerMixin {

	@Unique
	private static final Identifier PARTICLES_ATLAS_ID = ParticleEffects.parseId("minecraft:particles");

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/lopymine/ip/element/base/IRepaintable;setColor(I)V", remap = false), method = "tick(Lnet/lopymine/ip/element/base/IRepaintable;)V", remap = false)
	private void swapColorAndTextureForPotionParticleConfig(IRepaintable element, int color, Operation<Void> original) {
		particleEffects$updateTextureForColor(element, color);
		original.call(element, color);
	}

	@Unique
	private static void particleEffects$updateTextureForColor(IRepaintable element, int color) {
		if (!(element instanceof AbstractInventoryElement<?> inventoryElement)) {
			return;
		}

		if (inventoryElement.getColor() == color) {
			return;
		}

		if (!(inventoryElement instanceof IPotionParticleThing thing) || !thing.particleEffects$get()) {
			return;
		}

		if (inventoryElement.getTextureProvider() instanceof OneTimeTextureProvider provider) {
			inventoryElement.setTextureProvider(new DynamicPotionTextureProvider(provider));
		}

		if (!(inventoryElement.getTextureProvider() instanceof DynamicPotionTextureProvider textureProvider)) {
			return;
		}

		List<String> list = ParticleEffectsManager.getParticleMobEffects(color);
		if (list == null) {
			textureProvider.setEnabled(false);
			return;
		}

		textureProvider.setEnabled(true);

		String modEffectId = list.get(((IRandomizable) element).getRandom().nextInt(0, list.size()));
		Identifier id = ParticleEffects.id("%s/%s".formatted(modEffectId, inventoryElement.getRandom().nextInt(1, 3)));
		AtlasTexture currentTexture = new AtlasTexture(id, PARTICLES_ATLAS_ID);
		//? if >=1.21.10 {
		currentTexture.initialize();
		//?} else {
		/*currentTexture.setAtlasSprite(((ParticleEngineAccessor) Minecraft.getInstance().particleEngine).getTextureAtlas().getSprite(id));
		*///?}

		textureProvider.setCurrentTexture(currentTexture);
	}

}
