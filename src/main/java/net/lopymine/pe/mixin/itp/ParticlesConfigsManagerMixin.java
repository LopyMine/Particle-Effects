package net.lopymine.pe.mixin.itp;

//? if item-particles {

import com.llamalad7.mixinextras.sugar.Local;
import net.lopymine.itp.ItemParticles;
import net.lopymine.itp.config.particle.*;
import net.lopymine.itp.element.color.NbtListColorProvider;
import net.lopymine.itp.resourcepack.manager.ParticlesConfigsManager;
import net.lopymine.pe.compat.*;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticlesConfigsManager.class)
public class ParticlesConfigsManagerMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/lopymine/itp/config/particle/ParticleConfig;getHolders()Ljava/util/HashSet;", remap = false), method = "registerConfig(Lnet/lopymine/itp/config/particle/ParticleConfig;Lnet/minecraft/resources/Identifier;)V", remap = false)
	private void markPotionConfig(CallbackInfo ci, @Local(argsOnly = true) ParticleConfig config, @Local(argsOnly = true) Identifier id) {
		if (PotionParticleConfigs.isPotionConfig(id, ItemParticles.MOD_ID)) {
			((IPotionParticleThing) config).particleEffects$set(true);
		}
	}

	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lnet/lopymine/itp/config/particle/ParticleHolder;createSpawner(Ljava/util/function/Function;)Lnet/lopymine/itp/element/spawner/ParticleSpawner;",
					remap = false
			),
			method = "registerConfig(Lnet/lopymine/itp/config/particle/ParticleConfig;Lnet/minecraft/resources/Identifier;)V",
			remap = false
	)
	private void swapColorType(CallbackInfo ci, @Local(argsOnly = true) ParticleConfig config, @Local ParticleHolder holder) {
		if (!(config instanceof IPotionParticleThing thing) || !thing.particleEffects$get()) {
			return;
		}
		holder.setColor(new NbtListColorProvider());
	}

}
//?}
