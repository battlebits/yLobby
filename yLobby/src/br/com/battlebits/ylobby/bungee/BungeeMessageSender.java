package br.com.battlebits.ylobby.bungee;

import org.bukkit.Bukkit;

import br.com.battlebits.ylobby.LobbyMain;

public class BungeeMessageSender {

	public static void tryToSendMessage(BungeeMessage message) {
		if (Bukkit.getOnlinePlayers().size() > 0) {
			Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", message.getDataOutput().toByteArray());
		}
	}

}
