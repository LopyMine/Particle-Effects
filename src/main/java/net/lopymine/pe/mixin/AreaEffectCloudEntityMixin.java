package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import java.util.function.Supplier;
import net.lopymine.pe.capture.ParticleCaptures;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.*;
import net.minecraft.particle.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.client.ParticleEffectsClient;
import net.lopymine.pe.manager.ParticleEffectsManager;
import net.lopymine.pe.utils.*;

import java.util.List;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaEffectCloudEntity.class)
public abstract class AreaEffectCloudEntityMixin extends Entity {

	@Unique
	private boolean needReset;
	@Unique
	private boolean needResetDebugData;

	//? =1.20.1
	@Shadow public abstract int getColor();

	public AreaEffectCloudEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	// LINGERING POTION
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/AreaEffectCloudEntity;getParticleType()Lnet/minecraft/particle/ParticleEffect;"), method = /*? if >=1.21.2 {*/ /*"clientTick" *//*?} else {*/ "tick" /*?}*/)
	private ParticleEffect swapParticleType(AreaEffectCloudEntity instance, Operation<ParticleEffect> original) {
		ParticleEffect originalParticle = original.call(instance);

		if (!ParticleEffects.getConfig().isModEnabled()) {
			return this.markDebugData(31, originalParticle);
		}

		//? =1.20.1 {
		int color = this.getColor();
		//?} else {
		/*if (!(originalParticle instanceof /^? if >=1.21.8 {^/ /^TintedParticleEffect ^//^?} else {^/ EntityEffectParticleEffect /^?}^/ effect)) {
			return this.markDebugData(32, originalParticle);
		}
		int color = effect.color;
		*///?}

		List<ParticleEffect> list = ParticleEffectsManager.getParticleEffects(ArgbUtils.getColorWithoutAlpha(color));
		if (list == null) {
			return this.markDebugData(33, originalParticle);
		}
		if (list.isEmpty()) {
			return this.markDebugData(34, originalParticle);
		}

		ParticleEffect particleEffect = ListUtils.getRandomElement(list, this.getWorld().getRandom());
		if (particleEffect == null) {
			return this.markDebugData(35, originalParticle);
		}

		((PEType) particleEffect).particleEffects$setColor(color);

		ParticleCaptures.setParticle(particleEffect);
		this.needReset = true;
		return particleEffect;
	}

	@Inject(at = @At("TAIL"), method = /*? if >=1.21.2 {*/ /*"clientTick" *//*?} else {*/ "tick" /*?}*/)
	private void resetParticle(CallbackInfo ci) {
		if (this.needReset) {
			this.needReset = false;
			ParticleCaptures.setParticle(null);
		}
		if (this.needResetDebugData) {
			this.needResetDebugData = false;
			ParticleCaptures.setDebugData(null);
		}
	}

	@Unique
	private ParticleEffect markDebugData(int data, ParticleEffect originalParticle) {
		ParticleCaptures.setDebugData(data);
		this.needResetDebugData = true;
		return originalParticle;
	}

}
