package com.dxbednarczyk.craftconnect;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.TextChannel;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;

public class Discord {
    public GatewayDiscordClient Client;

    private final String channelID;

    static class Config {
        public String botToken;
        public String channelID;
    }

    public Discord(String token, String channelID) {
        this.Client = DiscordClientBuilder
                .create(token)
                .build()
                .gateway()
                .login()
                .block();

        this.channelID = channelID;
    }

    public void sendMessage(SignedMessage message, ServerPlayerEntity sender) {
        var author = sender.getDisplayName().getString();
        var content = message.getContent().getString();

        this.Client.getChannelById(Snowflake.of(this.channelID))
                .ofType(TextChannel.class)
                .flatMap(channel -> channel.createMessage(String.format("<%s>: %s", author, content)))
                .subscribe();
    }
}