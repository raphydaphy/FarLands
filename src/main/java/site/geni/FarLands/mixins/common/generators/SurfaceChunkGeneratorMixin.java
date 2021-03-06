package site.geni.FarLands.mixins.common.generators;

import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import site.geni.FarLands.utils.Config;

import static net.minecraft.util.math.noise.OctavePerlinNoiseSampler.maintainPrecision;

@SuppressWarnings("unused")
@Mixin(SurfaceChunkGenerator.class)
public abstract class SurfaceChunkGeneratorMixin {
	/**
	 * Maintains precision based on the mod's configuration
	 *
	 * @param coordinate A coordinate (X/Y/Z, does not matter)
	 * @return If the Far Lands are enabled, the coordinate without maintaining precision; if not, vanilla behaviour
	 * @author geni
	 */
	@Redirect(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/math/noise/OctavePerlinNoiseSampler;maintainPrecision(D)D"
		),
		method = "sampleNoise"
	)
	private double dontMaintainPrecision(double coordinate) {
		return Config.getConfig().farLandsEnabled ? coordinate : maintainPrecision(coordinate);
	}
}
