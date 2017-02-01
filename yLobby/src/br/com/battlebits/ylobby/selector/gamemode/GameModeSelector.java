package br.com.battlebits.ylobby.selector.gamemode;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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

	public GameModeSelector() {
		serverIds = new HashMap<Integer, GameModeBase>();
	}

	public void start() {
		selectorInventory = Bukkit.createInventory(null,
				LobbyUtils.getInventoryUtils()
						.getInventorySizeForItensOld(yLobbyPlugin.getyLobby().getGameModsManager().getGameModes().size()
								+ 18 + yLobbyPlugin.getyLobby().getGameModsManager().getGameModes().size() / 7 * 2),
				"§nEscolha o Modo de Jogo");
		int i = 10;
		// TODO ADD COMPARATOR
		for (GameModeBase gameModeBase : yLobbyPlugin.getyLobby().getGameModsManager().getGameModes()) {
			if ((i == 8) || (i == 17) || (i == 26) || (i == 35) || (i == 44)) {
				i += 2;
			}
			serverIds.put(Integer.valueOf(i), gameModeBase);
			i++;
		}
		for (Entry<Integer, GameModeBase> entry : serverIds.entrySet()) {
			GameModeBase gameModeBase = (GameModeBase) entry.getValue();
			selectorInventory.setItem(entry.getKey(), gameModeBase.getInventoryItem());
		}
	}

	public void stop() {
		serverIds.clear();
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

}
