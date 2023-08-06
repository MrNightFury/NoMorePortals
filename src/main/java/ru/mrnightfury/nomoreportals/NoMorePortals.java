package ru.mrnightfury.nomoreportals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.BlockEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.PortalForcer;
import ru.mrnightfury.nomoreportals.ConfigManager;

public class NoMorePortals implements ModInitializer {
	public static String MOD_ID = "nomoreportals";
	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTED.register((server -> {
			ConfigManager.loadWorldSettings(server.getSavePath(WorldSavePath.ROOT));

			UseItemCallback.EVENT.register((player, world, hand) -> {
				if (player.isSneaking()) {
					Log.info(LogCategory.LOG, ConfigManager.getCurrentConfig().toString());
				}
				return TypedActionResult.pass(ItemStack.EMPTY);
			});
		}));


	}
}
