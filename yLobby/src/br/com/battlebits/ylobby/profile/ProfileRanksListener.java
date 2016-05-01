package br.com.battlebits.ylobby.profile;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.battlebits.ycommon.common.BattlebitsAPI;
import br.com.battlebits.ycommon.common.account.BattlePlayer;
import br.com.battlebits.ycommon.common.permissions.enums.Group;
import br.com.battlebits.ycommon.common.tag.Tag;
import br.com.battlebits.ylobby.yLobbyPlugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class ProfileRanksListener implements Listener {

	private ArrayList<TextComponent> buyLight;
	private ArrayList<String> tagLight;
	private ArrayList<TextComponent> buyPremium;
	private ArrayList<String> tagPremium;
	private ArrayList<TextComponent> buyUltimate;
	private ArrayList<String> tagUltimate;
	private ArrayList<TextComponent> buyYoutuber;
	private ArrayList<String> tagYoutuber;

	public ProfileRanksListener() {
		buyLight = new ArrayList<>();
		buyLight.add(new TextComponent("�0"));
		TextComponent buyLightText = new TextComponent(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("�7Clique aqui para realizar a compra do seu �a�lLIGHT�7!"));
		buyLightText.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://loja.battlebits.com.br/checkout/cart/add?product=3"));
		buyLight.add(buyLightText);
		buyLight.add(new TextComponent("�0"));
		tagLight = new ArrayList<>();
		tagLight.add("�0");
		tagLight.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("�7Voc� selecionou a tag �a�lLIGHT�7!"));
		tagLight.add("�0");

		buyPremium = new ArrayList<>();
		buyPremium.add(new TextComponent("�0"));
		TextComponent buyPremiumText = new TextComponent(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("�7Clique aqui para realizar a compra do seu �6�lPREMIUM�7!"));
		buyPremiumText.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://loja.battlebits.com.br/checkout/cart/add?product=2"));
		buyPremium.add(buyPremiumText);
		buyPremium.add(new TextComponent("�0"));
		tagPremium = new ArrayList<>();
		tagPremium.add("�0");
		tagPremium.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("�7Voc� selecionou a tag �6�lPREMIUM�7!"));
		tagPremium.add("�0");

		buyUltimate = new ArrayList<>();
		buyUltimate.add(new TextComponent("�0"));
		TextComponent buyUltimateText = new TextComponent(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("�7Clique aqui para realizar a compra do seu �d�lULTIMATE�7!"));
		buyUltimateText.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://loja.battlebits.com.br/checkout/cart/add?product=3"));
		buyUltimate.add(buyUltimateText);
		buyUltimate.add(new TextComponent("�0"));
		tagUltimate = new ArrayList<>();
		tagUltimate.add("�0");
		tagUltimate.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("�7Voc� selecionou a tag �d�lULTIMATE�7!"));
		tagUltimate.add("�0");

		buyYoutuber = new ArrayList<>();
		buyYoutuber.add(new TextComponent("�0"));
		TextComponent buyYoutuberText = new TextComponent(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("�7Saiba como ser �b�lYOUTUBER�7 em nosso Twitter!"));
		buyYoutuberText.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://twitter.com/BattleBitsMC"));
		buyYoutuber.add(buyYoutuberText);
		buyYoutuber.add(new TextComponent("�0"));
		tagYoutuber = new ArrayList<>();
		tagYoutuber.add("�0");
		tagYoutuber.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("�7Voc� selecionou a tag �b�lYOUTUBER�7!"));
		tagYoutuber.add("�0");

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteractListener(PlayerInteractEvent e) {
		if (!e.isCancelled() || e.getAction() != org.bukkit.event.block.Action.PHYSICAL) {
			if (e.getItem() != null) {
				if (e.getItem().getType() == Material.SKULL_ITEM) {
					if (e.getItem().hasItemMeta()) {
						if (e.getItem().getItemMeta().hasDisplayName()) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("�6�lPerfil �7(Clique)")) {
								yLobbyPlugin.getyLobby().getYourProfileInventory().open(e.getPlayer());
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getType() == InventoryType.CHEST) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().getTitle().equalsIgnoreCase("       �nSobre o seu grupo atual")) {
						if (e.getClickedInventory() == e.getInventory()) {
							Player p = (Player) e.getWhoClicked();
							e.setCancelled(true);
							BattlePlayer player = BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId());
							Group group = player.getServerGroup();
							if (e.getSlot() == 10) {
								if (group == Group.NORMAL) {
									for (TextComponent msg : buyLight) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.LIGHT) {
									if (e.getAction() == InventoryAction.PICKUP_HALF) {
										for (TextComponent msg : buyPremium) {
											p.spigot().sendMessage(msg);
										}
									} else {
										player.setTag(Tag.LIGHT);
										for (String msg : tagLight) {
											p.sendMessage(msg);
										}
									}
								} else if (group == Group.PREMIUM) {
									player.setTag(Tag.LIGHT);
									for (String msg : tagLight) {
										p.sendMessage(msg);
									}
								} else if (group == Group.ULTIMATE) {
									player.setTag(Tag.LIGHT);
									for (String msg : tagLight) {
										p.sendMessage(msg);
									}
								} else if (group == Group.YOUTUBER) {
									player.setTag(Tag.LIGHT);
									for (String msg : tagLight) {
										p.sendMessage(msg);
									}
								}
							} else if (e.getSlot() == 12) {
								if (group == Group.NORMAL) {
									for (TextComponent msg : buyPremium) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.LIGHT) {
									for (TextComponent msg : buyPremium) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.PREMIUM) {
									if (e.getAction() == InventoryAction.PICKUP_HALF) {
										for (TextComponent msg : buyUltimate) {
											p.spigot().sendMessage(msg);
										}
									} else {
										player.setTag(Tag.PREMIUM);
										for (String msg : tagPremium) {
											p.sendMessage(msg);
										}
									}
								} else if (group == Group.ULTIMATE) {
									player.setTag(Tag.PREMIUM);
									for (String msg : tagPremium) {
										p.sendMessage(msg);
									}
								} else if (group == Group.YOUTUBER) {
									player.setTag(Tag.PREMIUM);
									for (String msg : tagPremium) {
										p.sendMessage(msg);
									}
								}
							} else if (e.getSlot() == 14) {
								if (group == Group.NORMAL) {
									for (TextComponent msg : buyUltimate) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.LIGHT) {
									for (TextComponent msg : buyUltimate) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.PREMIUM) {
									for (TextComponent msg : buyUltimate) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.ULTIMATE) {
									player.setTag(Tag.ULTIMATE);
									for (String msg : tagUltimate) {
										p.sendMessage(msg);
									}
								} else if (group == Group.YOUTUBER) {
									player.setTag(Tag.ULTIMATE);
									for (String msg : tagUltimate) {
										p.sendMessage(msg);
									}
								}
							} else if (e.getSlot() == 16) {
								if (group == Group.NORMAL) {
									for (TextComponent msg : buyYoutuber) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.LIGHT) {
									for (TextComponent msg : buyYoutuber) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.PREMIUM) {
									for (TextComponent msg : buyYoutuber) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.ULTIMATE) {
									for (TextComponent msg : buyYoutuber) {
										p.spigot().sendMessage(msg);
									}
								} else if (group == Group.YOUTUBER) {
									player.setTag(Tag.YOUTUBER);
									for (String msg : tagYoutuber) {
										p.sendMessage(msg);
									}
								}
							} else if (e.getSlot() == 22) {
								yLobbyPlugin.getyLobby().getYourProfileInventory().open(p);
							}
						}
					}
				}
			}
		}
	}

}
