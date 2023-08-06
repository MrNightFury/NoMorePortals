package ru.mrnightfury.nomoreportals.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.edenring.world.EdenPortal;
import ru.mrnightfury.nomoreportals.ConfigManager;

@Mixin(EdenPortal.class)
public abstract class EdenPortalMixin {
	@Inject(method = "buildPortal", at = @At("HEAD"), cancellable = true)
	private static void buildPortal(World level, BlockPos center, CallbackInfo ci) {
		if (!ConfigManager.getConfig().allowEdenRing) {
			PlayerEntity player = level.getClosestPlayer(center.getX(), center.getY(), center.getZ(), 10 ,false);
			if (!level.isClient) {
				if (player != null) {
					player.sendMessage(Text.translatable("nomoreportals:cantCreatePortal.edenRing"));
				}
				level.playSound(null, center, SoundEvents.ENTITY_GENERIC_EXPLODE,
						SoundCategory.BLOCKS, 0.5f, 1f);
			}
			ci.cancel();
		}
	}
}
