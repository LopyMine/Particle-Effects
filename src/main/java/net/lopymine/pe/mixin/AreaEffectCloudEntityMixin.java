package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.pe.capture.ParticleCaptures;
import net.minecraft.core.particles.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.lopymine.pe.utils.*;

import java.util.List;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaEffectCloud.class)
public abstract class AreaEffectCloudEntityMixin extends Entity {

	@Unique
	private boolean particleEffects$needReset;
	@Unique
	private boolean particleEffects$needResetDebugData;

	//? =1.20.1
	/*@Shadow public abstract int getColor();*/

	public AreaEffectCloudEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	// LINGERING POTION
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/AreaEffectCloud;getParticle()Lnet/minecraft/core/particles/ParticleOptions;"), method = /*? if >=1.21.2 {*/ "clientTick" /*?} else {*/ /*"tick" *//*?}*/)
	private ParticleOptions swapParticleType(AreaEffectCloud instance, Operation<ParticleOptions> original) {
		ParticleOptions originalParticle = original.call(instance);

		if (!ParticleEffects.getConfig().isModEnabled()) {
			return this.particleEffects$markDebugData(31, originalParticle);
		}

		//? =1.20.1 {
		/*int color = this.getColor();
		*///?} else {
		if (!(originalParticle instanceof /*? if >=1.21.8 {*/ ColorParticleOption /*?} else {*/ /*ColorParticleOption *//*?}*/ effect)) {
			return this.particleEffects$markDebugData(32, originalParticle);
		}
		int color = effect.color;
		//?}

		List<ParticleOptions> list = ParticleEffectsManager.getParticleEffects(ArgbUtils.getColorWithoutAlpha(color));
		if (list == null) {
			return this.particleEffects$markDebugData(33, originalParticle);
		}
		if (list.isEmpty()) {
			return this.particleEffects$markDebugData(34, originalParticle);
		}

		ParticleOptions particleEffect = ListUtils.getRandomElement(list, this.level().getRandom());
		if (particleEffect == null) {
			return this.particleEffects$markDebugData(35, originalParticle);
		}

		((PEType) particleEffect).particleEffects$setColor(color);

		ParticleCaptures.setParticle(particleEffect);
		this.particleEffects$needReset = true;
		return particleEffect;
	}

	@Inject(at = @At("TAIL"), method = /*? if >=1.21.2 {*/ "clientTick" /*?} else {*/ /*"tick" *//*?}*/)
	private void resetParticle(CallbackInfo ci) {
		if (this.particleEffects$needReset) {
			this.particleEffects$needReset = false;
			ParticleCaptures.setParticle(null);
		}
		if (this.particleEffects$needResetDebugData) {
			this.particleEffects$needResetDebugData = false;
			ParticleCaptures.setDebugData(null);
		}
	}

	@Unique
	private ParticleOptions particleEffects$markDebugData(int data, ParticleOptions originalParticle) {
		ParticleCaptures.setDebugData(data);
		this.particleEffects$needResetDebugData = true;
		return originalParticle;
	}

}
