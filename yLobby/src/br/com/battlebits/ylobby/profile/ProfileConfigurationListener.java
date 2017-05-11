package br.com.battlebits.ylobby.profile;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.core.permission.Group;
import br.com.battlebits.ylobby.LobbyMain;

public class ProfileConfigurationListener implements Listener {

	private ArrayList<String> hideMessage;
	private ArrayList<String> showMessage;
	private ArrayList<String> flyForLights;
	private ArrayList<String> flyEnable;
	private ArrayList<String> flyDisable;
	private ArrayList<String> tellEnable;
	private ArrayList<String> tellDisable;
	private ArrayList<String> chatEnable;
	private ArrayList<String> chatDisable;

	public ProfileConfigurationListener() {
		hideMessage = new ArrayList<>();
		hideMessage.add("§0");
		hideMessage.add("§7Voc§ §e§lescondeu §7todos os §e§ljogadores§7!");
		hideMessage.add("§0");
		showMessage = new ArrayList<>();
		showMessage.add("§0");
		showMessage.add("§7Voc§ deixou todos os §a§ljogadores visiveis§7!");
		showMessage.add("§0");
		flyForLights = new ArrayList<>();
		flyForLights.add("§0");
		flyForLights.add("§7O modo §e§lvoar §7so pode ser usado por um");
		flyForLights.add("§7jogador com o grupo §a§lLIGHT §7ou superior!");
		flyForLights.add("§0");
		flyEnable = new ArrayList<>();
		flyEnable.add("§0");
		flyEnable.add("§7Seu §a§lfly §7foi §a§lativado§7!");
		flyEnable.add("§0");
		flyDisable = new ArrayList<>();
		flyDisable.add("§0");
		flyDisable.add("§7Seu §e§lfly §7foi §e§ldesativado§7!");
		flyDisable.add("§0");
		tellEnable = new ArrayList<>();
		tellEnable.add("§0");
		tellEnable.add("§7Voce §a§lativou §7as §a§lmensagens privadas§7!");
		tellEnable.add("§0");
		tellDisable = new ArrayList<>();
		tellDisable.add("§0");
		tellDisable.add("§7Voce §e§ldesativou §7as §e§lmensagens privadas§7!");
		tellDisable.add("§0");
		chatEnable = new ArrayList<>();
		chatEnable.add("§0");
		chatEnable.add("§7Voce §a§lativou §7o §a§lchat geral§7!");
		chatEnable.add("§0");
		chatDisable = new ArrayList<>();
		chatDisable.add("§0");
		chatDisable.add("§7Voce §e§ldesativou §7o §e§lchat geral§7!");
		chatDisable.add("§0");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getType() == InventoryType.CHEST) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().getTitle().equalsIgnoreCase("        §nSuas preferencias")) {
						e.setCancelled(true);
						Player p = (Player) e.getWhoClicked();
						if (e.getClickedInventory() == e.getInventory()) {
							if (e.getSlot() == 10 || e.getSlot() == 19) {
								if (LobbyMain.getInstance().getPlayerHideManager().isHiding(p)) {
									LobbyMain.getInstance().getPlayerHideManager().showAllPlayers(p);
									for (String msg : showMessage) {
										p.sendMessage(msg);
									}
								} else {
									LobbyMain.getInstance().getPlayerHideManager().hideAllPlayers(p);
									for (String msg : hideMessage) {
										p.sendMessage(msg);
									}
								}
								LobbyMain.getInstance().getProfileConfigurationInventory()
										.setHideItens(e.getInventory(), p);
							} else if (e.getSlot() == 12 || e.getSlot() == 21) {
								if (p.getAllowFlight()) {
									p.setAllowFlight(false);
									p.setFlying(false);
									for (String str : flyDisable) {
										p.sendMessage(str);
									}
								} else {
									if (BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId())
											.hasGroupPermission(Group.LIGHT)) {
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
								LobbyMain.getInstance().getProfileConfigurationInventory()
										.setFlyItens(e.getInventory(), p);
							} else if (e.getSlot() == 14 || e.getSlot() == 23) {
							} else if (e.getSlot() == 16 || e.getSlot() == 25) {
							} else if (e.getSlot() == 31) {
								LobbyMain.getInstance().getYourProfileInventory().open(p);
							}
						}
					}
				}
			}
		}
	}

}
