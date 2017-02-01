package br.com.battlebits.ylobby.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.gamemode.GameModeBase;
import br.com.battlebits.ylobby.gamemode.GameModeMatch;
import br.com.battlebits.ylobby.gamemode.GameModeMulti;
import br.com.battlebits.ylobby.gamemode.GameModeSimple;

public class GameModsManager {

	private Map<ServerType, GameModeBase> gameModeBases;

	public GameModsManager() {
		gameModeBases = new HashMap<>();
		loadGameMods();
	}

	public void addGameMode(GameModeBase gameModeBase) {
		gameModeBases.put(gameModeBase.getServerType(), gameModeBase);
	}

	public Collection<GameModeBase> getGameModes() {
		return gameModeBases.values();
	}

	public boolean isGameMode(ServerType type) {
		return gameModeBases.containsKey(type);
	}

	public GameModeBase getGameMode(ServerType type) {
		return gameModeBases.get(type);
	}

	public void loadGameMods() {

		addGameMode(new GameModeMulti("§%pvpfulliron-item%§", "§%pvpfulliron-item-lore%§", Material.DIAMOND_SWORD,
				new Location(Bukkit.getWorlds().get(0), 8, Bukkit.getWorlds().get(0).getHighestBlockYAt(8, 8), 8),
				new BungeeMessage("PVPFulliron"), ServerType.PVP_FULLIRON, EntityType.SKELETON) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMultiSelectorManager().getMultiSelector(getServerType()).open(p);
			}
		});

		addGameMode(new GameModeMulti("§%pvpsimulator-item%§", "§%pvpsimulator-item-lore%§", Material.WOOD_SWORD,
				new Location(Bukkit.getWorlds().get(0), -7, Bukkit.getWorlds().get(0).getHighestBlockYAt(-7, 8), 8),
				new BungeeMessage("PVPSimulator"), ServerType.PVP_SIMULATOR, EntityType.SKELETON) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMultiSelectorManager().getMultiSelector(getServerType()).open(p);
			}
		});
		addGameMode(new GameModeMatch("§%hungergames-item%§", "§%hungergames-item-lore%§", Material.MUSHROOM_SOUP,
				new Location(Bukkit.getWorlds().get(0), -8, Bukkit.getWorlds().get(0).getHighestBlockYAt(-8, -8), -8),
				new BungeeMessage("Hungergames"), ServerType.HUNGERGAMES, EntityType.BLAZE) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getMatchSelector(getServerType()).open(p);
			}
		});

		addGameMode(new GameModeMatch("§%hungergames-custom-item%§", "§%hungergames-custom-item-lore%§",
				Material.WORKBENCH, new Location(Bukkit.getWorlds().get(0), 0, 14, 12),
				new BungeeMessage("CustomHungergames"), ServerType.CUSTOMHG, EntityType.WITCH) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getMatchSelector(getServerType()).open(p);
			}
		});

		addGameMode(new GameModeMatch("§%hungergames-doublekit-item%§", "§%hungergames-doublekit-item-lore%§",
				Material.RED_MUSHROOM,
				new Location(Bukkit.getWorlds().get(0), 0, Bukkit.getWorlds().get(0).getHighestBlockYAt(0, -12), -12),
				new BungeeMessage("DoubleKitHungergames"), ServerType.DOUBLEKITHG, EntityType.CREEPER) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getMatchSelector(getServerType()).open(p);
			}
		});
		addGameMode(
				new GameModeSimple("§%raid-item%§", "§%raid-item-lore%§", Material.DIAMOND_PICKAXE,
						"raid.battlebits.com.br", new Location(Bukkit.getWorlds().get(0), 8,
								Bukkit.getWorlds().get(0).getHighestBlockYAt(8, -7), -7),
						ServerType.RAID, EntityType.ZOMBIE));
	}

	public void stop() {
		for (GameModeBase base : gameModeBases.values()) {
			base.getCharacterNPC().despawn();
		}

		gameModeBases.clear();
	}

}
