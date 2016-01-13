package br.com.battlebits.ylobby.updater;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class LeatherArmorUpdater {

	private BukkitRunnable updaterRunnble;

	public LeatherArmorUpdater() {
		updaterRunnble = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().length > 0) {
					nextRGB();
					Color color = Color.fromRGB(r, g, b);
					for (final Player p : Bukkit.getOnlinePlayers()) {
						ItemStack stack = new ItemStack(Material.LEATHER_CHESTPLATE);
						LeatherArmorMeta armourmeta = (LeatherArmorMeta) stack.getItemMeta();
						armourmeta.setColor(color);
						stack.setItemMeta(armourmeta);
						p.getInventory().setChestplate(stack);
					}
				}

			}
		};
		updaterRunnble.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 0L, 0L);
	}

	private int r = 255;
	private int g = 0;
	private int b = 0;

	private void nextRGB() {
		if (r == 255 && g < 255 && b == 0) {
			g++;
		}
		if (g == 255 && r > 0 && b == 0) {
			r--;
		}
		if (g == 255 && b < 255 && r == 0) {
			b++;
		}
		if (b == 255 && g > 0 && r == 0) {
			g--;
		}
		if (b == 255 && r < 255 && g == 0) {
			r++;
		}
		if (r == 255 && b > 0 && g == 0) {
			b--;
		}
	}

	public void stop() {
		if (updaterRunnble != null) {
			updaterRunnble.cancel();
		}
	}

}
