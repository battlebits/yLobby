package br.com.battlebits.ylobby.selector.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.server.ServerInfo;

public class LobbySelector {

	private Inventory selectorInventory;
	private HashMap<Integer, String> lobbyIds;
	private ArrayList<String> connectingMessage;
	private ArrayList<String> connectedMessage;
	private ArrayList<String> serverRestartingMessage;
	private ArrayList<String> needToBeLightToJoinFull;
	private BukkitRunnable updaterRunnable;

	public LobbySelector() {
		lobbyIds = new HashMap<>();
	}

	public void start() {
		selectorInventory = Bukkit.createInventory(null, LobbyUtils.getInventoryUtils()
				.getInventorySizeForItens(yLobbyPlugin.getyLobby().getBungeeManager().getLobbyservers().size() + 18
						+ ((yLobbyPlugin.getyLobby().getBungeeManager().getLobbyservers().size() / 7) * 2)),
				"§%choose-lobby%§");
		int i = 10;
		int id = 0;
		for (String ip : yLobbyPlugin.getyLobby().getBungeeManager().getLobbyservers()) {
			if (i == 8 || i == 17 || i == 26 || i == 35 || i == 44) {
				i = i + 2;
			}
			lobbyIds.put(i, ip);
			i += 1;
			id += 1;
			if (ip.equals(BattlebitsAPI.getServerId())) {
				yLobbyPlugin.getyLobby().getBungeeManager().setLobbyID("#" + id);
			}
		}
		startInventoryUpdater();
	}

	public void stop() {
		if (updaterRunnable != null) {
			updaterRunnable.cancel();
		}
		lobbyIds.clear();
		connectingMessage.clear();
		serverRestartingMessage.clear();
		needToBeLightToJoinFull.clear();
	}

	public void open(Player p) {
		if (selectorInventory != null) {
			p.openInventory(selectorInventory);
		}
	}

	public void tryToConnect(Player p, int i) {
		if (lobbyIds.containsKey(i)) {
			ServerInfo info = yLobbyPlugin.getyLobby().getServerInfoManager().get(lobbyIds.get(i));
			if (info.getIp().equalsIgnoreCase(BattlebitsAPI.getServerId())) {
				for (String msg : connectedMessage) {
					p.sendMessage(msg);
				}
			} else {
				p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord",
						new BungeeMessage("Connect", info.getIp()).getDataOutput().toByteArray());
			}
		}
	}

	public void startInventoryUpdater() {
		updaterRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				int id = 1;
				for (Entry<Integer, String> entry : lobbyIds.entrySet()) {
					ServerInfo info = yLobbyPlugin.getyLobby().getServerInfoManager().get(entry.getValue());
					ItemStack stack = new ItemStack(Material.INK_SACK, 1);
					ItemMeta meta = stack.getItemMeta();
					ArrayList<String> lore = new ArrayList<>();
					lore.add("§3§l" + info.getOnlinePlayers() + " §7"
							+ ((info.getOnlinePlayers() == 1) ? "§%player-connected%§" : "§%players-connected%§"));
					lore.add("§0");
					if (info.getIp().equalsIgnoreCase(BattlebitsAPI.getServerId())) {
						stack.setAmount(id);
						stack.setDurability((short) 10);
						meta.setDisplayName("§9§l> §a§lLobby " + id + " §9§l<");
						lore.add("§%already-connected%§");
					} else {
						if (info.getOnlinePlayers() >= info.getMaxPlayers()) {
							stack.setAmount(id);
							stack.setDurability((short) 14);
							meta.setDisplayName("§9§l> §6§lLobby " + id + " §9§l<");
							lore.add("§%lobby-server-full%§");
							lore.add("§0");
							lore.add("§%click-to-connect%§");
						} else {
							stack.setAmount(id);
							stack.setDurability((short) 6);
							meta.setDisplayName("§9§l> §a§lLobby " + id + " §9§l<");
							lore.add("§%click-to-connect%§");
						}
					}
					meta.setLore(lore);
					stack.setItemMeta(meta);
					selectorInventory.setItem(entry.getKey(), stack);
					id = id + 1;
				}
			}
		};
		updaterRunnable.runTaskTimer(yLobbyPlugin.getyLobby(), 1L, 40L);
	}

}
