package ru.mrnightfury.nomoreportals.mixin;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.AreaHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.mrnightfury.nomoreportals.ConfigManager;

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
		if (!ConfigManager.getConfig().allowNether) {
			PlayerEntity player = world.getClosestPlayer(lowerCorner.getX(), lowerCorner.getY(), lowerCorner.getZ(), 10, false);
			if (player != null) {
				player.sendMessage(Text.translatable("nomoreportals:cantCreatePortal.nether"));
			}
			world.playSound(null, lowerCorner, SoundEvents.ENTITY_GHAST_SCREAM,
					SoundCategory.BLOCKS, 0.5f, 1f);

			BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1)
							.offset(this.negativeDir, this.width - 1))
					.forEach(blockPos -> {
						this.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
						((ServerWorld) world).spawnParticles(ParticleTypes.FLAME, blockPos.getX() + .5, blockPos.getY() + .5, blockPos.getZ() + .5,
								10, .25, .25, .25, 0);
					});
			ci.cancel();
		}
	}
}
