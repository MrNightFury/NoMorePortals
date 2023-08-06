package ru.mrnightfury.nomoreportals;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypedActionResult;

public class NoMorePortalsServer implements DedicatedServerModInitializer {
	@Override
	public void onInitializeServer() {
		ConfigManager.load();
		Log.info(LogCategory.LOG, ConfigManager.getConfig().toString());
		Log.info(LogCategory.LOG, "Server Init");

	}
}
