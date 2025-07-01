package net.lopymine.pe.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.pe.capture.ParticleCaptures;
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

	//? =1.20.1
	/*@Shadow public abstract int getColor();*/

	public AreaEffectCloudEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	// LINGERING POTION
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/AreaEffectCloudEntity;getParticleType()Lnet/minecraft/particle/ParticleEffect;"), method = /*? if >=1.21.2 {*/ "clientTick" /*?} else {*/ /*"tick" *//*?}*/)
	private ParticleEffect swapParticleType(AreaEffectCloudEntity instance, Operation<ParticleEffect> original) {
		ParticleEffect originalParticle = original.call(instance);

		if (!ParticleEffects.getConfig().isModEnabled()) {
			return originalParticle;
		}

		//? =1.20.1 {
		/*int color = this.getColor();
		*///?} else {
		if (!(originalParticle instanceof EntityEffectParticleEffect effect)) {
			return originalParticle;
		}
		int color = effect.color;
		//?}

		List<ParticleEffect> list = ParticleEffectsManager.getParticleEffects(ArgbUtils.getColorWithoutAlpha(color));
		if (list == null || list.isEmpty()) {
			return originalParticle;
		}

		ParticleEffect particleEffect = ListUtils.getRandomElement(list, this.getWorld().getRandom());
		if (particleEffect == null) {
			return originalParticle;
		}

		//? =1.20.1 {
		/*((PEType) particleEffect).particleEffects$setColor(-1);
		// The color doesn't support alpha at 1.20.1, so we set it to default -1
		*///?} else {
		((PEType) particleEffect).particleEffects$setColor(color);
		//?}

		ParticleCaptures.setParticle(particleEffect);
		this.needReset = true;
		return particleEffect;
	}

	@Inject(at = @At("TAIL"), method = /*? if >=1.21.2 {*/ "clientTick" /*?} else {*/ /*"tick" *//*?}*/)
	private void resetParticle(CallbackInfo ci) {
		if (this.needReset) {
			this.needReset = false;
			ParticleCaptures.setParticle(null);
		}
	}


}
