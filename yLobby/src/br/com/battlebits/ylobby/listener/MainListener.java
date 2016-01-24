package br.com.battlebits.ylobby.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class MainListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoinListener(PlayerJoinEvent e) {
		yLobbyPlugin.getyLobby().getLobbyItensManager().setItems(e.getPlayer());
		if (e.getPlayer().getGameMode() != GameMode.ADVENTURE) {
			e.getPlayer().setGameMode(GameMode.ADVENTURE);
		}
		if (e.getPlayer().getFoodLevel() != 20) {
			e.getPlayer().setFoodLevel(20);
		}
		if (((Damageable) e.getPlayer()).getMaxHealth() != 2) {
			e.getPlayer().setMaxHealth(2);
		}
		if (((Damageable) e.getPlayer()).getHealth() != 2) {
			e.getPlayer().setHealth(2);
		}
		if (e.getPlayer().getLevel() != -10) {
			e.getPlayer().setLevel(-10);
		}
		e.getPlayer().teleport(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation());
		e.setJoinMessage("");
		yLobbyPlugin.getyLobby().getScoreboardManager().setupMainScoreboard(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onFoodLevelChangeListener(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageListener(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause() == DamageCause.VOID) {
			((Player) e.getEntity()).teleport(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation());
		}
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerDropItemListener(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlaceListener(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreakListener(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerQuitListener(PlayerQuitEvent e) {
		if (e.getPlayer().getAllowFlight()) {
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setFlying(false);
		}
		e.setQuitMessage("");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWeatherChangeListener(WeatherChangeEvent e) {
		e.setCancelled(true);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear 100000000");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntitySpawnListener(EntitySpawnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteractListener(PlayerInteractEvent e) {
		if ((e.getAction() == Action.PHYSICAL || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
				e.setCancelled(true);
			}
		}
	}

}
