package ru.mrnightfury.nomoreportals.mixin;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.AreaHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaHelper.class)
public abstract class AreaHelperMixin {
	@Shadow
	private BlockPos lowerCorner;
	@Shadow
	private int height;
	@Shadow @Final
	private int width;
	@Shadow @Final
	private Direction negativeDir;
	@Shadow @Final
	private WorldAccess world;


	@Inject(cancellable = true, method = "createPortal", at = @At("HEAD"))
	public void asd(CallbackInfo ci) {
		MinecraftClient.getInstance().player.sendMessage(Text.translatable("nomoreportals.cantCreatePortal"));
//				.sendChatMessage("Text", Text.translatable("nomoreportals.cantCreatePortal"));
		ci.cancel();
//		BlockState blockState = Blocks.PINK_STAINED_GLASS.getDefaultState();
//		BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1)
//				.offset(this.negativeDir, this.width - 1))
//				.forEach(blockPos -> this.world.setBlockState(blockPos, blockState, Block.NOTIFY_LISTENERS | Block.FORCE_STATE));
	}
}
