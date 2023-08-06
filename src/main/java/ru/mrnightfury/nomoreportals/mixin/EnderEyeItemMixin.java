package ru.mrnightfury.nomoreportals.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.mrnightfury.nomoreportals.ConfigManager;

@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {
	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		BlockPos blockPos = context.getBlockPos();
		World world = context.getWorld();
		BlockState blockState = world.getBlockState(blockPos);
		// Если клик по рамке, в ней нет глаза и портал запрещён
		if (blockState.isOf(Blocks.END_PORTAL_FRAME) && !blockState.get(EndPortalFrameBlock.EYE).booleanValue()
				&& !ConfigManager.getConfig().allowEnd) {
			PlayerEntity player = context.getPlayer();
			// На стороне сервера
			if (!context.getWorld().isClient) {
				// Если игрок есть, шлём его (ему)
				if (player != null) {
					player.sendMessage(Text.translatable("nomoreportals:cantCreatePortal.end"));
				}
				// Фигачим звук
				context.getWorld().playSound(null, blockPos, SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT,
						SoundCategory.BLOCKS, 0.5f, 1f);
				cir.setReturnValue(ActionResult.PASS);
			// На стороне клиента success, чтоб рукой махнул
			} else {
				cir.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}
}
