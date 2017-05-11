package br.com.battlebits.ylobby.selector.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.battlebits.commons.api.item.ItemBuilder;
import br.com.battlebits.commons.api.menu.ClickType;
import br.com.battlebits.commons.api.menu.MenuClickHandler;
import br.com.battlebits.commons.api.menu.MenuInventory;
import br.com.battlebits.commons.api.menu.MenuItem;
import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.commons.core.server.loadbalancer.server.BattleServer;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import lombok.Getter;

public abstract class MultiSelector {

	private MenuInventory serverSelectorInventory;
	private ItemStack directConnectItem;
	private ArrayList<String> directConnectItemLore;
	private ItemMeta directConnectItemMeta;
	private ItemStack backToServerMenuItem;
	private String inventoryTitle;
	private BungeeMessage directConnectMessage;
	@Getter
	private ServerType serverType;

	public MultiSelector(ServerType serverType, String title, BungeeMessage dc) {
		this.serverType = serverType;
		inventoryTitle = title;
		directConnectMessage = dc;
		directConnectItemLore = new ArrayList<>();
		directConnectItem = new ItemStack(Material.GHAST_TEAR, 1);
		directConnectItem = ItemBuilder.glow(directConnectItem);
		directConnectItemMeta = directConnectItem.getItemMeta();
		directConnectItemMeta.setDisplayName("§9§l> §b§l§%join-now%§! §9§l<");
		directConnectItemLore
				.addAll(Arrays.asList("§7§%click-here-to-connect%§!", "§7§%we-have%§ §3§l0 §7§%players-connected%§"));
		directConnectItemMeta.setLore(directConnectItemLore);
		directConnectItem.setItemMeta(directConnectItemMeta);
		backToServerMenuItem = new ItemStack(Material.ARROW, 1);
		ItemMeta backmeta = backToServerMenuItem.getItemMeta();
		backmeta.setDisplayName("§9§l§%game-modes%§");
		backmeta.setLore(Arrays.asList("§%back-game-mode%§"));
		backToServerMenuItem.setItemMeta(backmeta);
		int size = LobbyMain.getInstance().getServerManager().getBalancer(serverType).getList().size();
		serverSelectorInventory = new MenuInventory(inventoryTitle,
				LobbyUtils.getInventoryUtils().getInventorySizeForItens(size + 18 + ((size / 7) * 2)));
		update();
	}

	public abstract int getMultiOnlinePlayers();

	public void directConnect(Player p) {
		p.sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", directConnectMessage.getDataOutput().toByteArray());
	}

	public void stop() {
		directConnectItemLore.clear();
	}

	public String getInventoryTitle() {
		return inventoryTitle;
	}

	public void open(Player p) {
		if (serverSelectorInventory != null) {
			serverSelectorInventory.open(p);
		}
	}

	public void tryToConnect(Player p, String ip) {
		p.sendPluginMessage(LobbyMain.getInstance(), "BungeeCord",
				new BungeeMessage("Connect", ip).getDataOutput().toByteArray());
	}

	public void update() {
		int size = LobbyMain.getInstance().getServerManager().getBalancer(serverType).getList().size();
		MenuInventory serverSelectorInventory = new MenuInventory(inventoryTitle,
				LobbyUtils.getInventoryUtils().getInventorySizeForItens(size + 18 + ((size / 7) * 2)));
		int i = 10;
		try {
			directConnectItemLore.set(1, "§7§%we-have%§ §3§l" + getMultiOnlinePlayers() + " §7§%players-connected%§");
			directConnectItemMeta.setLore(directConnectItemLore);
			directConnectItem.setItemMeta(directConnectItemMeta);
			serverSelectorInventory.setItem(serverSelectorInventory.getInventory().getSize() - 5,
					new MenuItem(backToServerMenuItem, new MenuClickHandler() {

						@Override
						public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
							LobbyMain.getInstance().getGameModeSelector().open(p);
						}
					}));
			serverSelectorInventory.setItem(4, new MenuItem(directConnectItem, new MenuClickHandler() {

				@Override
				public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
					directConnect(p);
				}
			}));
		} catch (Exception e) {
		}
		List<BattleServer> gameServerInfos = LobbyMain.getInstance().getServerManager().getBalancer(serverType)
				.getList();
		Collections.sort(gameServerInfos, new Comparator<BattleServer>() {

			@Override
			public int compare(BattleServer o1, BattleServer o2) {
				return o1.getServerId().compareTo(o2.getServerId());
			}
		});
		for (BattleServer info : gameServerInfos) {
			if (i == 8 || i == 17 || i == 26 || i == 35 || i == 44) {
				i = i + 2;
			}
			ItemStack stack = new ItemStack(Material.INK_SACK, 1);
			ItemMeta meta = stack.getItemMeta();
			ArrayList<String> lore = new ArrayList<>();

			if (info.getOnlinePlayers() >= info.getMaxPlayers()) {
				stack.setDurability((short) 14);
				meta.setDisplayName("§9§l> §6§l" + info.getServerId() + " §9§l<");
			} else {
				stack.setDurability((short) 10);
				meta.setDisplayName("§9§l> §a§l" + info.getServerId() + " §9§l<");
			}

			if (info.getOnlinePlayers() > 0) {
				lore.add("§0");
				lore.add("§3§l" + info.getOnlinePlayers() + " §7§%players-connected%§");
			}
			lore.add("§0");
			lore.add("§%click-to-connect%§.");

			meta.setLore(lore);
			stack.setItemMeta(meta);
			serverSelectorInventory.setItem(i, new MenuItem(stack, new MenuClickHandler() {

				@Override
				public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
					tryToConnect(p, info.getServerId());
				}
			}));
			i = i + 1;
		}
		for (HumanEntity entity : new ArrayList<>(this.serverSelectorInventory.getInventory().getViewers())) {
			if (entity instanceof Player)
				serverSelectorInventory.open((Player) entity);
		}
		this.serverSelectorInventory = serverSelectorInventory;
	}

}
