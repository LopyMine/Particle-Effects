package net.lopymine.pe.loader;

//? if fabric {

import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class MossyLoader {

	public static boolean isModLoaded(String modid, boolean loadingPhase) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}

	public static Path getConfigDir() {
		return FabricLoader.getInstance().getConfigDir();
	}
}
//?} elif neoforge {

/*import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.*;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class MossyLoader {

	public static boolean isModLoaded(String modid, boolean loadingPhase) {
		if (loadingPhase) {
			//? if >=1.21.10 {
			/^return FMLLoader.getCurrent().getLoadingModList().getModFileById(modid) != null;
			^///?} else {
			return FMLLoader.getLoadingModList().getModFileById(modid) != null;
			//?}
		} else {
			return ModList.get().isLoaded(modid);
		}
	}

	public static Path getConfigDir() {
		return FMLPaths.CONFIGDIR.get();
	}
}
*///?} elif forge {

/*import java.nio.file.Path;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

@SuppressWarnings("unused")
public class MossyLoader {

	public static boolean isModLoaded(String modid, boolean loadingPhase) {
		if (loadingPhase) {
			return FMLLoader.getLoadingModList().getModFileById(modid) != null;
		} else {
			return ModList.get().isLoaded(modid);
		}
	}

	public static Path getConfigDir() {
		return net.minecraftforge.fml.loading.FMLPaths.CONFIGDIR.get();
	}
}
*///?}
