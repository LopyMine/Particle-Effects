package net.lopymine.pe.compat.itp;

import net.lopymine.pe.compat.CompatPlugin;

public class ItemParticlesCompatPlugin extends CompatPlugin {

	@Override
	protected String getCompatModId() {
		return "item_particles";
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return super.shouldApplyMixin(targetClassName, mixinClassName) && this.isCompatModVersionAtLeast("1.4.0");
	}
}
