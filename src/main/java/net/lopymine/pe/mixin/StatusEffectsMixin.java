package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.world.effect.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffects.class)
public class StatusEffectsMixin {

	//? if >=1.21 {
	@WrapOperation(at = @At(value = "NEW", target = "(Lnet/minecraft/world/effect/MobEffectCategory;I)Lnet/minecraft/world/effect/SaturationMobEffect;"), method = "<clinit>")
	private static SaturationMobEffect fixColor(MobEffectCategory statusEffectCategory, int i, Operation<SaturationMobEffect> original) {
		return original.call(statusEffectCategory, 16262180);
	}
	//?} else {
	/*@WrapOperation(at = @At(value = "NEW", target = "(Lnet/minecraft/world/effect/MobEffectCategory;I)Lnet/minecraft/world/effect/InstantenousMobEffect;", ordinal = 2), method = "<clinit>")
	private static InstantenousMobEffect fixColor(MobEffectCategory statusEffectCategory, int i, Operation<InstantenousMobEffect> original) {
		return original.call(statusEffectCategory, 16262180);
	}
	*///?}

}
