package br.com.battlebits.ylobby.manager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.gamemode.GameModeBase;
import br.com.battlebits.ylobby.gamemode.GameModeMatch;
import br.com.battlebits.ylobby.gamemode.GameModeMulti;
import br.com.battlebits.ylobby.gamemode.GameModeSimple;

public class GameModsManager {

	private ArrayList<GameModeBase> gameModeBases;

	public GameModsManager() {
		gameModeBases = new ArrayList<>();
		loadGameMods();
	}

	public void addGameMode(GameModeBase gameModeBase) {
		gameModeBases.add(gameModeBase);
	}

	public ArrayList<GameModeBase> getGameMods() {
		return gameModeBases;
	}

	public void loadGameMods() {

		addGameMode(
				new GameModeMulti("BattleCraft FullIron",
						"Novo servidor de KitPvP com sopa feito para todos lutarem com armaduras de ferro, batalhando com espadas com mais dano\\n\\n§5§lAgora com Void Challenge",
						Material.DIAMOND_SWORD, new Location(Bukkit.getWorlds().get(0), 8,
								Bukkit.getWorlds().get(0).getHighestBlockYAt(8, 8), 8),
						new BungeeMessage("PVPFulliron"), EntityType.SKELETON) {
					@Override
					public int getOnlinePlayers() {
						return yLobbyPlugin.getyLobby().getPlayerCountManager().getFullIronOnlinePlayers();
					}

					@Override
					public void onRightClick(Player p) {
						yLobbyPlugin.getyLobby().getMultiSelectorManager().getPvPFullironSelector().open(p);
					}
				});

		addGameMode(
				new GameModeMulti("BattleCraft Simulator",
						"Novo servidor de KitPvP com sopa feito para todos usarem estrategias e lutarem sem armadura em um estilo mais Hardcore simulando o estilo do HG\\n\\n§5§lAgora com Void Challenge",
						Material.WOOD_SWORD, new Location(Bukkit.getWorlds().get(0), -7,
								Bukkit.getWorlds().get(0).getHighestBlockYAt(-7, 8), 8),
						new BungeeMessage("PVPSimulator"), EntityType.SKELETON) {
					@Override
					public int getOnlinePlayers() {
						return yLobbyPlugin.getyLobby().getPlayerCountManager().getSimulatorOnlinePlayers();
					}

					@Override
					public void onRightClick(Player p) {
						yLobbyPlugin.getyLobby().getMultiSelectorManager().getPvPSimulatorSelector().open(p);
					}
				});

		// addGameMode(new GameModeSimple("BattleCraft", "Servidor de KitPvP com
		// sopa feito para todos lutarem com armaduras de ferro e tirar 1v1s",
		// Material.DIAMOND_SWORD, "battlecraft.com.br", new
		// Location(Bukkit.getWorlds().get(0), 0, 65, 37),
		// CharacterType.WITCH));
		addGameMode(new GameModeMatch("Battle-HG",
				"Servidores de HungerGames.\\nSeja o ultimo sobrevivente em uma luta brutal com kits onde apenas um sera o campeao",
				Material.MUSHROOM_SOUP,
				new Location(Bukkit.getWorlds().get(0), -8, Bukkit.getWorlds().get(0).getHighestBlockYAt(-8, -8), -8),
				new BungeeMessage("Hungergames"), EntityType.BLAZE) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getPlayerCountManager().getHgOnlinePlayers();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getHardcoreGamesSelector().open(p);
			}
		});

		addGameMode(new GameModeMatch("CustomHG",
				"Servidores de HungerGames.\\nSeja o ultimo sobrevivente em uma luta brutal com kits onde apenas um sera o campeao",
				Material.WORKBENCH, new Location(Bukkit.getWorlds().get(0), 0, 14, 12),
				new BungeeMessage("CustomHungergames"), EntityType.WITCH) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getPlayerCountManager().getCustomHgOnlinePlayers();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getCustomHGSelector().open(p);
			}
		});

		addGameMode(
				new GameModeMatch("Battle-HG DoubleKit",
						"Servidores de HardcoreGames.\\nSeja o ultimo sobrevivente em uma luta brutal com kits onde apenas um sera o campeao",
						Material.RED_MUSHROOM, new Location(Bukkit.getWorlds().get(0), 0,
								Bukkit.getWorlds().get(0).getHighestBlockYAt(0, -12), -12),
						new BungeeMessage("DoubleKitHungergames"), EntityType.CREEPER) {
					@Override
					public int getOnlinePlayers() {
						return yLobbyPlugin.getyLobby().getPlayerCountManager().getDoubleKitOnlinePlayers();
					}

					@Override
					public void onRightClick(Player p) {
						yLobbyPlugin.getyLobby().getMatchSelectorManager().getDoubleKitHgSelector().open(p);
					}
				});
		addGameMode(new GameModeSimple("BattleRaid",
				"Crie seu time, minere e monte sua base para destruir as bases inimigas e ser o mais forte.",
				Material.DIAMOND_PICKAXE, "raid.battlebits.com.br",
				new Location(Bukkit.getWorlds().get(0), 8, Bukkit.getWorlds().get(0).getHighestBlockYAt(8, -7), -7),
				EntityType.ZOMBIE));
	}

	public void stop() {

		for (GameModeBase base : gameModeBases) {
			base.getCharacterNPC().despawn();
		}

		gameModeBases.clear();
	}

}
