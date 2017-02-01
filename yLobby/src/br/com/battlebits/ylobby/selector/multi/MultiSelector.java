package br.com.battlebits.ylobby.selector.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
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
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import lombok.Getter;

public abstract class MultiSelector {

	private MenuInventory serverSelectorInventory;
	private ItemStack directConnectItem;
	private ArrayList<String> directConnectItemLore;
	private ItemMeta directConnectItemMeta;
	private ItemStack backToServerMenuItem;
	private ArrayList<String> serverRestartingMessage;
	private ArrayList<String> needToBeLightToJoinFull;
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
				.addAll(Arrays.asList("§7Clique aqui para §b§lconectar ", "§7a um servidor §b§ldisponivel§7!", "§0",
						"§7No §3§ltotal §7temos §3§l0 §r§7jogadores", "§7conectados nos servidores."));
		directConnectItemMeta.setLore(directConnectItemLore);
		directConnectItem.setItemMeta(directConnectItemMeta);
		backToServerMenuItem = new ItemStack(Material.ARROW, 1);
		ItemMeta backmeta = backToServerMenuItem.getItemMeta();
		backmeta.setDisplayName("§9§lModos de Jogo");
		backmeta.setLore(Arrays.asList("§7Clique aqui para voltar", "§7para o menu de Modos de Jogo."));
		backToServerMenuItem.setItemMeta(backmeta);
		serverRestartingMessage = new ArrayList<>();
		serverRestartingMessage.add("§0");
		serverRestartingMessage.add("§7Este servidor está §8§lREINICIANDO §7aguarde para §b§lconectar§7!");
		serverRestartingMessage.add("§0");
		needToBeLightToJoinFull = new ArrayList<>();
		needToBeLightToJoinFull.add("§0");
		needToBeLightToJoinFull.add("§7Você precisa ser §a§lLIGHT§7 ou superior para");
		needToBeLightToJoinFull.add("§6§lentrar§7 com o§6§l servidor cheio§7!");
		needToBeLightToJoinFull.add("§7Compre em nosso site §6§lwww.battlebits.com.br§7!");
		needToBeLightToJoinFull.add("§0");
		int size = yLobbyPlugin.getyLobby().getServerManager().getBalancer(serverType).getList().size();
		serverSelectorInventory = new MenuInventory(inventoryTitle,
				LobbyUtils.getInventoryUtils().getInventorySizeForItens(size + 18 + ((size / 7) * 2)));
		serverSelectorInventory.setItem(4, new MenuItem(directConnectItem, new MenuClickHandler() {

			@Override
			public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
				directConnect(p);
			}
		}));
		serverSelectorInventory.setItem(serverSelectorInventory.getInventory().getSize() - 5,
				new MenuItem(backToServerMenuItem, new MenuClickHandler() {

					@Override
					public void onClick(Player p, Inventory inv, ClickType type, ItemStack stack, int slot) {
						yLobbyPlugin.getyLobby().getGameModeSelector().open(p);
					}
				}));
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
			serverSelectorInventory.open(p);
		}
	}

	public void tryToConnect(Player p, String ip) {
		p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord",
				new BungeeMessage("Connect", ip).getDataOutput().toByteArray());
	}

	public void update() {
		int i = 10;
		try {
			directConnectItemLore.set(directConnectItemLore.size() - 2,
					"§7No §3§ltotal §7temos §3§l" + getMultiOnlinePlayers() + " §r§7jogadores");
			directConnectItemMeta.setLore(directConnectItemLore);
			directConnectItem.setItemMeta(directConnectItemMeta);
		} catch (Exception e) {
		}
		List<BattleServer> gameServerInfos = yLobbyPlugin.getyLobby().getServerManager().getBalancer(serverType)
				.getList();
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
				lore.add("§3§l" + info.getOnlinePlayers() + " §7"
						+ ((info.getOnlinePlayers() == 1) ? "jogador conectado" : "jogadores conectados"));
			}
			lore.add("§0");
			lore.add("§b§lClique§r§b para §r§b§lconectar§r§b.");

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
	}

}
