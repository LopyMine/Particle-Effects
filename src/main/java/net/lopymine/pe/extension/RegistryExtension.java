package net.lopymine.pe.extension;

import java.util.List;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;

public class RegistryExtension {

	public static <T> List<Reference<T>> references(Registry<T> registry) {
		//? if >=1.21.4 {
		return registry.listElements().toList();
		//?} else {
		/*return registry.holders().toList();
		*///?}
	}

}
