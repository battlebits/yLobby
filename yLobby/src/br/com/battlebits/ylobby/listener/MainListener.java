package br.com.battlebits.ylobby.listener;

import java.util.UUID;

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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.yaddons.yAddonsPlugin;
import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;

public class MainListener implements Listener {

	@EventHandler
	public void onAsyncPreLoginListener(AsyncPlayerPreLoginEvent e) {
		try {
			yLobbyPlugin.getyLobby().getForumManager().loadForumID(e.getUniqueId());
		} catch (Exception ex) {
			e.disallow(Result.KICK_OTHER, "§cOcorreu um erro ao carregar sua conta!");
		}
	}

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
		// if
		// (Main.getPlugin().getPermissionManager().hasGroupPermission(e.getPlayer().getUniqueId(),
		// Group.LIGHT)) {
		// e.getPlayer().setAllowFlight(true);
		// e.getPlayer().setFlying(true);
		// }
		e.getPlayer().teleport(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation());
		e.setJoinMessage("");
		yLobbyPlugin.getyLobby().getScoreboardManager().setupMainScoreboard(e.getPlayer());
		for (PotionEffect pot : e.getPlayer().getActivePotionEffects()) {
			e.getPlayer().removePotionEffect(pot.getType());
		}
	}

	// @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	// public void onVipExpire(IW4PostExpiredPlayerEvent e) {
	// for (IW4OrderProduct product : e.getExpiredPackages().values()) {
	// if (product.getProductName().toLowerCase().startsWith("vip")) {
	// Player p = Bukkit.getPlayer(e.getPlayer().getUUID());
	// if (p.getAllowFlight()) {
	// p.setAllowFlight(false);
	// }
	// yAddonsPlugin.getyAddons().getAccountManager().getAccount(p).removeCurrentGadget();
	// yAddonsPlugin.getyAddons().getAccountManager().getAccount(p).removeCurrentHat();
	// yAddonsPlugin.getyAddons().getAccountManager().getAccount(p).removeCurrentParticle();
	// yAddonsPlugin.getyAddons().getAccountManager().getAccount(p).removeCurrentPet();
	// yAddonsPlugin.getyAddons().getAccountManager().getAccount(p).removeCurrentMorph();
	// break;
	// }
	// }
	// }

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
		yLobbyPlugin.getyLobby().getForumManager().handleQuit(e.getPlayer().getUniqueId());
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

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onAsyncPlayerChatListener(AsyncPlayerChatEvent e) {
		if (!e.getMessage().startsWith("Damage Indicators")) {
			if (yLobbyPlugin.getyLobby().getChatManager().isChatEnabled(e.getPlayer().getUniqueId())) {
				if (!(e.getMessage().startsWith("@")
						&& Main.getPlugin().getPermissionManager().hasGroupPermission(e.getPlayer().getUniqueId(), Group.MOD))) {
					for (UUID id : yLobbyPlugin.getyLobby().getChatManager().getChatDisabledPlayers()) {
						e.getRecipients().remove(Bukkit.getPlayer(id));
					}
				} else {
					if (e.getMessage().length() > 1) {
						String str = e.getMessage().substring(1, e.getMessage().length());
						if (str.length() > 0) {
							e.setMessage(str);
						} else {
							e.setCancelled(true);
						}
					} else {
						e.setCancelled(true);
					}
				}
			} else {
				yLobbyPlugin.getyLobby().getChatManager().chatDisabledMessage(e.getPlayer());
			}
		} else {
			e.setCancelled(true);
		}
	}

}