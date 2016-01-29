package br.com.battlebits.ylobby.profile;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;

public class ProfileConfigurationListener implements Listener {

	private ArrayList<String> hideMessage;
	private ArrayList<String> showMessage;
	private ArrayList<String> flyForLights;
	private ArrayList<String> flyEnable;
	private ArrayList<String> flyDisable;
	private ArrayList<String> tellEnable;
	private ArrayList<String> tellDisable;

	public ProfileConfigurationListener() {
		hideMessage = new ArrayList<>();
		hideMessage.add("§0");
		hideMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você §e§lescondeu §7todos os §e§ljogadores§7!"));
		hideMessage.add("§0");
		showMessage = new ArrayList<>();
		showMessage.add("§0");
		showMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você deixou todos os §a§ljogadores visiveis§7!"));
		showMessage.add("§0");
		flyForLights = new ArrayList<>();
		flyForLights.add("§0");
		flyForLights.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7O modo §e§lvoar §7so pode ser usado por um"));
		flyForLights.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7jogador com o grupo §a§lLIGHT §7ou superior!"));
		flyForLights.add("§0");
		flyEnable = new ArrayList<>();
		flyEnable.add("§0");
		flyEnable.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Seu §a§lfly §7foi §a§lativado§7!"));
		flyEnable.add("§0");
		flyDisable = new ArrayList<>();
		flyDisable.add("§0");
		flyDisable.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Seu §e§lfly §7foi §e§ldesativado§7!"));
		flyDisable.add("§0");
		tellEnable = new ArrayList<>();
		tellEnable.add("§0");
		tellEnable.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Voce §a§lativou §7as §a§lmensagens privadas§7!"));
		tellEnable.add("§0");
		tellDisable = new ArrayList<>();
		tellDisable.add("§0");
		tellDisable
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Voce §e§ldesativou §7as §e§lmensagens privadas§7!"));
		tellDisable.add("§0");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getType() == InventoryType.CHEST) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().getTitle().equalsIgnoreCase("        §nSuas preferencias")) {
						e.setCancelled(true);
						Player p = (Player) e.getWhoClicked();
						if (e.getSlot() == 10 || e.getSlot() == 19) {
							if (yLobbyPlugin.getyLobby().getPlayerHideManager().isHiding(p)) {
								yLobbyPlugin.getyLobby().getPlayerHideManager().showAllPlayers(p);
								for (String msg : showMessage) {
									p.sendMessage(msg);
								}
							} else {
								yLobbyPlugin.getyLobby().getPlayerHideManager().hideOnlyNormal(p);
								for (String msg : hideMessage) {
									p.sendMessage(msg);
								}
							}
							yLobbyPlugin.getyLobby().getProfileConfigurationInventory().setHideItens(e.getInventory(), p);
						} else if (e.getSlot() == 12 || e.getSlot() == 21) {
							if (p.getAllowFlight()) {
								p.setAllowFlight(false);
								p.setFlying(false);
								for (String str : flyDisable) {
									p.sendMessage(str);
								}
							} else {
								if (Main.getPlugin().getPermissionManager().hasGroupPermission(p.getUniqueId(), Group.LIGHT)) {
									p.setAllowFlight(true);
									p.setFlying(true);
									for (String str : flyEnable) {
										p.sendMessage(str);
									}
								} else {
									for (String str : flyForLights) {
										p.sendMessage(str);
									}
								}
							}
							yLobbyPlugin.getyLobby().getProfileConfigurationInventory().setFlyItens(e.getInventory(), p);
						} else if (e.getSlot() == 14 || e.getSlot() == 23) {
							if (yLobbyPlugin.getyLobby().getChatManager().isTellEnabled(p.getUniqueId())) {
								yLobbyPlugin.getyLobby().getChatManager().disableTell(p.getUniqueId());
								for (String str : tellDisable) {
									p.sendMessage(str);
								}
							} else {
								yLobbyPlugin.getyLobby().getChatManager().enableTell(p.getUniqueId());
								for (String str : tellEnable) {
									p.sendMessage(str);
								}
							}
							yLobbyPlugin.getyLobby().getProfileConfigurationInventory().setTellItens(e.getInventory(), p);
						} else if (e.getSlot() == 31) {
							yLobbyPlugin.getyLobby().getYourProfileInventory().open(p);
						}
					}
				}
			}
		}
	}

}
