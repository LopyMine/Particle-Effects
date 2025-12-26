package net.lopymine.pe.compat;

import java.util.stream.Stream;
import net.lopymine.pe.loader.MossyLoader;

public class LoadedMods {

	public static boolean isAnyOldPotionsModLoaded() {
		return Stream.of("oldpotions", "legacy_potion_colors").anyMatch((id) -> MossyLoader.isModLoaded(id, true));
	}

}
