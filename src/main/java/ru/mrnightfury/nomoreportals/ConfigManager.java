package ru.mrnightfury.nomoreportals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.util.Identifier;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ConfigManager {
	private static File file;
	private static void prepareFile() {
		if (file == null) {
			file = new File(FabricLoader.getInstance().getConfigDir().toFile(), NoMorePortals.MOD_ID + ".json");
		}
	}

	private static Config config;
	public static Config getConfig() {
		return config;
	}

	public static void load() {
		prepareFile();
		if (!file.exists()) {
			Log.info(LogCategory.LOG, "File not found, creating...");
			createDefaultConfig();
			save();
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			config = new Gson().fromJson(br, Config.class);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void save() {
		prepareFile();
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = g.toJson(config);

		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(jsonString);
			Log.info(LogCategory.LOG, "Successfully saved commands");
		} catch (IOException e) {
			System.err.println("Couldn't save Commands list");
			Log.error(LogCategory.LOG, e.getMessage());
		}
	}

	public static void setConfig(Config newConfig) {
		config = newConfig;
	}

	public static void createDefaultConfig() {
		config = new Config();
		config.allowNether = false;
		config.allowEnd = false;
		config.allowEdenRing = false;
		config.dimIDs = new ArrayList<>();
	}

	public static boolean isForbidden(Identifier world) {
		for (String s : config.dimIDs) {
			if (Objects.equals(s, world.toString())) {
				return true;
			}
		}
		return false;
	}

	public static class Config {
		@Expose public Boolean allowNether;
		@Expose public Boolean allowEnd;
		@Expose public Boolean allowEdenRing;
		@Expose public ArrayList<String> dimIDs;

		@Override
		public String toString() {
			return "Config{" +
					"\nallowNether=" + allowNether +
					", \nallowEnd=" + allowEnd +
					", \nallowEdenRing=" + allowEdenRing +
					", \ndimIDs=" + dimIDs.toString() +
					"\n}";
		}
	}
}
