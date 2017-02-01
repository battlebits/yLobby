package br.com.battlebits.ylobby.selector.lobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.api.menu.ClickType;
import br.com.battlebits.commons.api.menu.MenuClickHandler;
import br.com.battlebits.commons.api.menu.MenuInventory;
import br.com.battlebits.commons.api.menu.MenuItem;
import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.commons.core.server.loadbalancer.server.BattleServer;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public class LobbySelector {

	private MenuInventory selectorInventory;

	public void start() {
		int size = yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.LOBBY).getList().size();
		selectorInventory = new MenuInventory("§%choose-lobby%§",
				LobbyUtils.getInventoryUtils().getInventorySizeForItens(size + 18 + ((size / 7) * 2)));

		for (BattleServer ip : yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.LOBBY).getList()) {
			if (ip.getServerId().equals(BattlebitsAPI.getServerId())) {
				int lobbyId = Integer
						.valueOf(ip.getServerId().replace(".lobby.battlebits.com.br", "").replace("a", ""));
				yLobbyPlugin.getyLobby().setLobbyID("#" + lobbyId);
			}
		}
		update();
	}

	public void open(Player p) {
		if (selectorInventory != null) {
			selectorInventory.open(p);
		}
	}

	public void tryToConnect(Player p, String id) {
		p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord",
				new BungeeMessage("Connect", id).getDataOutput().toByteArray());
	}

	public void update() {
		int i = 10;
		List<BattleServer> gameServerInfos = yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.LOBBY)
				.getList();
		for (BattleServer info : gameServerInfos) {
			if (i == 8 || i == 17 || i == 26 || i == 35 || i == 44) {
				i = i + 2;
			}
			ItemStack stack = new ItemStack(Material.INK_SACK, 1);
			ItemMeta meta = stack.getItemMeta();
			ArrayList<String> lore = new ArrayList<>();
			int lobbyId = Integer.valueOf(info.getServerId().replace(".lobby.battlebits.com.br", "").replace("a", ""));
			stack.setAmount(lobbyId);
			if (info.getServerId().equalsIgnoreCase(BattlebitsAPI.getServerId())) {
				stack.setDurability((short) 10);
				meta.setDisplayName("§9§l> §a§lLobby " + lobbyId + " §9§l<");
				lore.add("§%already-connected%§");
			} else {
				if (info.getOnlinePlayers() >= info.getMaxPlayers()) {
					stack.setDurability((short) 14);
					meta.setDisplayName("§9§l> §6§lLobby " + lobbyId + " §9§l<");
					lore.add("§%lobby-server-full%§");
					lore.add("§0");
				} else {
					stack.setDurability((short) 6);
					meta.setDisplayName("§9§l> §a§lLobby " + lobbyId + " §9§l<");
				}
				lore.add("§%click-to-connect%§");
			}
			meta.setLore(lore);
			stack.setItemMeta(meta);
			selectorInventory.setItem(i, new MenuItem(stack, new MenuClickHandler() {
				@Override
				public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
					tryToConnect(p, info.getServerId());
				}
			}));
			i += 1;
		}
	}

}
