package net.lopymine.pe.compat.ip;

import net.lopymine.pe.compat.CompatPlugin;

public class InventoryParticlesCompatPlugin extends CompatPlugin {

	@Override
	protected String getCompatModId() {
		return "inventory_particles";
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (!super.shouldApplyMixin(targetClassName, mixinClassName)) {
			return false;
		}

		//? if fabric {
		String string = net.fabricmc.loader.api.FabricLoader.getInstance()
				.getModContainer(this.getCompatModId())
				.orElseThrow()
				.getMetadata()
				.getVersion()
				.getFriendlyString();
		//?} elif neoforge {

		/*String string =
		//? if >=1.21.10 {
		 net.neoforged.fml.loading.FMLLoader.getCurrent().getLoadingModList().getModFileById(this.getCompatModId()).versionString();
		//?} else {
		/^net.neoforged.fml.loading.FMLLoader.getLoadingModList().getModFileById(this.getCompatModId()).versionString();
		^///?}

		*///?} elif forge {
		/*String string = net.minecraftforge.fml.loading.LoadingModList.get().getModFileById(this.getCompatModId()).versionString();
		 *///?}

		Version hotVersion = Version.of("1.5.0");
		Version version = Version.of(substringBefore(substringBefore(string, "+"), "-"));
		return version.isGreaterOrEqualThan(hotVersion);
	}

	public static String substringBefore(String value, String since) {
		int i = value.indexOf(since);
		if (i == -1) {
			return value;
		}
		return value.substring(0, i);
	}
}
