package br.com.battlebits.ylobby.detector;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class PlayerOutOfLobbyDetector extends DetectorBase {

	@Override
	public void detect() {
		if (Bukkit.getOnlinePlayers().size() > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if ((p.getLocation().distance(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation()) >= 120
						|| p.getLocation().getY() >= 220 || (p.getLocation().getY() <= 5))
						&& p.getGameMode() != GameMode.CREATIVE) {
					if ((p.getLocation().clone().subtract(0, p.getLocation().getY(), 0)
							.distance(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation().clone()
									.subtract(0, 14, 0)) >= 115
							|| p.getLocation().getY() >= 220 || (p.getLocation().getY() <= 10))
							&& p.getGameMode() != GameMode.CREATIVE) {
						p.teleport(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation());
						p.playSound(p.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
						p.setFallDistance(0F);
					}
				}
			}
		}
	}
}
