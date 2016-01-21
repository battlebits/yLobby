package br.com.battlebits.ylobby.detector;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class PlayerOutOfLobbyDetector extends DetectorBase {

	private ArrayList<String> message;

	public PlayerOutOfLobbyDetector() {
		message = new ArrayList<>();
		message.add("§0");
		message.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você §c§lnao§7 pode §c§lsair §7do §c§lLobby§7!"));
		message.add("§0");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void detect() {
		if (Bukkit.getOnlinePlayers().length > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if ((p.getLocation().distance(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation()) >= 60 || p.getLocation().getY() >= 100
						|| (p.getLocation().getY() <= 35)) && p.getGameMode() != GameMode.CREATIVE) {
					p.teleport(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation());
					for (String msg : message) {
						p.sendMessage(msg);
					}
					p.playSound(p.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
					p.setFallDistance(0F);
				}
			}
		}
	}

}
