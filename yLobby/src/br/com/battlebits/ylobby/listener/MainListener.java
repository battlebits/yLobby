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
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.bukkit.event.redis.RedisPubSubMessageEvent;
import br.com.battlebits.commons.core.data.DataServer.DataServerMessage;
import br.com.battlebits.commons.core.data.DataServer.DataServerMessage.StartPayload;
import br.com.battlebits.commons.core.data.DataServer.DataServerMessage.StopPayload;
import br.com.battlebits.commons.core.data.DataServer.DataServerMessage.UpdatePayload;
import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.commons.core.server.loadbalancer.server.BattleServer;
import br.com.battlebits.commons.core.server.loadbalancer.server.MinigameServer;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.updater.TabAndHeaderUpdater;

public class MainListener implements Listener {

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
		}.runTaskLater(yLobbyPlugin.getyLobby(), 20L);
		e.getPlayer().teleport(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation());
		e.setJoinMessage("");
		yLobbyPlugin.getyLobby().getScoreboardManager().setupMainScoreboard(e.getPlayer());
		for (PotionEffect pot : e.getPlayer().getActivePotionEffects()) {
			e.getPlayer().removePotionEffect(pot.getType());
		}
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
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear 1000000");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteractListener(PlayerInteractEvent e) {
		if ((e.getAction() == Action.PHYSICAL || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void PlayerCommandPreprocessListener(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/ver") || e.getMessage().toLowerCase().startsWith("/version")
				|| e.getMessage().toLowerCase().startsWith("/help") || e.getMessage().toLowerCase().startsWith("/?")
				|| e.getMessage().toLowerCase().startsWith("/icanhasbukkit")
				|| e.getMessage().toLowerCase().startsWith("/pl")
				|| e.getMessage().toLowerCase().startsWith("/plugins")) {
			// e.setCancelled(true);
			// e.getPlayer().sendMessage("§7Sistema de Lobby para a
			// §6§lBattle§r§lBits §9§lNetwork §7versão " +
			// yLobbyPlugin.getyLobby().getDescription().getVersion() + "!");
		}
	}

	@EventHandler
	public void onRedisMessage(RedisPubSubMessageEvent event) {
		if (!event.getChannel().equals("server-info"))
			return;
		if (event.getMessage() == null || event.getMessage().isEmpty())
			return;
		String message = event.getMessage();
		JsonObject jsonObject = BattlebitsAPI.getParser().parse(message).getAsJsonObject();
		String source = jsonObject.get("source").getAsString();
		if (source.equals(BattlebitsAPI.getServerId()))
			return;
		ServerType sourceType = ServerType.getServerType(source);
		DataServerMessage.Action action = DataServerMessage.Action.valueOf(jsonObject.get("action").getAsString());
		switch (action) {
		case JOIN: {
			BattleServer server = yLobbyPlugin.getyLobby().getServerManager().getServer(source);
			if (server == null) {
				System.out.println("Servidor " + source + " do tipo " + sourceType + " está nulo no plugin do yLobby");
				break;
			}
			server.setOnlinePlayers(server.getOnlinePlayers() + 1);
			if (yLobbyPlugin.getyLobby().getGameModsManager().isGameMode(sourceType))
				yLobbyPlugin.getyLobby().getGameModsManager().getGameMode(sourceType).updateOnlinePlayersOnItem();
			if (yLobbyPlugin.getyLobby().getMatchSelectorManager().isMatchSelector(sourceType))
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getMatchSelector(sourceType).update();
			if (yLobbyPlugin.getyLobby().getMultiSelectorManager().isMultiSelector(sourceType))
				yLobbyPlugin.getyLobby().getMultiSelectorManager().getMultiSelector(sourceType).update();
			if (sourceType == ServerType.LOBBY) {
				yLobbyPlugin.getyLobby().getLobbySelector().update();
			} else if (sourceType == ServerType.NETWORK) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					TabAndHeaderUpdater.send(p);
					yLobbyPlugin.getyLobby().getScoreboardManager().updateMainScoreboard(p);
				}
			}
			break;
		}
		case LEAVE: {
			BattleServer server = yLobbyPlugin.getyLobby().getServerManager().getServer(source);
			server.setOnlinePlayers(server.getOnlinePlayers() - 1);
			if (yLobbyPlugin.getyLobby().getGameModsManager().isGameMode(sourceType))
				yLobbyPlugin.getyLobby().getGameModsManager().getGameMode(sourceType).updateOnlinePlayersOnItem();
			if (yLobbyPlugin.getyLobby().getMatchSelectorManager().isMatchSelector(sourceType))
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getMatchSelector(sourceType).update();
			if (yLobbyPlugin.getyLobby().getMultiSelectorManager().isMultiSelector(sourceType))
				yLobbyPlugin.getyLobby().getMultiSelectorManager().getMultiSelector(sourceType).update();
			if (sourceType == ServerType.LOBBY) {
				yLobbyPlugin.getyLobby().getLobbySelector().update();
			} else if (sourceType == ServerType.NETWORK) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					TabAndHeaderUpdater.send(p);
					yLobbyPlugin.getyLobby().getScoreboardManager().updateMainScoreboard(p);
				}
			}
			break;
		}
		case START: {
			DataServerMessage<StartPayload> payload = BattlebitsAPI.getGson().fromJson(jsonObject,
					new TypeToken<DataServerMessage<StartPayload>>() {
					}.getType());
			yLobbyPlugin.getyLobby().getServerManager().addActiveServer(payload.getPayload().getServerAddress(),
					payload.getPayload().getServer().getServerId(), payload.getPayload().getServer().getMaxPlayers());
			if (yLobbyPlugin.getyLobby().getGameModsManager().isGameMode(sourceType))
				yLobbyPlugin.getyLobby().getGameModsManager().getGameMode(sourceType).updateOnlinePlayersOnItem();
			if (yLobbyPlugin.getyLobby().getMatchSelectorManager().isMatchSelector(sourceType))
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getMatchSelector(sourceType).update();
			if (yLobbyPlugin.getyLobby().getMultiSelectorManager().isMultiSelector(sourceType))
				yLobbyPlugin.getyLobby().getMultiSelectorManager().getMultiSelector(sourceType).update();
			if (sourceType == ServerType.LOBBY)
				yLobbyPlugin.getyLobby().getLobbySelector().update();
			break;
		}
		case STOP: {
			DataServerMessage<StopPayload> payload = BattlebitsAPI.getGson().fromJson(jsonObject,
					new TypeToken<DataServerMessage<StopPayload>>() {
					}.getType());
			yLobbyPlugin.getyLobby().getServerManager().removeActiveServer(payload.getPayload().getServerId());
			if (yLobbyPlugin.getyLobby().getGameModsManager().isGameMode(sourceType))
				yLobbyPlugin.getyLobby().getGameModsManager().getGameMode(sourceType).updateOnlinePlayersOnItem();
			if (yLobbyPlugin.getyLobby().getMatchSelectorManager().isMatchSelector(sourceType))
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getMatchSelector(sourceType).update();
			if (yLobbyPlugin.getyLobby().getMultiSelectorManager().isMultiSelector(sourceType))
				yLobbyPlugin.getyLobby().getMultiSelectorManager().getMultiSelector(sourceType).update();
			if (sourceType == ServerType.LOBBY)
				yLobbyPlugin.getyLobby().getLobbySelector().update();
			break;
		}
		case UPDATE: {
			DataServerMessage<UpdatePayload> payload = BattlebitsAPI.getGson().fromJson(jsonObject,
					new TypeToken<DataServerMessage<UpdatePayload>>() {
					}.getType());
			BattleServer server = yLobbyPlugin.getyLobby().getServerManager().getServer(source);
			if (server instanceof MinigameServer) {
				((MinigameServer) server).setState(payload.getPayload().getState());
				((MinigameServer) server).setTime(payload.getPayload().getTime());
			}
			if (yLobbyPlugin.getyLobby().getGameModsManager().isGameMode(sourceType))
				yLobbyPlugin.getyLobby().getGameModsManager().getGameMode(sourceType).updateOnlinePlayersOnItem();
			if (yLobbyPlugin.getyLobby().getMatchSelectorManager().isMatchSelector(sourceType))
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getMatchSelector(sourceType).update();
			if (yLobbyPlugin.getyLobby().getMultiSelectorManager().isMultiSelector(sourceType))
				yLobbyPlugin.getyLobby().getMultiSelectorManager().getMultiSelector(sourceType).update();
			if (sourceType == ServerType.LOBBY)
				yLobbyPlugin.getyLobby().getLobbySelector().update();
			break;
		}
		default:
			break;
		}
	}

}