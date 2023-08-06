package ru.mrnightfury.nomoreportals.mixin;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.mrnightfury.nomoreportals.ConfigManager;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
	@Shadow public abstract ServerWorld getWorld();

	@Shadow public abstract void sendMessage(Text message, boolean overlay);

	@Inject(method = "moveToWorld", at = @At("HEAD"), cancellable = true)
	public void moveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
		Identifier worldID = destination.getRegistryKey().getValue();
		Log.info(LogCategory.LOG, "Trying to teleport to " + worldID.toString());
		if (isForbiddenTeleport(worldID)) {
			sendMessage(Text.translatable("nomoreportals:cantTeleport", worldID.toString()), true);
			cir.cancel();
		}
	}

	@Inject(method = "teleport", at = @At("HEAD"), cancellable = true)
	public void teleport(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch, CallbackInfo ci) {
		Identifier worldID = targetWorld.getRegistryKey().getValue();
		if (isForbiddenTeleport(worldID)) {
			sendMessage(Text.translatable("nomoreportals:cantTeleport", worldID.toString()), true);
			ci.cancel();
		}
	}

	@Unique
	private boolean isForbiddenTeleport(Identifier to) {
		Identifier from = getWorld().getRegistryKey().getValue();
		return ConfigManager.isForbidden(to) && !Objects.equals(from.toString(), to.toString());
	}
}
