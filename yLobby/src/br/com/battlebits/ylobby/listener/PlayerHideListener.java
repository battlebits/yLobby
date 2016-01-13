package br.com.battlebits.ylobby.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class PlayerHideListener implements Listener {

	private ArrayList<String> hideMessage;
	private ArrayList<String> showMessage;
	private HashMap<UUID, Long> uuidCooldown;

	public PlayerHideListener() {
		hideMessage = new ArrayList<>();
		hideMessage.add("§0");
		hideMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você §e§lescondeu §7todos os §e§ljogadores§7!"));
		hideMessage.add("§0");
		showMessage = new ArrayList<>();
		showMessage.add("§0");
		showMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você deixou todos os §a§ljogadores visiveis§7!"));
		showMessage.add("§0");
		uuidCooldown = new HashMap<>();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoinListener(PlayerJoinEvent e) {
		yLobbyPlugin.getyLobby().getPlayerHideManager().playerJoin(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuitListener(PlayerQuitEvent e) {
		uuidCooldown.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteractListener(PlayerInteractEvent e) {
		if (!e.isCancelled() || e.getAction().name().contains("AIR")) {
			if (e.getItem() != null) {
				if (e.getItem().getType() == Material.INK_SACK) {
					if (e.getItem().hasItemMeta()) {
						if (e.getItem().getItemMeta().hasDisplayName()) {
							if (e.getItem().getItemMeta().getDisplayName().contains("Jogadores")) {
								if (!uuidCooldown.containsKey(e.getPlayer().getUniqueId())
										|| System.currentTimeMillis() >= uuidCooldown.get(e.getPlayer().getUniqueId())) {
									if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lMostrar Jogadores §7(Clique)")) {
										yLobbyPlugin.getyLobby().getPlayerHideManager().showAllPlayers(e.getPlayer());
										e.getPlayer().getInventory()
												.setItemInHand(yLobbyPlugin.getyLobby().getLobbyItensManager().getHidePlayersItem());
										for (String msg : showMessage) {
											e.getPlayer().sendMessage(msg);
										}
										e.setCancelled(true);
									} else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§lEsconder Jogadores §7(Clique)")) {
										yLobbyPlugin.getyLobby().getPlayerHideManager().hideOnlyNormal(e.getPlayer());
										e.getPlayer().getInventory()
												.setItemInHand(yLobbyPlugin.getyLobby().getLobbyItensManager().getShowPlayersItem());
										for (String msg : hideMessage) {
											e.getPlayer().sendMessage(msg);
										}
										e.setCancelled(true);
									}
									uuidCooldown.put(e.getPlayer().getUniqueId(), System.currentTimeMillis() + 5000);
								} else {
									e.getPlayer().sendMessage("§0");
									e.getPlayer()
											.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
													.centerChatMessage("§c§lAguarde §7"
															+ yLobbyPlugin.getyLobby().getzUtils().getTimeUtils()
																	.formatTime((int) (((uuidCooldown.get(e.getPlayer().getUniqueId())
																			- System.currentTimeMillis()) / 1000)) + 1)
													+ "§7 para usar isso §c§lnovamente§7!"));
									e.getPlayer().sendMessage("§0");
									e.setCancelled(true);
								}
							}
						}
					}
				}
			}
		}
	}

}
