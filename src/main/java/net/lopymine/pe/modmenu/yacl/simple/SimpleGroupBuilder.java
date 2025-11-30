package net.lopymine.pe.modmenu.yacl.simple;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.OptionGroup.Builder;
import net.minecraft.network.chat.Component;

import net.lopymine.pe.utils.ModMenuUtils;

public class SimpleGroupBuilder {

	private final Builder groupBuilder;

	public SimpleGroupBuilder(String groupId) {
		String groupKey = ModMenuUtils.getGroupKey(groupId);
		Component groupName = ModMenuUtils.getName(groupKey);
		Component description = ModMenuUtils.getDescription(groupKey);

		this.groupBuilder = OptionGroup.createBuilder()
				.name(groupName)
				.description(OptionDescription.of(description));
	}

	public static SimpleGroupBuilder createBuilder(String groupId) {
		return new SimpleGroupBuilder(groupId);
	}

	public SimpleGroupBuilder options(Option<?>... options) {
		for (Option<?> option : options) {
			if (option == null) {
				continue;
			}
			this.groupBuilder.option(option);
		}
		return this;
	}

	public OptionGroup build() {
		return this.groupBuilder.build();
	}
}
