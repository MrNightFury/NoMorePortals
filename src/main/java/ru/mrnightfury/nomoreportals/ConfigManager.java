package ru.mrnightfury.nomoreportals;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

public class ConfigManager {
	private static Config currentConfig;

	public static Config getCurrentConfig() {
		return currentConfig;
	}

	public static void loadWorldSettings(Path path) {
		File file = new File(path.toFile(), NoMorePortals.MOD_ID + ".json");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			currentConfig = Config.load(new Gson().fromJson(br, JsonObject.class));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void saveWorldSettings(Path path) {

	}

	public static class Config {
		@Expose public Boolean netherPortalAllowed;
		@Expose public Boolean endPortalAllowed;
		@Expose public Boolean edenRingPortalAllowed;

		public static Config load(JsonObject obj) {
			Config config = new Config();
			JsonElement el = obj.get("netherPortalAllowed");
			config.netherPortalAllowed = el != null && el.getAsBoolean();
			el = obj.get("endPortalAllowed");
			config.endPortalAllowed = el != null && el.getAsBoolean();
			el = obj.get("edenRingPortalAllowed");
			config.edenRingPortalAllowed = el != null && el.getAsBoolean();
			return config;
		}

		@Override
		public String toString() {
			return "Config{" +
					"\nnetherPortalAllowed=" + netherPortalAllowed +
					",\nendPortalAllowed=" + endPortalAllowed +
					",\nedenRingPortalAllowed=" + edenRingPortalAllowed +
					"\n}";
		}
	}
}
