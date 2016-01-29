package br.com.battlebits.ylobby.manager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.gamemode.GameModeBase;
import br.com.battlebits.ylobby.gamemode.GameModeMatch;
import br.com.battlebits.ylobby.gamemode.GameModeSimple;
import br.com.battlebits.yutils.character.CharacterType;

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
		addGameMode(new GameModeSimple("BattleCraft", "Servidor de KitPvP com sopa feito para todos lutarem com armaduras de ferro e tirar 1v1s",
				Material.DIAMOND_SWORD, "battlecraft.com.br", new Location(Bukkit.getWorlds().get(0), 2, 65, 35), CharacterType.SKELETON));
		addGameMode(new GameModeSimple("BattleCraft V2",
				"Novo servidor de KitPvP com sopa feito para todos usarem estrategias e lutarem sem armadura em um estilo mais Hardcore",
				Material.GOLD_SWORD, "beta.battlecraft.com.br", new Location(Bukkit.getWorlds().get(0), -1, 65, 35), CharacterType.WITHERSKELETON));
		addGameMode(new GameModeMatch("Battle-HG",
				"Servidores de HungerGames.\\nSeja o ultimo sobrevivente em uma luta brutal com kits onde apenas um sera o campeao",
				Material.MUSHROOM_SOUP, new Location(Bukkit.getWorlds().get(0), -34, 65, 0), CharacterType.BLAZE, new BungeeMessage("Hungergames")) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getPlayerCountManager().getHgOnlinePlayers();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getHardcoreGamesSelector().open(p);
			}
		});
		addGameMode(new GameModeMatch("FairPlayHG", "Jogue o HungerGames normal com otimizacoes para remover times.", Material.IRON_FENCE,
				new Location(Bukkit.getWorlds().get(0), 35, 65, 0), CharacterType.PIGZOMBIE, new BungeeMessage("Fairplayhg")) {
			@Override
			public int getOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getPlayerCountManager().getFpOnlinePlayers();
			}

			@Override
			public void onRightClick(Player p) {
				yLobbyPlugin.getyLobby().getMatchSelectorManager().getFairPlaySelector().open(p);
			}
		});
		addGameMode(new GameModeSimple("BattleRaid", "Crie seu time, minere e monte sua base para destruir as bases inimigas e ser o mais forte.",
				Material.DIAMOND_PICKAXE, "raid.battlebits.com.br", new Location(Bukkit.getWorlds().get(0), 0, 65, -34), CharacterType.ZOMBIE));
	}

	public void stop() {
		for (GameModeBase base : gameModeBases) {
			base.getCharacterNPC().remove();
		}
		gameModeBases.clear();
	}

}
