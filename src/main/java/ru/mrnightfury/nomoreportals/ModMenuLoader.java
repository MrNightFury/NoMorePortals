package ru.mrnightfury.nomoreportals;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class ModMenuLoader implements ModMenuApi {
	private ConfigManager.Config config;

	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			config = ConfigManager.getConfig();
			ConfigBuilder builder = ConfigBuilder.create()
					.setParentScreen(parent)
					.setTitle(Text.translatable("ru.mrnightfury.configScreenTitle"))
					.setSavingRunnable(() -> {
						ConfigManager.setConfig(config);
						ConfigManager.save();
					});
			ConfigCategory category = builder.getOrCreateCategory(Text.translatable("nomoreportals:config.title"));

			category.addEntry(builder.entryBuilder()
					.startBooleanToggle(Text.translatable("nomoreportals:config.allowPortal.nether"), config.allowNether)
					.setDefaultValue(false)
					.setSaveConsumer(newValue -> config.allowNether = newValue)
					.build());

			category.addEntry(builder.entryBuilder()
					.startBooleanToggle(Text.translatable("nomoreportals:config.allowPortal.end"), config.allowEnd)
					.setDefaultValue(false)
					.setSaveConsumer(newValue -> config.allowEnd = newValue)
					.build());

			category.addEntry(builder.entryBuilder()
					.startBooleanToggle(Text.translatable("nomoreportals:config.allowPortal.nether"), config.allowEdenRing)
					.setDefaultValue(false)
					.setSaveConsumer(newValue -> config.allowEdenRing = newValue)
					.build());

			category.addEntry(builder.entryBuilder()
					.startStrList(Text.translatable("nomoreportals:config.forbiddenDimensions"), config.dimIDs)
					.setDefaultValue(new ArrayList<>())
					.setSaveConsumer(newValue -> config.dimIDs = new ArrayList<>(newValue))
					.build());

			return builder.build();
		};
	}
}
