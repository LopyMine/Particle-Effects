package net.lopymine.pe.mixin.ip;

import net.lopymine.ip.element.inventory.AbstractInventoryElement;
import net.lopymine.pe.compat.ip.IPotionParticleThing;
import net.lopymine.pe.utils.ArgbUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractInventoryElement.class)
public class AbstractInventoryElementMixin {

	@Inject(at = @At("RETURN"), method = "getRenderColor", cancellable = true, remap = false)
	private void inject(CallbackInfoReturnable<Integer> cir) {
		if (!(this instanceof IPotionParticleThing thing) || !thing.particleEffects$get()) {
			return;
		}
		cir.setReturnValue(ArgbUtils.getArgb(ArgbUtils.getAlpha(cir.getReturnValue()), 255, 255,255));
	}

}
