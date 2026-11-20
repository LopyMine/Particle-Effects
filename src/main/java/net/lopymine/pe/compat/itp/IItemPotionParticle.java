package net.lopymine.pe.compat.itp;

import net.lopymine.pe.compat.IPotionParticleThing;
import org.jetbrains.annotations.Nullable;

public interface IItemPotionParticle extends IPotionParticleThing {

	@Nullable
	Integer particleEffects$getLastColor();

	void particleEffects$setLastColor(int color);

}
