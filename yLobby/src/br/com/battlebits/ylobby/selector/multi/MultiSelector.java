package br.com.battlebits.ylobby.selector.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.api.item.ItemBuilder;
import br.com.battlebits.commons.core.permission.Group;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.server.ServerInfo;

public abstract class MultiSelector {

	private Inventory serverSelectorInventory;
	private ItemStack directConnectItem;
	private ArrayList<String> directConnectItemLore;
	private ItemMeta directConnectItemMeta;
	private ItemStack backToServerMenuItem;
	private ArrayList<String> serverRestartingMessage;
	private ArrayList<String> needToBeLightToJoinFull;
	private ArrayList<String> selectorServers;
	private int maxPlayersServer;
	private String inventoryTitle;
	private BungeeMessage directConnectMessage;

	public MultiSelector(List<String> servers, int maxPlayersPerServer, String title, BungeeMessage dc) {
		selectorServers = new ArrayList<>(servers);
		maxPlayersServer = maxPlayersPerServer;
		inventoryTitle = title;
		directConnectMessage = dc;
		directConnectItemLore = new ArrayList<>();
		directConnectItem = new ItemStack(Material.GHAST_TEAR, 1);
		directConnectItem = ItemBuilder.glow(directConnectItem);
		directConnectItemMeta = directConnectItem.getItemMeta();
		directConnectItemMeta.setDisplayName("�9�l> �b�l�%join-now%�! �9�l<");
		directConnectItemLore
				.addAll(Arrays.asList("�7Clique aqui para �b�lconectar ", "�7a um servidor �b�ldisponivel�7!", "�0",
						"�7No �3�ltotal �7temos �3�l0 �r�7jogadores", "�7conectados nos servidores."));
		directConnectItemMeta.setLore(directConnectItemLore);
		directConnectItem.setItemMeta(directConnectItemMeta);
		backToServerMenuItem = new ItemStack(Material.ARROW, 1);
		ItemMeta backmeta = backToServerMenuItem.getItemMeta();
		backmeta.setDisplayName("�9�lModos de Jogo");
		backmeta.setLore(Arrays.asList("�7Clique aqui para voltar", "�7para o menu de Modos de Jogo."));
		backToServerMenuItem.setItemMeta(backmeta);
		serverRestartingMessage = new ArrayList<>();
		serverRestartingMessage.add("�0");
		serverRestartingMessage.add("�7Este servidor est� �8�lREINICIANDO �7aguarde para �b�lconectar�7!");
		serverRestartingMessage.add("�0");
		needToBeLightToJoinFull = new ArrayList<>();
		needToBeLightToJoinFull.add("�0");
		needToBeLightToJoinFull.add("�7Voc� precisa ser �a�lLIGHT�7 ou superior para");
		needToBeLightToJoinFull.add("�6�lentrar�7 com o�6�l servidor cheio�7!");
		needToBeLightToJoinFull.add("�7Compre em nosso site �6�lwww.battlebits.com.br�7!");
		needToBeLightToJoinFull.add("�0");
		serverSelectorInventory = Bukkit.createInventory(null, LobbyUtils.getInventoryUtils().getInventorySizeForItens(
				selectorServers.size() + 18 + ((selectorServers.size() / 7) * 2)), inventoryTitle);
		serverSelectorInventory.setItem(4, directConnectItem);
		serverSelectorInventory.setItem(serverSelectorInventory.getSize() - 5, backToServerMenuItem);
	}

	public abstract int getMultiOnlinePlayers();

	public void directConnect(Player p) {
		p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", directConnectMessage.getDataOutput().toByteArray());
	}

	public void stop() {
		serverRestartingMessage.clear();
		needToBeLightToJoinFull.clear();
		directConnectItemLore.clear();
	}

	public String getInventoryTitle() {
		return inventoryTitle;
	}

	public void open(Player p) {
		if (serverSelectorInventory != null) {
			p.openInventory(serverSelectorInventory);
		}
	}

	public void tryToConnect(Player p, String ip) {
		ServerInfo info = yLobbyPlugin.getyLobby().getServerInfoManager().get(ip);
		if (info.getOnlinePlayers() >= 100) {
			if (BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId()).hasGroupPermission(Group.LIGHT)) {
				p.sendMessage("�0");
				p.sendMessage("�6�lConectando�7 ao servidor �9�l" + ip + "�7!");
				p.sendMessage("�0");
				p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord",
						new BungeeMessage("Connect", ip).getDataOutput().toByteArray());
			} else {
				for (String msg : needToBeLightToJoinFull) {
					p.sendMessage(msg);
				}
			}
		} else {
			p.sendMessage("�0");
			p.sendMessage("�a�lConectando�7 ao servidor �9�l" + ip + "�7!");
			p.sendMessage("�0");
			p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord",
					new BungeeMessage("Connect", ip).getDataOutput().toByteArray());
		}
	}

	public void update() {
		int i = 10;
		try {
			directConnectItemLore.set(directConnectItemLore.size() - 2,
					"�7No �3�ltotal �7temos �3�l" + getMultiOnlinePlayers() + " �r�7jogadores");
			directConnectItemMeta.setLore(directConnectItemLore);
			directConnectItem.setItemMeta(directConnectItemMeta);
			serverSelectorInventory.setItem(4, directConnectItem);
		} catch (Exception e) {
		}
		ArrayList<ServerInfo> gameServerInfos = new ArrayList<>();
		for (String ip : selectorServers) {
			ServerInfo info = yLobbyPlugin.getyLobby().getServerInfoManager().get(ip);
			gameServerInfos.add(info);
		}
		for (ServerInfo info : gameServerInfos) {
			if (i == 8 || i == 17 || i == 26 || i == 35 || i == 44) {
				i = i + 2;
			}
			ItemStack stack = new ItemStack(Material.INK_SACK, 1);
			ItemMeta meta = stack.getItemMeta();
			ArrayList<String> lore = new ArrayList<>();

			if (info.getOnlinePlayers() >= maxPlayersServer) {
				stack.setDurability((short) 14);
				meta.setDisplayName("�9�l> �6�l" + info.getIp() + " �9�l<");
			} else {
				stack.setDurability((short) 10);
				meta.setDisplayName("�9�l> �a�l" + info.getIp() + " �9�l<");
			}

			if (info.getOnlinePlayers() > 0) {
				lore.add("�0");
				lore.add("�3�l" + info.getOnlinePlayers() + " �7"
						+ ((info.getOnlinePlayers() == 1) ? "jogador conectado" : "jogadores conectados"));
			}
			lore.add("�0");
			lore.add("�b�lClique�r�b para �r�b�lconectar�r�b.");

			meta.setLore(lore);
			stack.setItemMeta(meta);
			serverSelectorInventory.setItem(i, stack);
			i = i + 1;
		}
	}

}