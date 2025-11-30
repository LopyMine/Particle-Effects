package net.lopymine.pe.manager;

import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import java.util.Map.Entry;
import java.util.function.*;
import lombok.experimental.ExtensionMethod;
import net.lopymine.pe.capture.ParticleCaptures;
import net.lopymine.pe.client.ParticleEffectsClient;
import net.lopymine.pe.extension.RegistryExtension;
import net.lopymine.pe.particle.TexturedParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.alchemy.Potion;

import net.minecraft.core.Holder.Reference;
import net.minecraft.resources.ResourceLocation;
import net.lopymine.pe.ParticleEffects;
import net.lopymine.pe.utils.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

//? if fabric {

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

//?}

//? if neoforge {

/*import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.registries.*;

*///?}

//? if forge {
/*import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.*;
*///?}

@ExtensionMethod(RegistryExtension.class)
public class ParticleEffectsManager {

	//? if fabric {
	private static final List<ParticleOptions> REGISTERED_PARTICLE_TYPES = new ArrayList<>();
	private static final Map<MobEffect, ParticleOptions> EFFECT_TO_PARTICLE = new HashMap<>();
	//?}

	//? if neoforge {
	/*public static final DeferredRegister<ParticleType<?>> PARTICLES_REGISTER = DeferredRegister.create(Registries.PARTICLE_TYPE, ParticleEffects.MOD_ID);
	private static final List<DeferredHolder<ParticleType<?>, SimpleParticleType>> REGISTERED_PARTICLE_TYPES = new ArrayList<>();
	private static final Map<MobEffect, DeferredHolder<ParticleType<?>, SimpleParticleType>> EFFECT_TO_PARTICLE = new HashMap<>();
	*///?}

	//? if forge {
	/*public static final DeferredRegister<ParticleType<?>> PARTICLES_REGISTER = DeferredRegister.create(Registries.PARTICLE_TYPE, ParticleEffects.MOD_ID);

	private static final List<RegistryObject<SimpleParticleType>> REGISTERED_PARTICLE_TYPES = new ArrayList<>();
	private static final Map<MobEffect, RegistryObject<SimpleParticleType>> EFFECT_TO_PARTICLE = new HashMap<>();
	*///?}

	private static final Map<Integer, List<ParticleOptions>> COLOR_TO_PARTICLES_MAP = new HashMap<>();
	private static final HashMap<ParticleOptions, MobEffect> MINECRAFT_EFFECTS_WITH_TEXTURED_PARTICLE = getMinecraftEffectWidthTexturedParticles();

	@Nullable
	public static List<ParticleOptions> getParticleEffects(Integer i) {
		return COLOR_TO_PARTICLES_MAP.get(i);
	}

	private static void registerParticleTypeForEffect(MobEffect statusEffect, ResourceLocation effectId) {
		ResourceLocation modEffectId = getModEffectId(statusEffect, effectId);

		//? if fabric {
		// CREATE PARTICLE TYPE
		ParticleOptions type = Registry.register(
				BuiltInRegistries.PARTICLE_TYPE,
				modEffectId, // WE NEED IT TO AVOID ISSUE WITH VANILLA TEXTURED PARTICLES
				FabricParticleTypes.simple()
		);

		// ADD TO REGISTERED PARTICLES TO REGISTER THEIR FACTORY AT CLIENT LAYER
		REGISTERED_PARTICLE_TYPES.add(type);
		EFFECT_TO_PARTICLE.put(statusEffect, type);
		//?}

		//? if neoforge {
		/*String registryName = modEffectId.getPath();
		DeferredHolder<ParticleType<?>, SimpleParticleType> holder = PARTICLES_REGISTER.register(registryName, () -> new SimpleParticleType(false));
		REGISTERED_PARTICLE_TYPES.add(holder);
		EFFECT_TO_PARTICLE.put(statusEffect, holder);
		*///?}

		//? if forge {
		/*String registryName = modEffectId.getPath();
		RegistryObject<SimpleParticleType> holder = PARTICLES_REGISTER.register(registryName, () -> new SimpleParticleType(false));
		REGISTERED_PARTICLE_TYPES.add(holder);
		EFFECT_TO_PARTICLE.put(statusEffect, holder);
		*///?}

	}

	private static ResourceLocation getModEffectId(MobEffect statusEffect, ResourceLocation effectId) {
		boolean bl = MINECRAFT_EFFECTS_WITH_TEXTURED_PARTICLE.containsValue(statusEffect);
		return ParticleEffects.id(effectId.getPath() + (bl ? "_new" : ""));
	}

	public static void registerParticleTypes() {
		//-----------------------------------------------------//
		// SWAP OLD PARTICLE TYPE OF STATUS EFFECTS TO NEW ONE //
		//-----------------------------------------------------//
		for (Reference<MobEffect> reference : BuiltInRegistries.MOB_EFFECT.references()) {
			MobEffect statusEffect = reference.value();
			ResourceLocation id = reference.key().location();
			if (!id.getNamespace().equals("minecraft")) {
				continue;
			}

			ParticleEffectsManager.registerParticleTypeForEffect(statusEffect, id);
		}
	}

	public static void swapParticleTypes() {
		//? if fabric {
		for (Entry<MobEffect, ParticleOptions> entry : EFFECT_TO_PARTICLE.entrySet()) {
			StatusEffectUtils.swapParticle(entry.getKey(), entry.getValue());
		}
		//?}

		//? if neoforge {
		/*for (Entry<MobEffect, DeferredHolder<ParticleType<?>, SimpleParticleType>> entry : EFFECT_TO_PARTICLE.entrySet()) {
			StatusEffectUtils.swapParticle(entry.getKey(), entry.getValue().get());
		}
		*///?}

		//? if forge {
		/*for (Entry<MobEffect, RegistryObject<SimpleParticleType>> entry : EFFECT_TO_PARTICLE.entrySet()) {
			StatusEffectUtils.swapParticle(entry.getKey(), entry.getValue().get());
		}
		*///?}
	}

	public static void registerParticleColorsForTypes() {
		//---------------------------------------------------//
		// REGISTER EACH POTION COLOR TO LIST POTION EFFECTS //
		//        POTION COLOR = MIXED COLORS OF EFFECTS     //
		//---------------------------------------------------//
		for (Reference<Potion> reference : BuiltInRegistries.POTION.references()) {
			Potion potion = reference.value();
			ResourceLocation id = reference.key().location();
			if (!id.getNamespace().equals("minecraft")) {
				continue;
			}

			List<MobEffectInstance> effects = potion.getEffects();

			//? =1.20.1 {
			/*int color = ArgbUtils.getColorWithoutAlpha(StatusEffectUtils.getColor(effects));

			List<ParticleOptions> particleEffects = effects.stream()
					.map(MobEffectInstance::getEffect)
					.flatMap((effect) -> {
						ParticleOptions particleEffect = ((PEStatusEffect) effect).particleEffects$getParticleEffect();
						if (particleEffect == null) {
							ParticleEffectsClient.LOGGER.error("[DEV/Potion Registration] Looks like {} effect (from potion with color {}) doesn't have textured particle, this shouldn't happen! Skipping it registration.", color, effect.getDisplayName().getString());
							return Stream.empty();
						}
						return Stream.of(particleEffect);
					})
					.toList();

			*///?} else {
			OptionalInt optional = net.minecraft.world.item.alchemy.PotionContents.getColorOptional(effects);
			if (optional.isEmpty()) {
				continue;
			}

			int color = ArgbUtils.getColorWithoutAlpha(optional.getAsInt());

			List<ParticleOptions> particleEffects = effects.stream()
					.map(MobEffectInstance::getEffect)
					.map(Holder::value)
					.flatMap((effect) -> {
						ParticleOptions particleEffect = ((PEStatusEffect) effect).particleEffects$getParticleEffect();
						if (particleEffect == null) {
							ParticleEffects.LOGGER.error("[DEV/Potion Registration] Looks like {} effect with color {} doesn't have textured particle, this shouldn't happen! Skipping it registration.", color, effect.getDisplayName().getString());
							return Stream.empty();
						}
						return Stream.of(particleEffect);
					})
					.toList();
			//?}

			COLOR_TO_PARTICLES_MAP.put(color, particleEffects);
		}

		//------------------------------------------------------//
		// NOT ALL EFFECTS CAN BE FOUND IN POTIONS, SO, WE ALSO //
		//  NEED TO REGISTER EACH EFFECT COLOR TO THEIR EFFECT  //
		//------------------------------------------------------//
		for (Reference<MobEffect> reference : BuiltInRegistries.MOB_EFFECT.references()) {
			MobEffect statusEffect = reference.value();
			ResourceLocation id = reference.key().location();
			if (!id.getNamespace().equals("minecraft")) {
				continue;
			}

			int color = ArgbUtils.getColorWithoutAlpha(statusEffect.getColor());

			ParticleOptions particleEffect = ((PEStatusEffect) statusEffect).particleEffects$getParticleEffect();
			// WE SET PARTICLE EFFECT(AND TYPE) AT FIRST PHASE
			// WHEN WE REGISTERED NEW PARTICLE TYPE

			if (particleEffect == null) {
				ParticleEffects.LOGGER.error("[DEV/Effect Registration] Looks like {} effect with color {} doesn't have textured particle, this shouldn't happen! Skipping its registration.", color, statusEffect.getDisplayName().getString());
				continue;
			}

			List<ParticleOptions> effects = COLOR_TO_PARTICLES_MAP.get(color);
			if (effects != null) {
				if (ParticleEffects.getConfig().isDebugLogEnabled()) {
					ParticleEffects.LOGGER.warn("[DEV/Effect Registration] Found registered effects for color {} from {} effect, skipping its registration. If you just mod user, ignore it.", color, statusEffect.getDisplayName().getString());
				}
			} else {
				COLOR_TO_PARTICLES_MAP.put(color, List.of(particleEffect));
			}
		}
	}

	//? if fabric {
	public static void registerParticleFactories() {
		for (ParticleOptions type : REGISTERED_PARTICLE_TYPES) {
			ParticleFactoryRegistry.getInstance().register((SimpleParticleType) type, TexturedParticleFactory::new);
		}
	}
	//?}

	//? if neoforge {
	/*@SubscribeEvent
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		for (DeferredHolder<ParticleType<?>, SimpleParticleType> holder : REGISTERED_PARTICLE_TYPES) {
			event.registerSpriteSet(holder.get(), TexturedParticleFactory::new);
		}
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			swapParticleTypes();
			registerParticleColorsForTypes();
		});
	}
	*///?}

	//? if forge {
	/*@SubscribeEvent
	public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
		for (RegistryObject<SimpleParticleType> holder : REGISTERED_PARTICLE_TYPES) {
			event.registerSpriteSet(holder.get(), TexturedParticleFactory::new);
		}
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			swapParticleTypes();
			registerParticleColorsForTypes();
		});
	}
	*///?}

	private static HashMap<ParticleOptions, MobEffect> getMinecraftEffectWidthTexturedParticles() {
		//? =1.20.1 {
		/*return new HashMap<>();
		 *///?} else {
		HashMap<ParticleOptions, MobEffect> map = new HashMap<>();

		map.put(ParticleTypes.ITEM_SLIME, MobEffects.OOZING.value());
		map.put(ParticleTypes.ITEM_COBWEB, MobEffects.WEAVING.value());
		map.put(ParticleTypes.INFESTED, MobEffects.INFESTED.value());
		map.put(ParticleTypes.TRIAL_OMEN, MobEffects.TRIAL_OMEN.value());
		map.put(ParticleTypes.RAID_OMEN, MobEffects.RAID_OMEN.value());
		map.put(ParticleTypes.SMALL_GUST, MobEffects.WIND_CHARGED.value());

		return map;
		//?}
	}

	public static MobEffect getVanillaStatusEffectByStatusEffect(ParticleOptions parameters) {
		return MINECRAFT_EFFECTS_WITH_TEXTURED_PARTICLE.get(parameters);
	}

	public static void processSplashPotionStageOne(LocalRef<List<ParticleOptions>> localParticleEffects, int color) {
		localParticleEffects.set(null);

		if (!ParticleEffects.getConfig().isModEnabled()) {
			return;
		}

		List<ParticleOptions> list = ParticleEffectsManager.getParticleEffects(ArgbUtils.getColorWithoutAlpha(color));
		if (list == null) {
			return;
		}

		localParticleEffects.set(list);
	}

	public static Particle processSplashPotionStageTwo(@Nullable Level world, ParticleOptions original, Function<ParticleOptions, Particle> function, LocalRef<List<ParticleOptions>> localParticleEffects, int color) {
		Supplier<Particle> particleSupplier = () -> function.apply(original);

		if (!ParticleEffects.getConfig().isModEnabled()) {
			return markDebugData(21, particleSupplier);
		}

		List<ParticleOptions> list = localParticleEffects.get();
		if (list == null) {
			return markDebugData(22, particleSupplier);
		}
		if (list.isEmpty()) {
			return markDebugData(23, particleSupplier);
		}
		if (world == null) {
			return markDebugData(24, particleSupplier);
		}
		ParticleOptions particleEffect = ListUtils.getRandomElement(list, world.getRandom());
		if (particleEffect == null) {
			return markDebugData(25, particleSupplier);
		}

		((PEType) particleEffect).particleEffects$setColor(color);

		ParticleCaptures.setParticle(particleEffect);
		Particle apply = function.apply(particleEffect);
		ParticleCaptures.setParticle(null);
		return apply;
	}

	public static Particle swapParticle(Level world, ParticleOptions original, Function<ParticleOptions, Particle> function, Supplier<Particle> originalCall/*? if =1.20.1 {*//*, double x, double y, double z *//*?}*/) {
		if (!ParticleEffects.getConfig().isModEnabled()) {
			return markDebugData(10, originalCall);
		}

		if (ParticleCaptures.getParticle() != original) {
			return markDebugData(11, originalCall);
		}

		//? =1.20.1 {
		/*boolean bl = original.equals(ParticleTypes.ENTITY_EFFECT);
		boolean bl2 = original.equals(ParticleTypes.AMBIENT_ENTITY_EFFECT);
		if (!bl && !bl2) {
			return markDebugData(12, originalCall);
		}

		int color = ArgbUtils.getArgb(bl2 ? 38 : 255, (int) (x * 255), (int) (y * 255), (int) (z * 255));
		*///?} else {
		int color;

		if (original instanceof /*? if >=1.21.8 {*/ ColorParticleOption /*?} else {*/ /*ColorParticleOption *//*?}*/ effect) { // RECEIVES IN SINGLEPLAYER AND IN MULTIPLAYER
			color = effect.color;
		} else {
			MobEffect statusEffect = ParticleEffectsManager.getVanillaStatusEffectByStatusEffect(original);
			color = statusEffect == null ? 0 : ArgbUtils.getColorWithoutAlpha(statusEffect.getColor());
		}

		if (color == 0) {
			return markDebugData(13, originalCall);
		}
		//?}

		List<ParticleOptions> list = ParticleEffectsManager.getParticleEffects(ArgbUtils.getColorWithoutAlpha(color));
		if (list == null) {
			return markDebugData(14, originalCall);
		}
		if (list.isEmpty()) {
			return markDebugData(15, originalCall);
		}
		if (world == null) {
			return markDebugData(16, originalCall);
		}

		ParticleOptions particleEffect = ListUtils.getRandomElement(list, world.getRandom());
		if (particleEffect == null) {
			return markDebugData(17, originalCall);
		}

		((PEType) particleEffect).particleEffects$setColor(color);

		return function.apply(particleEffect);
	}

	private static Particle markDebugData(int data, Supplier<Particle> supplier) {
		ParticleCaptures.setDebugData(data);
		Particle particle = supplier.get();
		ParticleCaptures.setDebugData(null);
		return particle;
	}
}
