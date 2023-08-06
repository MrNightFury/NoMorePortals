package ru.mrnightfury.nomoreportals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.WorldSavePath;

public class NoMorePortals implements ModInitializer {
	public static String MOD_ID = "nomoreportals";
	@Override
	public void onInitialize() {
		ConfigManager.load();
	}
}
