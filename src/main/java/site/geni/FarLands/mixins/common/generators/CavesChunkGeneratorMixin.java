package site.geni.FarLands.mixins.common.generators;

import net.minecraft.world.gen.chunk.CavesChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import site.geni.FarLands.utils.Config;

@SuppressWarnings("unused")
@Mixin(CavesChunkGenerator.class)
public abstract class CavesChunkGeneratorMixin {
	/**
	 * Sets Buffet's Caves chunk generator type's coordinate scale to the one set in the mod's configuration
	 *
	 * @param original Original double value of 684.412
	 * @return Coordinate scale as set in the mod's configuration
	 * @author geni
	 */
	@ModifyConstant(
		constant = @Constant(
			doubleValue = 684.412D,
			ordinal = 1
		),
		method = "sampleNoiseColumn"
	)
	private static double setCoordinateScale(double original) {
		return Config.getConfig().coordinateScale * Config.getConfig().coordinateScaleMultiplier;
	}

	/**
	 * Sets Buffet's Caves chunk generator type's height scale to the one set in the mod's configuration
	 *
	 * @param original Original double value of 2053.236
	 * @return Height scale as set in the mod's configuration
	 * @author geni
	 */
	@ModifyConstant(
		constant = @Constant(
			doubleValue = 2053.236D,
			ordinal = 1
		),
		method = "sampleNoiseColumn"
	)
	private static double setHeightScale(double original) {
		return Config.getConfig().heightScale * Config.getConfig().heightScaleMultiplier;
	}
}
