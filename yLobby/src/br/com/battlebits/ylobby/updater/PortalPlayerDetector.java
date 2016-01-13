package br.com.battlebits.ylobby.updater;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class PortalPlayerDetector {

	private BukkitRunnable detectorRunnable;

	public PortalPlayerDetector() {
		detectorRunnable = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().length > 0) {
					for (final Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().getBlock().getType() == Material.PORTAL) {
							p.setVelocity(p.getEyeLocation().clone().getDirection().multiply(1.0F).setY(-0.15));
							p.closeInventory();
							new BukkitRunnable() {
								@Override
								public void run() {
									yLobbyPlugin.getyLobby().getGameModeSelector().open(p);
								}
							}.runTaskLaterAsynchronously(yLobbyPlugin.getyLobby(), 3l);
						}
					}
				}

			}
		};
		detectorRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 1L);
	}

	public void stop() {
		if (detectorRunnable != null) {
			detectorRunnable.cancel();
		}
	}

}
