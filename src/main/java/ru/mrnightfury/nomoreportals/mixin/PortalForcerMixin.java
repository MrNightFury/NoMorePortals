package ru.mrnightfury.nomoreportals.mixin;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(net.minecraft.world.PortalForcer.class)
public abstract class PortalForcerMixin {
//	@Inject(at = @At("HEAD"), method = "createPortal", cancellable = true)
//	public void createPortal(BlockPos pos, Direction.Axis axis, CallbackInfoReturnable<Optional<BlockLocating.Rectangle>> cir) {
//		Log.info(LogCategory.LOG, "asd");
//		cir.cancel();
//	}
//
//	@Inject(at = @At("HEAD"), method = "getPortalRect")
//	public void asd(BlockPos pos, boolean destIsNether, WorldBorder worldBorder, CallbackInfoReturnable<Optional<BlockLocating.Rectangle>> cir) {
//		Log.info(LogCategory.LOG, pos.toString());
//	}

//	@Inject(method = "createPortal", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
//	public void asd(BlockPos pos, Direction.Axis axis, CallbackInfoReturnable<Optional<BlockLocating.Rectangle>> cir) {
//		Log.info(LogCategory.LOG, "He");
//		cir.cancel();
//	}
}
