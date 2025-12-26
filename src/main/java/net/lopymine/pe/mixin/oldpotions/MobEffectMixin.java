package net.lopymine.pe.mixin.oldpotions;

import net.lopymine.pe.manager.ParticleEffectsManager;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(value = MobEffect.class, priority = 989)
public class MobEffectMixin {

	@Shadow @Final private int color;

	@Inject(at = @At("HEAD"), method = "getColor", cancellable = true)
	private void noTodayMisterOldPotions(CallbackInfoReturnable<Integer> cir) {
		if (!ParticleEffectsManager.redirectEnabled) {
			return;
		}
		if (ParticleEffectsManager.redirectToVanillaEffectColors) {
			cir.setReturnValue(this.color);
		}
	}

}
