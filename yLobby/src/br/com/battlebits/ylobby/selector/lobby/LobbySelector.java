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
import br.com.battlebits.commons.core.permission.Group;
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
		serverRestartingMessage = new ArrayList<>();
		serverRestartingMessage.add("§0");
		serverRestartingMessage.add(LobbyUtils.getMessageUtils()
				.centerChatMessage("§7Este servidor está §8§lREINICIANDO§7! Aguarde para §b§lconectar§7!"));
		serverRestartingMessage.add("§0");
		needToBeLightToJoinFull = new ArrayList<>();
		needToBeLightToJoinFull.add("§0");
		needToBeLightToJoinFull
				.add(LobbyUtils.getMessageUtils().centerChatMessage("§7Você precisa ser §a§lLIGHT§7 ou superior para"));
		needToBeLightToJoinFull
				.add(LobbyUtils.getMessageUtils().centerChatMessage("§6§lconectar§7 com o§6§l servidor cheio§7!"));
		needToBeLightToJoinFull.add(
				LobbyUtils.getMessageUtils().centerChatMessage("§7Compre em nosso site §6§lwww.battlebits.com.br§7!"));
		needToBeLightToJoinFull.add("§0");
		connectingMessage = new ArrayList<>();
		connectingMessage.add("§0");
		connectingMessage.add(LobbyUtils.getMessageUtils().centerChatMessage("§7Conectando ao §3§lLobby§7!"));
		connectingMessage.add("§0");
		connectedMessage = new ArrayList<>();
		connectedMessage.add("§0");
		connectedMessage.add(
				LobbyUtils.getMessageUtils().centerChatMessage("§7Você já está §a§lconectado§7 a esse §a§lLobby§7!"));
		connectedMessage.add("§0");
	}

	public void start() {
		selectorInventory = Bukkit.createInventory(null, LobbyUtils.getInventoryUtils()
				.getInventorySizeForItens(yLobbyPlugin.getyLobby().getBungeeManager().getLobbyservers().size() + 18
						+ ((yLobbyPlugin.getyLobby().getBungeeManager().getLobbyservers().size() / 7) * 2)),
				"          §nEscolha o Lobby");
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
				if (info.getOnlinePlayers() >= 100) {
					if (BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId())
							.hasGroupPermission(Group.LIGHT)) {
						for (String msg : connectingMessage) {
							p.sendMessage(msg);
						}
						p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord",
								new BungeeMessage("Connect", info.getIp()).getDataOutput().toByteArray());
					} else {
						for (String msg : needToBeLightToJoinFull) {
							p.sendMessage(msg);
						}
					}
				} else {
					for (String msg : connectingMessage) {
						p.sendMessage(msg);
					}
					p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord",
							new BungeeMessage("Connect", info.getIp()).getDataOutput().toByteArray());
				}
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
							+ ((info.getOnlinePlayers() == 1) ? "jogador conectado" : "jogadores conectados"));
					lore.add("§0");
					if (info.getIp().equalsIgnoreCase(BattlebitsAPI.getServerId())) {
						stack.setAmount(id);
						stack.setDurability((short) 10);
						meta.setDisplayName("§9§l> §a§lLobby " + id + " §9§l<");
						lore.add("§b§lVocê§r§b ja esta §r§b§lconectado§r§b.");
					} else {
						if (info.getOnlinePlayers() >= 100) {
							stack.setAmount(id);
							stack.setDurability((short) 14);
							meta.setDisplayName("§9§l> §6§lLobby " + id + " §9§l<");
							lore.add("§7O servidor está §6§lCHEIO §7entrada");
							lore.add("§7apenas para §a§lLIGHT §7ou superior");
							lore.add("§0");
							lore.add("§b§lClique§r§b para §r§b§lconectar§r§b.");
						} else {
							stack.setAmount(id);
							stack.setDurability((short) 6);
							meta.setDisplayName("§9§l> §a§lLobby " + id + " §9§l<");
							lore.add("§b§lClique§r§b para §r§b§lconectar§r§b.");
						}
					}
					meta.setLore(lore);
					stack.setItemMeta(meta);
					selectorInventory.setItem(entry.getKey(), stack);
					id = id + 1;
				}
			}
		};
		updaterRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 40L);
	}

}
