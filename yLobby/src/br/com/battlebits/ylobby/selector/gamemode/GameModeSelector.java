package br.com.battlebits.ylobby.selector.gamemode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.battlebits.commons.api.menu.ClickType;
import br.com.battlebits.commons.api.menu.MenuClickHandler;
import br.com.battlebits.commons.api.menu.MenuInventory;
import br.com.battlebits.commons.api.menu.MenuItem;
import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.gamemode.GameModeBase;
import br.com.battlebits.ylobby.gamemode.GameModeMatch;
import br.com.battlebits.ylobby.gamemode.GameModeMulti;
import br.com.battlebits.ylobby.gamemode.GameModeSimple;
import br.com.battlebits.ylobby.gamemode.GameModeType;

public class GameModeSelector {
	private MenuInventory selectorInventory;
	private HashMap<Integer, GameModeBase> serverIds;

	public GameModeSelector() {
		serverIds = new HashMap<Integer, GameModeBase>();
	}

	public void start() {
		selectorInventory = new MenuInventory("§n§%choose-game-mode%§",
				LobbyUtils.getInventoryUtils()
						.getInventorySizeForItensOld(LobbyMain.getInstance().getGameModsManager().getGameModes().size()
								+ 18 + LobbyMain.getInstance().getGameModsManager().getGameModes().size() / 7 * 2));
		int i = 10;
		List<GameModeBase> bases = new ArrayList<>(LobbyMain.getInstance().getGameModsManager().getGameModes());
		Collections.sort(bases, new Comparator<GameModeBase>() {
			@Override
			public int compare(GameModeBase o1, GameModeBase o2) {
				if (o1.getServerType() == ServerType.PVP_FULLIRON)
					return -1;
				if (o2.getServerType() == ServerType.PVP_FULLIRON)
					return 1;
				if (o1.getServerType() == ServerType.PVP_SIMULATOR)
					return -1;
				if (o2.getServerType() == ServerType.PVP_SIMULATOR)
					return 1;
				if (o1.getServerType() == ServerType.HUNGERGAMES)
					return -1;
				if (o2.getServerType() == ServerType.HUNGERGAMES)
					return 1;
				if (o1.getServerType() == ServerType.CUSTOMHG)
					return -1;
				if (o2.getServerType() == ServerType.CUSTOMHG)
					return 1;
				if (o1.getServerType() == ServerType.DOUBLEKITHG)
					return -1;
				if (o2.getServerType() == ServerType.DOUBLEKITHG)
					return 1;
				if (o1.getServerType() == ServerType.RAID)
					return -1;
				if (o2.getServerType() == ServerType.RAID)
					return 1;
				return 0;
			}
		});
		for (GameModeBase gameModeBase : bases) {
			if ((i == 8) || (i == 17) || (i == 26) || (i == 35) || (i == 44)) {
				i += 2;
			}
			serverIds.put(Integer.valueOf(i), gameModeBase);
			i++;
		}
		for (Entry<Integer, GameModeBase> entry : serverIds.entrySet()) {
			GameModeBase gameModeBase = (GameModeBase) entry.getValue();
			selectorInventory.setItem(entry.getKey(),
					new MenuItem(gameModeBase.getInventoryItem(), new MenuClickHandler() {

						@Override
						public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
							tryToConnect(p, slot, type == ClickType.RIGHT);
						}
					}));
		}
	}

	public void stop() {
		serverIds.clear();
	}

	public void open(Player p) {
		if (selectorInventory != null) {
			selectorInventory.open(p);
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
