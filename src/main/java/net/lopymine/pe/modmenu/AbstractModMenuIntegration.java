package net.lopymine.pe.modmenu;

//? if fabric {
import com.terraformersmc.modmenu.api.*;
import net.fabricmc.loader.api.*;
import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.client.ParticleEffectsClient;
import net.minecraft.client.gui.screens.Screen;

public abstract class AbstractModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		FabricLoader fabricLoader = FabricLoader.getInstance();
		if (fabricLoader.isModLoaded("yet_another_config_lib_v3")) {
			ModContainer modContainer = fabricLoader.getModContainer("yet_another_config_lib_v3").orElseThrow();
			Version version = modContainer.getMetadata().getVersion();
			try {
				Version requestsVersion = Version.parse(ParticleEffects.YACL_DEPEND_VERSION);
				if (version.compareTo(requestsVersion) >= 0) {
					return this::createConfigScreen;
				}
			} catch (VersionParsingException e) {
				ParticleEffectsClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
			}
			return parent -> NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getFriendlyString());
		}
		return NoConfigLibraryScreen::createScreen;
	}

	protected abstract String getModId();

	protected abstract Screen createConfigScreen(Screen parent);
}

//?} elif neoforge {

/*import net.neoforged.fml.*;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.client.ParticleEffectsClient;
import net.lopymine.pe.loader.MossyLoader;
import net.minecraft.client.gui.screens.Screen;
import org.apache.maven.artifact.versioning.*;

public abstract class AbstractModMenuIntegration {

	public void register(ModContainer container) {
		container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> {
			if (MossyLoader.isModLoaded("yet_another_config_lib_v3", false)) {
				ModContainer yacl = ModList.get().getModContainerById("yet_another_config_lib_v3").orElseThrow();
				ArtifactVersion version = yacl.getModInfo().getVersion();
				try {
					ArtifactVersion requestsVersion = new DefaultArtifactVersion(ParticleEffects.YACL_DEPEND_VERSION);
					if (version.compareTo(requestsVersion) >= 0) {
						return this.createConfigScreen(parent);
					}
				} catch (Exception e) {
					ParticleEffectsClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
				}
				return NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getQualifier());
			}
			return NoConfigLibraryScreen.createScreen(parent);
		});
	}

	protected abstract String getModId();

	protected abstract Screen createConfigScreen(Screen parent);

}

*///?} elif forge {

/*import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.client.ParticleEffectsClient;
import net.lopymine.pe.loader.MossyLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.fml.*;
import org.apache.maven.artifact.versioning.*;

public abstract class AbstractModMenuIntegration {

	public void register(ModContainer container) {
		container.registerExtensionPoint(ConfigScreenFactory.class, () -> new ConfigScreenFactory((minecraft, parent) -> {
			if (MossyLoader.isModLoaded("yet_another_config_lib_v3", false)) {
				ModContainer yacl = ModList.get().getModContainerById("yet_another_config_lib_v3").orElseThrow();
				ArtifactVersion version = yacl.getModInfo().getVersion();
				try {
					ArtifactVersion requestsVersion = new DefaultArtifactVersion(ParticleEffects.YACL_DEPEND_VERSION);
					if (version.compareTo(requestsVersion) >= 0) {
						return this.createConfigScreen(parent);
					}
				} catch (Exception e) {
					ParticleEffectsClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
				}
				return NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getQualifier());
			}
			return NoConfigLibraryScreen.createScreen(parent);
		}));
	}

 	protected abstract String getModId();

	protected abstract Screen createConfigScreen(Screen parent);

}

*///?}
