package com.dxbednarczyk.craftconnect;

import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CraftConnect implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("com.dxbednarczyk.craftconnect");
	public static Discord DISCORD_CLIENT;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		LOGGER.info("Creating Discord client");

		var configFile = new File("config", "craftconnect.json");

		try (Reader reader = new FileReader(configFile)) {
			var gson = new Gson();
			var config = gson.fromJson(reader, Discord.Config.class);

			DISCORD_CLIENT = new Discord(config.botToken, config.channelID);
		} catch (IOException e) {
			LOGGER.error("Failed to read from configuration file");
			return;
		}

		LOGGER.info("Discord client created");
	}
}
