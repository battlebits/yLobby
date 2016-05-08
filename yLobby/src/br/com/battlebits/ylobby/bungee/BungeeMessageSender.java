package br.com.battlebits.ylobby.bungee;

import org.bukkit.Bukkit;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class BungeeMessageSender {

	public void tryToSendMessage(BungeeMessage message) {
		if (Bukkit.getOnlinePlayers().size() > 0) {
			Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", message.getDataOutput().toByteArray());
		}
	}

}
