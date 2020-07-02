package org.luckymod;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class LuckyModEvents {
    public static void OnJoin(){

        DiscordRichPresence presence = new DiscordRichPresence();
        presence.largeImageKey = "logo";
        presence.largeImageText = "LuckyMod";
        presence.details = "Jogando...";
        presence.state = Minecraft.getMinecraft().isIntegratedServerRunning()? "no Mapa " + Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldName() : "no servidor " + Minecraft.getMinecraft().getCurrentServerData().serverIP;
        presence.startTimestamp = System.currentTimeMillis();
        DiscordRPC.discordUpdatePresence(presence);
        DiscordRPC.discordRunCallbacks();
    }
    public static void onUnpause(){
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.largeImageKey = "logo";
        presence.largeImageText = "LuckyMod";
        presence.details = "Jogando...";
        presence.state = Minecraft.getMinecraft().isIntegratedServerRunning()? "no Mapa " + Minecraft.getMinecraft().theWorld.getSaveHandler().getWorldDirectory().getName() : "no servidor " + Minecraft.getMinecraft().getCurrentServerData().serverIP;
        presence.startTimestamp = System.currentTimeMillis();
        DiscordRPC.discordUpdatePresence(presence);
        DiscordRPC.discordRunCallbacks();
    }
}
