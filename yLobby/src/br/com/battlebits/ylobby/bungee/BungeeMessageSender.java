package br.com.battlebits.ylobby.bungee;

import org.bukkit.Bukkit;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class BungeeMessageSender {

	@SuppressWarnings("deprecation")
	public void tryToSendMessage(BungeeMessage message) {
		if (Bukkit.getOnlinePlayers().length > 0) {
			Bukkit.getOnlinePlayers()[0].sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", message.getDataOutput().toByteArray());
		}
	}

}
