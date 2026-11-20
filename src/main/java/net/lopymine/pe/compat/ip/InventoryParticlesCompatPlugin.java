package net.lopymine.pe.compat.ip;

import net.lopymine.pe.compat.CompatPlugin;

public class InventoryParticlesCompatPlugin extends CompatPlugin {

	@Override
	protected String getCompatModId() {
		return "inventory_particles";
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return super.shouldApplyMixin(targetClassName, mixinClassName) && this.isCompatModVersionAtLeast("1.5.0");
	}
}
