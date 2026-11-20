package net.lopymine.pe.mixin.itp;

//? if item-particles {

import net.lopymine.itp.element.controller.color.ColorController;
import net.lopymine.itp.manager.ItemParticleManager.ItemParticleRequest;
import net.lopymine.itp.particle.ItemParticle;
import net.lopymine.pe.compat.IPotionParticleThing;
import net.lopymine.pe.compat.itp.IItemPotionParticle;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemParticle.class)
public class ItemParticleMixin implements IItemPotionParticle {

	@Unique
	private boolean particleEffects$bl = false;

	@Unique
	@Nullable
	private Integer particleEffects$lastColor;

	@Inject(at = @At("RETURN"), method = "create(Lnet/lopymine/itp/manager/ItemParticleManager$ItemParticleRequest;DDD)Lnet/lopymine/itp/particle/ItemParticle;", remap = false)
	private static void markPotionParticle(ItemParticleRequest request, double x, double y, double z, CallbackInfoReturnable<ItemParticle> cir) {
		ItemParticle particle = cir.getReturnValue();
		if (particle == null || !(request.getConfig() instanceof IPotionParticleThing thing) || !thing.particleEffects$get()) {
			return;
		}
		((IItemPotionParticle) particle).particleEffects$set(true);

		ColorController<ItemParticle> controller = particle.getColorController();
		if (controller != null) {
			controller.tick(particle);
		}
	}

	@Override
	public boolean particleEffects$get() {
		return this.particleEffects$bl;
	}

	@Override
	public void particleEffects$set(boolean bl) {
		this.particleEffects$bl = bl;
	}

	@Override
	public @Nullable Integer particleEffects$getLastColor() {
		return this.particleEffects$lastColor;
	}

	@Override
	public void particleEffects$setLastColor(int color) {
		this.particleEffects$lastColor = color;
	}
}
//?}
