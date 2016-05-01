package br.com.battlebits.ylobby.selector.gamemode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ycommon.common.BattlebitsAPI;
import br.com.battlebits.ycommon.common.permissions.enums.Group;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.gamemode.GameModeBase;
import br.com.battlebits.ylobby.gamemode.GameModeMatch;
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
		serverRestartingMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
				.centerChatMessage("§7Este servidor está §8§lREINICIANDO§7! Aguarde para §b§lconectar§7!"));
		serverRestartingMessage.add("§0");
		needToBeLightToJoinFull = new ArrayList<String>();
		needToBeLightToJoinFull.add("§0");
		needToBeLightToJoinFull
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você precisa ser §a§lLIGHT§7 ou superior para"));
		needToBeLightToJoinFull
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§6§lconectar§7 com o§6§l servidor cheio§7!"));
		needToBeLightToJoinFull
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Compre em nosso site §6§lwww.battlebits.com.br§7!"));
		needToBeLightToJoinFull.add("§0");
	}

	public void start() {
		selectorInventory = Bukkit.createInventory(null,
				yLobbyPlugin.getyLobby().getzUtils().getInventoryUtils()
						.getInventorySizeForItens(yLobbyPlugin.getyLobby().getGameModsManager().getGameMods().size() + 18
								+ yLobbyPlugin.getyLobby().getGameModsManager().getGameMods().size() / 7 * 2),
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
				if (gamemmode.getOnlinePlayers() >= gamemmode.getServerInfo().getMaxPlayers()) {
					if (BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId()).hasGroupPermission(Group.LIGHT)) {
						p.sendMessage("§0");
						p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
								.centerChatMessage("§9§lConectando §7ao §9§l" + gamemmode.getServerName() + "§7!"));
						p.sendMessage("§0");
						gamemmode.connect(p);
					} else {
						for (String msg : needToBeLightToJoinFull) {
							p.sendMessage(msg);
						}
					}
				} else {
					p.sendMessage("§0");
					p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
							.centerChatMessage("§9§lConectando §7ao servidor §9§l" + gamemmode.getServerName() + "§7!"));
					p.sendMessage("§0");
					gamemmode.connect(p);
				}
			} else if (gm.getGameModeType() == GameModeType.MATCH) {
				GameModeMatch server = (GameModeMatch) gm;
				if (right) {
					server.onRightClick(p);
				} else {
					p.sendMessage("§0");
					p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
							.centerChatMessage("§9§lConectando §7ao servidor §9§l" + server.getServerName() + "§7!"));
					p.sendMessage("§0");
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
					selectorInventory.setItem(((Integer) entry.getKey()).intValue(), gameModeBase.getInventoryItem());
				}
			}
		};
		updaterRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 200L, 30L);
	}
}
