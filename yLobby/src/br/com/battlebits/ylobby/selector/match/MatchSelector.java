package br.com.battlebits.ylobby.selector.match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.server.GameServerInfo;
import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;

public abstract class MatchSelector {

	private Inventory serverSelectorInventory;
	private ItemStack directConnectItem;
	private ArrayList<String> directConnectItemLore;
	private ItemMeta directConnectItemMeta;
	private ItemStack backToServerMenuItem;
	private ArrayList<String> serverRestartingMessage;
	private ArrayList<String> needToBeUltimateToSpectate;
	private ArrayList<String> needToBeLightToJoinFull;
	private ArrayList<String> selectorServers;
	private int maxPlayersServer;
	private String inventoryTitle;
	private BungeeMessage directConnectMessage;

	public MatchSelector(List<String> servers, int maxPlayersPerServer, String title, BungeeMessage dc) {
		selectorServers = new ArrayList<>(servers);
		maxPlayersServer = maxPlayersPerServer;
		inventoryTitle = title;
		directConnectMessage = dc;
		directConnectItemLore = new ArrayList<>();
		directConnectItem = new ItemStack(Material.GHAST_TEAR, 1);
		directConnectItem = yLobbyPlugin.getyLobby().getzUtils().getItemUtils().addGlow(directConnectItem);
		directConnectItemMeta = directConnectItem.getItemMeta();
		directConnectItemMeta.setDisplayName("§9§l> §b§lPartida rapida §9§l<");
		directConnectItemLore.addAll(Arrays.asList("§0", "§7Clique aqui para §b§lconectar ", "§7a um servidor §b§ldisponivel§7!", "§0",
				"§7No §3§ltotal §7temos §3§l0 §r§7jogadores", "§7conectados nos servidores.", "§0"));
		directConnectItemMeta.setLore(directConnectItemLore);
		directConnectItem.setItemMeta(directConnectItemMeta);
		backToServerMenuItem = new ItemStack(Material.ARROW, 1);
		ItemMeta backmeta = backToServerMenuItem.getItemMeta();
		backmeta.setDisplayName("§9§lModos de Jogo");
		backmeta.setLore(Arrays.asList("§0", "§7Clique aqui para voltar", "§7para o menu de Modos de Jogo.", "§0"));
		backToServerMenuItem.setItemMeta(backmeta);
		serverRestartingMessage = new ArrayList<>();
		serverRestartingMessage.add("§0");
		serverRestartingMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
				.centerChatMessage("§7Este servidor está §8§lREINICIANDO §7aguarde para §b§lconectar§7!"));
		serverRestartingMessage.add("§0");
		needToBeUltimateToSpectate = new ArrayList<>();
		needToBeUltimateToSpectate.add("§0");
		needToBeUltimateToSpectate.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
				.centerChatMessage("§7Você precisa ser §D§LULTIMATE§7 ou superior para §b§lespectar§7!"));
		needToBeUltimateToSpectate
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Compre em nosso site §6§lwww.battlebits.com.br§7!"));
		needToBeUltimateToSpectate.add("§0");
		needToBeLightToJoinFull = new ArrayList<>();
		needToBeLightToJoinFull.add("§0");
		needToBeLightToJoinFull
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você precisa ser §a§lLIGHT§7 ou superior para"));
		needToBeLightToJoinFull
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§6§lentrar§7 com o§6§l servidor cheio§7!"));
		needToBeLightToJoinFull
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Compre em nosso site §6§lwww.battlebits.com.br§7!"));
		needToBeLightToJoinFull.add("§0");
		serverSelectorInventory = Bukkit.createInventory(null, yLobbyPlugin.getyLobby().getzUtils().getInventoryUtils()
				.getInventorySizeForItens(selectorServers.size() + 18 + ((selectorServers.size() / 7) * 2)), inventoryTitle);
		serverSelectorInventory.setItem(4, directConnectItem);
		serverSelectorInventory.setItem(serverSelectorInventory.getSize() - 5, backToServerMenuItem);
	}

	public abstract int getMatchsOnlinePlayers();

	public void directConnect(Player p) {
		p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", directConnectMessage.getDataOutput().toByteArray());
	}

	public void stop() {
		serverRestartingMessage.clear();
		needToBeLightToJoinFull.clear();
		needToBeUltimateToSpectate.clear();
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
		GameServerInfo info = yLobbyPlugin.getyLobby().getGameServerInfoManager().get(ip);
		if (info.getTime() == 0) {
			if (info.getMotd().contains("progresso")) {
				if (Main.getPlugin().getPermissionManager().hasGroupPermission(p, Group.ULTIMATE)) {
					p.sendMessage("§0");
					p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
							.centerChatMessage("§b§lConectando§7 ao servidor §9§l" + ip + "§7!"));
					p.sendMessage("§0");
					p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", new BungeeMessage("Connect", ip).getDataOutput().toByteArray());
				} else {
					for (String msg : needToBeUltimateToSpectate) {
						p.sendMessage(msg);
					}
				}
			} else {
				for (String msg : serverRestartingMessage) {
					p.sendMessage(msg);
				}
			}
		} else if (info.getTime() == 9999) {
			p.sendMessage("§0");
			p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§e§lConectando§7 ao servidor §9§l" + ip + "§7!"));
			p.sendMessage("§0");
			p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", new BungeeMessage("Connect", ip).getDataOutput().toByteArray());
		} else {
			if (info.getOnlinePlayers() >= 100) {
				if (Main.getPlugin().getPermissionManager().hasGroupPermission(p, Group.LIGHT)) {
					p.sendMessage("§0");
					p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
							.centerChatMessage("§6§lConectando§7 ao servidor §9§l" + ip + "§7!"));
					p.sendMessage("§0");
					p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", new BungeeMessage("Connect", ip).getDataOutput().toByteArray());
				} else {
					for (String msg : needToBeLightToJoinFull) {
						p.sendMessage(msg);
					}
				}
			} else {
				p.sendMessage("§0");
				p.sendMessage(
						yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§a§lConectando§7 ao servidor §9§l" + ip + "§7!"));
				p.sendMessage("§0");
				p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", new BungeeMessage("Connect", ip).getDataOutput().toByteArray());
			}
		}
	}

	public void update() {
		int i = 10;
		try {
			directConnectItemLore.set(directConnectItemLore.size() - 3,
					"§7No §3§ltotal §7temos §3§l" + yLobbyPlugin.getyLobby().getPlayerCountManager().getFpOnlinePlayers() + " §r§7jogadores");
			directConnectItemMeta.setLore(directConnectItemLore);
			directConnectItem.setItemMeta(directConnectItemMeta);
			serverSelectorInventory.setItem(4, directConnectItem);
		} catch (Exception e) {
		}
		ArrayList<GameServerInfo> gameServerInfos = new ArrayList<>();
		for (String ip : selectorServers) {
			GameServerInfo info = yLobbyPlugin.getyLobby().getGameServerInfoManager().get(ip);
			gameServerInfos.add(info);
		}
		Collections.sort(gameServerInfos);
		for (GameServerInfo info : gameServerInfos) {
			if (i == 8 || i == 17 || i == 26 || i == 35 || i == 44) {
				i = i + 2;
			}
			ItemStack stack = new ItemStack(Material.INK_SACK, 1);
			ItemMeta meta = stack.getItemMeta();
			ArrayList<String> lore = new ArrayList<>();
			lore.add("§0");
			if (info.getTime() != 0 || info.getMotd().contains("progresso")) {
				lore.add("§3§l" + info.getOnlinePlayers() + " §7" + ((info.getOnlinePlayers() == 1) ? "jogador conectado" : "jogadores conectados"));
			}
			if (info.getTime() == 9999) {
				stack.setAmount(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().getItemAmount(info.getOnlinePlayers()));
				stack.setDurability((short) 11);
				meta.setDisplayName("§9§l> §e§l" + info.getIp() + " §9§l<");
				lore.add("§7Aguardando §e§ljogadores §7para iniciar!");
				lore.add("§7Precisa-se de mais §e§l" + (5 - info.getOnlinePlayers()) + "§7 jogadores!");
				lore.add("§0");
				lore.add("§b§lClique§r§b para §r§b§lconectar§r§b.");
			} else if (info.getTime() == 0) {
				if (info.getMotd().contains("progresso")) {
					if (info.getOnlinePlayers() > 1) {
						stack.setAmount(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().getItemAmount(info.getOnlinePlayers()));
						stack.setDurability((short) 1);
						meta.setDisplayName("§9§l> §c§l" + info.getIp() + " §9§l<");
						lore.add("§7A partida está em §c§lprogresso§7!");
						lore.add("§7A partida iniciou com §c§l" + info.getMaxPlayers() + " §7jogadores.");
						lore.add("§0");
						lore.add("§b§lClique§r§b para §r§b§lespectar§r§b.");
					} else {
						stack.setAmount(1);
						stack.setDurability((short) 5);
						meta.setDisplayName("§9§l> §5§l" + info.getIp() + " §9§l<");
						lore.add("§7A partida ja §5§lacabou§7!");
						lore.add("§7O servidor irá §8§lreiniciar§7!");
						lore.add("§0");
						lore.add("§b§lAguarde§r§b reiniciar para §r§b§lconectar§r§b.");
					}
				} else {
					stack.setAmount(1);
					stack.setDurability((short) 8);
					meta.setDisplayName("§9§l> §8§l" + info.getIp() + " §9§l<");
					lore.add("§7O servidor está §8§lreiniciando§7!");
					lore.add("§0");
					lore.add("§b§lAguarde§r§b para se §r§b§lconectar§r§b!");
				}
			} else {
				if (info.getTime() >= 120) {
					stack.setAmount(info.getTime() / 60);
				} else if (info.getTime() >= 60) {
					stack.setAmount(1);
				} else {
					stack.setAmount(info.getTime());
				}
				if (info.getOnlinePlayers() >= maxPlayersServer) {
					stack.setDurability((short) 14);
					meta.setDisplayName("§9§l> §6§l" + info.getIp() + " §9§l<");
					lore.add("§7A partida §6§linicia §r§7em §6§l" + yLobbyPlugin.getyLobby().getzUtils().getTimeUtils().formatTime(info.getTime())
							+ "§r§7.");
					lore.add("§0");
					lore.add("§b§lClique§r§b para §r§b§lconectar§r§b.");
				} else {
					stack.setDurability((short) 10);
					meta.setDisplayName("§9§l> §a§l" + info.getIp() + " §9§l<");
					lore.add("§7A partida §a§linicia §r§7em §a§l" + yLobbyPlugin.getyLobby().getzUtils().getTimeUtils().formatTime(info.getTime())
							+ "§r§7.");
					lore.add("§0");
					lore.add("§b§lClique§r§b para §r§b§lconectar§r§b.");
				}
			}
			lore.add("§0");
			meta.setLore(lore);
			stack.setItemMeta(meta);
			serverSelectorInventory.setItem(i, stack);
			i = i + 1;
		}
	}

}
