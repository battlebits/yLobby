package br.com.battlebits.ylobby.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.yaddons.yAddonsPlugin;
import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;

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
		if (((Damageable) e.getPlayer()).getMaxHealth() != 20) {
			e.getPlayer().setMaxHealth(20);
		}
		if (((Damageable) e.getPlayer()).getHealth() != 20) {
			e.getPlayer().setHealth(20);
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				if (e.getPlayer().getLevel() != -10) {
					e.getPlayer().setLevel(-10);
				}
				if (e.getPlayer().getExp() != -100F) {
					e.getPlayer().setExp(-100F);
				}
			}
		}.runTaskLaterAsynchronously(yLobbyPlugin.getyLobby(), 20L);
		if(Main.getPlugin().getPermissionManager().hasGroupPermission(e.getPlayer().getUniqueId(), Group.LIGHT)){
			e.getPlayer().setAllowFlight(true);
			e.getPlayer().setFlying(true);
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
		yLobbyPlugin.getyLobby().getChatManager().removeFromList(e.getPlayer().getUniqueId());
		e.setQuitMessage("");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWeatherChangeListener(WeatherChangeEvent e) {
		e.setCancelled(true);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear 100000000");
	}

	// @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	// public void onEntitySpawnListener(EntitySpawnEvent e) {
	// e.setCancelled(true);
	// }

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteractListener(PlayerInteractEvent e) {
		if ((e.getAction() == Action.PHYSICAL || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void openCosmetics(PlayerInteractEvent e) {
		if (!e.isCancelled() || e.getAction() != Action.PHYSICAL) {
			if (e.getItem() != null) {
				if (e.getItem().getType() == Material.ENDER_CHEST) {
					if (e.getItem().hasItemMeta()) {
						if (e.getItem().getItemMeta().hasDisplayName()) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lCosmeticos §7(Clique)")) {
								if (Main.getPlugin().getPermissionManager().hasGroupPermission(e.getPlayer().getUniqueId(), Group.LIGHT)) {
									yAddonsPlugin.getyAddons().getSelectorInventory().open(e.getPlayer());
								} else {
									e.getPlayer().sendMessage(
											"§7Esse sistema está em fase de testes e desenvolvimento! Devido a isso, ele é apenas para jogadores com o grupo §a§lLIGHT §7ou superior!");
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void PlayerCommandPreprocessListener(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/ver") || e.getMessage().toLowerCase().startsWith("/version")
				|| e.getMessage().toLowerCase().startsWith("/help") || e.getMessage().toLowerCase().startsWith("/?")
				|| e.getMessage().toLowerCase().startsWith("/icanhasbukkit") || e.getMessage().toLowerCase().startsWith("/pl")
				|| e.getMessage().toLowerCase().startsWith("/plugins")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§7Sistema de Lobby para a §6§lBattle§r§lBits §9§lNetwork §7versão "
					+ yLobbyPlugin.getyLobby().getDescription().getVersion() + "!");
		}
	}

}