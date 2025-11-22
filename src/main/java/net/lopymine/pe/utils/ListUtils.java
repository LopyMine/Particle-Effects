package net.lopymine.pe.utils;

import net.minecraft.util.RandomSource;

import java.util.List;
import org.jetbrains.annotations.Nullable;

public class ListUtils {

	@Nullable
	public static <T> T getRandomElement(List<T> list, RandomSource random) {
		if (list.isEmpty()) {
			return null;
		}
		return list.get(random.nextIntBetweenInclusive(0, list.size() - 1));
	}
}
