package site.geni.FarLands.mixins.client.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.particle.DustParticleParameters;
import net.minecraft.state.property.IntegerProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import site.geni.FarLands.utils.Config;

import java.util.Random;

@SuppressWarnings("unused")
@Mixin(RepeaterBlock.class)
public abstract class RepeaterBlockMixin extends HorizontalFacingBlockMixin {
	@Shadow
	@Final
	public static IntegerProperty field_11451;

	/**
	 * Adds particles created by repeater blocks using {@link Double} for positions instead of {@link Float} in order to have precise particle positions
	 *
	 * @param ci         {@link CallbackInfo} required for {@link Inject}
	 * @param blockState {@link BlockState} of the block
	 * @param world      {@link World} of the block
	 * @param blockPos   {@link BlockPos} of the block
	 * @param random     {@code world}'s {@link Random} instance
	 * @author geni
	 */
	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleParameters;DDDDDD)V"
		),
		method = "randomDisplayTick",
		cancellable = true
	)
	private void addParticlesProperly(BlockState blockState, World world, BlockPos blockPos, Random random, CallbackInfo ci) {
		if (Config.getConfig().fixParticles) {
			Direction direction = blockState.get(field_11177);
			final double x = (blockPos.getX() + 0.5D) + ((double) random.nextFloat() - 0.5D) * 0.2D;
			final double y = (blockPos.getY() + 0.4D) + ((double) random.nextFloat() - 0.5D) * 0.2D;
			final double z = (blockPos.getZ() + 0.5D) + ((double) random.nextFloat() - 0.5D) * 0.2D;

			float float_1 = -5.0F;
			if (random.nextBoolean()) {
				float_1 = (float) (blockState.get(field_11451) * 2 - 1);
			}
			float_1 /= 16.0F;

			double offsetX = (double) (float_1 * (float) direction.getOffsetX());
			double offsetZ = (double) (float_1 * (float) direction.getOffsetZ());

			world.addParticle(DustParticleParameters.RED, x + offsetX, y, z + offsetZ, 0.0D, 0.0D, 0.0D);

			ci.cancel();
		}
	}
}
