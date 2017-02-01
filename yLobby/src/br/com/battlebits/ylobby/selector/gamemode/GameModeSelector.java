package br.com.battlebits.ylobby.selector.gamemode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.gamemode.GameModeBase;
import br.com.battlebits.ylobby.gamemode.GameModeMatch;
import br.com.battlebits.ylobby.gamemode.GameModeMulti;
import br.com.battlebits.ylobby.gamemode.GameModeSimple;
import br.com.battlebits.ylobby.gamemode.GameModeType;

public class GameModeSelector {
	private Inventory selectorInventory;
	private HashMap<Integer, GameModeBase> serverIds;
	private ArrayList<String> serverRestartingMessage;
	private ArrayList<String> needToBeLightToJoinFull;
	private BukkitRunnable updaterRunnable;

	public GameModeSelector() {
		serverIds = new HashMap<Integer, GameModeBase>();
		serverRestartingMessage = new ArrayList<String>();
		serverRestartingMessage.add("§0");
		serverRestartingMessage.add("§7Este servidor está §8§lREINICIANDO§7! Aguarde para §b§lconectar§7!");
		serverRestartingMessage.add("§0");
		needToBeLightToJoinFull = new ArrayList<String>();
		needToBeLightToJoinFull.add("§0");
		needToBeLightToJoinFull.add("§7Você precisa ser §a§lLIGHT§7 ou superior para");
		needToBeLightToJoinFull.add("§6§lconectar§7 com o§6§l servidor cheio§7!");
		needToBeLightToJoinFull.add("§7Compre em nosso site §6§lwww.battlebits.com.br§7!");
		needToBeLightToJoinFull.add("§0");
	}

	public void starta() {
		selectorInventory = Bukkit.createInventory(null,
				LobbyUtils.getInventoryUtils()
						.getInventorySizeForItens(yLobbyPlugin.getyLobby().getGameModsManager().getGameMods().size()
								+ 18 + yLobbyPlugin.getyLobby().getGameModsManager().getGameMods().size() / 7 * 2),
				"     §nEscolha o Modo de Jogo");
		int i = 10;
		for (GameModeBase gameModeBase : yLobbyPlugin.getyLobby().getGameModsManager().getGameMods()) {
			if ((i == 8) || (i == 17) || (i == 26) || (i == 35) || (i == 44)) {
				i += 2;
			}
			serverIds.put(Integer.valueOf(i), gameModeBase);
			i++;
		}
		startInventoryUpdater();
	}

	public void stop() {
		if (updaterRunnable != null) {
			updaterRunnable.cancel();
		}
		serverIds.clear();
		serverRestartingMessage.clear();
		needToBeLightToJoinFull.clear();
	}

	public void open(Player p) {
		if (selectorInventory != null) {
			p.openInventory(selectorInventory);
		}
	}

	public void tryToConnect(Player p, int i, boolean right) {
		if (serverIds.containsKey(i)) {
			GameModeBase gm = serverIds.get(i);
			if (gm.getGameModeType() == GameModeType.SIMPLE) {
				GameModeSimple gamemmode = (GameModeSimple) gm;
				gamemmode.connect(p);
			} else if (gm.getGameModeType() == GameModeType.MATCH) {
				GameModeMatch server = (GameModeMatch) gm;
				if (right) {
					server.onRightClick(p);
				} else {
					server.onLeftClick(p);
				}
			} else if (gm.getGameModeType() == GameModeType.MULTI) {
				GameModeMulti server = (GameModeMulti) gm;
				if (right) {
					server.onRightClick(p);
				} else {
					server.onLeftClick(p);
				}
			}
		}
	}

	public void startInventoryUpdater() {
		updaterRunnable = new BukkitRunnable() {
			public void run() {
				for (Entry<Integer, GameModeBase> entry : serverIds.entrySet()) {
					GameModeBase gameModeBase = (GameModeBase) entry.getValue();
					gameModeBase.updateOnlinePlayersOnItem();
					selectorInventory.setItem(entry.getKey(), gameModeBase.getInventoryItem());
				}
			}
		};
		updaterRunnable.runTaskTimer(yLobbyPlugin.getyLobby(), 1L, 30L);
	}
}
