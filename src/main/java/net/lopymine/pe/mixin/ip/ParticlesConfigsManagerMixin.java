package net.lopymine.pe.mixin.ip;

import com.llamalad7.mixinextras.sugar.Local;
import net.lopymine.ip.InventoryParticles;
import net.lopymine.ip.config.particle.*;
import net.lopymine.ip.element.color.NbtListColorProvider;
import net.lopymine.ip.resourcepack.manager.ParticlesConfigsManager;
import net.lopymine.pe.compat.ip.IPotionParticleThing;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticlesConfigsManager.class)
public class ParticlesConfigsManagerMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/lopymine/ip/config/particle/ParticleConfig;getHolders()Ljava/util/HashSet;", remap = false), method = "registerConfig(Lnet/lopymine/ip/config/particle/ParticleConfig;Lnet/minecraft/resources/Identifier;)V", remap = false)
	private void markPotionConfig(CallbackInfo ci, @Local(argsOnly = true) ParticleConfig config, @Local(argsOnly = true) Identifier id) {
		if (id.getNamespace().equals(InventoryParticles.MOD_ID) && id.getPath().startsWith("iparticles/vanilla/potion/potion")) {
			((IPotionParticleThing) config).particleEffects$set(true);
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/lopymine/ip/config/particle/ParticleHolder;create(Ljava/util/function/Function;)Lnet/lopymine/ip/element/mod/spawner/ParticleSpawner;", remap = false), method = "registerConfig(Lnet/lopymine/ip/config/particle/ParticleConfig;Lnet/minecraft/resources/Identifier;)V", remap = false)
	private void swapColorType(CallbackInfo ci, @Local(argsOnly = true) ParticleConfig config, @Local ParticleHolder holder) {
		if (!(config instanceof IPotionParticleThing thing) || !thing.particleEffects$get()) {
			return;
		}
		holder.setColor(new NbtListColorProvider());
	}

}
