package site.geni.FarLands.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Config {
	private static ConfigSpec config = new ConfigSpec();
	private static File configFile;

	private static Logger logger = LogManager.getLogger("FarLands");

	/**
	 * Creates config if it doesn't exist; if it does exist, it loads it
	 *
	 * @param file Path to config file
	 * @author geni
	 */
	public static void createConfig(File file) {
		configFile = file;
		try {
			if (!file.isFile() || !file.getParentFile().isDirectory()) {
				file.getParentFile().mkdirs();
				Files.write(file.toPath(), new GsonBuilder().setPrettyPrinting().create().toJson(config).getBytes(), StandardOpenOption.CREATE_NEW);
			}

			config = new Gson().fromJson(new FileReader(file), ConfigSpec.class);
			saveConfig();
		} catch (IOException e) {
			logger.error("[FarLands] Failed to generate config!");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the config
	 *
	 * @return value of config
	 * @author geni
	 */
	public static ConfigSpec getConfig() {
		return config;
	}

	/**
	 * Sets the config
	 *
	 * @param configToSetTo {@link ConfigSpec} to set the config to
	 * @author geni
	 */
	public static void setConfig(ConfigSpec configToSetTo) {
		config = configToSetTo;
	}

	/**
	 * Saves the config to a JSON file, overwriting any already existing one
	 *
	 * @author geni
	 */
	public static void saveConfig() {
		try {
			Files.write(configFile.toPath(), new GsonBuilder().setPrettyPrinting().create().toJson(config).getBytes());
		} catch (IOException e) {
			logger.error("[FarLands] Failed to save config!");
			throw new RuntimeException(e);
		}
	}

	/**
	 * The default configuration
	 *
	 * @author geni
	 */
	public static class ConfigSpec {
		public double coordinateScale = 684.4119873046875D;
		public double heightScale = 684.4119873046875D;
		public double coordinateScaleMultiplier = 1;
		public double heightScaleMultiplier = 1;
		public boolean killFallingBlockEntitiesInFarLands = false;
		public boolean farLandsEnabled = true;
		public boolean fixOreGeneration = true;
		public boolean fixParticles = true;
		public boolean fixLighting = true;
	}
}
