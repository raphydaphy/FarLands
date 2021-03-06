package site.geni.FarLands.mixins.common.world;

import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
@Mixin(World.class)
public abstract class WorldMixin {
	@Shadow
	@Final
	public Dimension dimension;

	@Shadow
	@Final
	private WorldBorder border;

	/**
	 * @param original Original integer of -30000000
	 * @return
	 * @author geni
	 */
	@ModifyConstant(
		constant = @Constant(
			intValue = -30000000
		),
		method = "isValid(Lnet/minecraft/util/math/BlockPos;)Z"
	)
	private static int validUpToNegativeIntegerMaxValueXZ(int original) {
		return Integer.MIN_VALUE;
	}

	/**
	 * Make anything up to X/Z: {@link Integer#MAX_VALUE} a valid position
	 *
	 * @param original Original integer of 30000000
	 * @return
	 * @author geni
	 */
	@ModifyConstant(
		constant = @Constant(
			intValue = 30000000
		),
		method = "isValid(Lnet/minecraft/util/math/BlockPos;)Z"
	)
	private static int validUpToPositiveIntegerMaxValueXZ(int original) {
		return Integer.MAX_VALUE;
	}

	/**
	 * Gets light level of given position up to X/Z: {@link Integer#MIN_VALUE} instead of -3.0E7
	 *
	 * @param original Original integer of -30000000
	 * @return {@link Integer#MIN_VALUE}
	 */
	@ModifyConstant(
		constant = @Constant(
			intValue = -30000000
		),
		method = "getLightLevel(Lnet/minecraft/util/math/BlockPos;I)I"
	)
	private static int getLightLevelUpToNegativeIntegerMaxValueXZ(int original) {
		return Integer.MIN_VALUE;
	}

	/**
	 * Gets light level of given position up to X/Z: {@link Integer#MAX_VALUE} instead of 3.0E7
	 *
	 * @param original Original integer of 30000000
	 * @return {@link Integer#MAX_VALUE}
	 */
	@ModifyConstant(
		constant = @Constant(
			intValue = 30000000
		),
		method = "getLightLevel(Lnet/minecraft/util/math/BlockPos;I)I"
	)
	private static int getLightLevelUpToPositiveIntegerMaxValueXZ(int original) {
		return Integer.MAX_VALUE;
	}

	/**
	 * Gets top block inside the ground in sample up to X/Z: {@link Integer#MIN_VALUE} instead of -3.0E7
	 *
	 * @param original Original integer of -30000000
	 * @return {@link Integer#MIN_VALUE}
	 * @author geni
	 */
	@ModifyConstant(
		constant = @Constant(
			intValue = -30000000
		),
		method = "getTop"
	)
	private static int getTopUpToNegativeIntegerMaxValueXZ(int original) {
		return Integer.MIN_VALUE;
	}

	/**
	 * Gets top block inside the ground in sample up to X/Z: {@link Integer#MAX_VALUE} instead of 3.0E7
	 *
	 * @param original Original integer of 30000000
	 * @return {@link Integer#MAX_VALUE}
	 * @author geni
	 */
	@ModifyConstant(
		constant = @Constant(
			intValue = 30000000
		),
		method = "getTop"
	)
	private static int getTopUpToPositiveIntegerMaxValueXZ(int original) {
		return Integer.MAX_VALUE;
	}

	@Shadow
	public abstract WorldBorder getWorldBorder();

	@Shadow
	public abstract World getWorld();

	@Shadow
	public abstract Dimension getDimension();

	@Shadow
	public abstract LevelProperties getLevelProperties();

	@Shadow @Final public Random random;

	/**
	 * Sets world border maximum radius to {@link Integer#MAX_VALUE} and size to {@link Double#MAX_VALUE}
	 *
	 * @param levelProperties World's {@link LevelProperties}
	 * @param dimensionType   World's {@link DimensionType}
	 * @param biFunction      {@link BiFunction} used for creating a {@link ChunkManager}
	 * @param profiler        World's {@link Profiler}
	 * @param isClient        If the world is a {@link net.minecraft.client.world.ClientWorld} or a {@link net.minecraft.server.world.ServerWorld}
	 * @param ci              {@link CallbackInfo} required for {@link Inject}
	 * @author geni
	 */
	@Inject(
		at = @At(
			value = "RETURN"
		),
		method = "<init>"
	)
	private void setSizeAndMaxRadius(LevelProperties levelProperties, DimensionType dimensionType, BiFunction<World, Dimension, ChunkManager> biFunction, Profiler profiler, boolean isClient, CallbackInfo ci) {
		this.getWorldBorder().setMaxWorldBorderRadius(Integer.MAX_VALUE);
		this.getWorldBorder().setSize(Double.MAX_VALUE);

		this.getLevelProperties().setBorderSize(Double.MAX_VALUE);
	}
}
