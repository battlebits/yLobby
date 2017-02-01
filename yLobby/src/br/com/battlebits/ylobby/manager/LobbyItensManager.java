package br.com.battlebits.ylobby.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.api.item.ActionItemStack;
import br.com.battlebits.commons.api.item.ActionItemStack.InteractHandler;
import br.com.battlebits.commons.api.item.ItemBuilder;
import br.com.battlebits.commons.core.account.BattlePlayer;
import br.com.battlebits.commons.core.permission.Group;
import br.com.battlebits.commons.core.translate.T;
import br.com.battlebits.commons.util.string.StringLoreUtils;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.listener.PlayerHideListener;
import lombok.Getter;

public class LobbyItensManager {

	@Getter
	private ItemStack hideItem;
	private ItemStack lobbyItem;
	private ItemStack gameModeItem;
	private ItemStack parkourItem;
	private ItemStack profileItem;
	private SkullMeta profileMeta;
	private ItemStack cosmeticsItem;

	public LobbyItensManager() {
		hideItem = new ActionItemStack(new ItemBuilder().type(Material.INK_SACK).durability(10)
				.name("§%hide-players-item%§ §7(§%click%§)").lore("§%hide-players-item-lore%§").build(),
				new InteractHandler() {

					@Override
					public boolean onInteract(Player player, ItemStack item, Action action) {
						if (!PlayerHideListener.getUuidCooldown().containsKey(player.getUniqueId())
								|| System.currentTimeMillis() >= PlayerHideListener.getUuidCooldown()
										.get(player.getUniqueId())) {
							if (LobbyMain.getInstance().getPlayerHideManager().isHiding(player)) {
								LobbyMain.getInstance().getPlayerHideManager().showAllPlayers(player);
								player.sendMessage("§0");
								player.sendMessage("§%players-showed%§");
								player.sendMessage("§0");
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName("§%hide-players-item%§ §7(§%click%§)");
								meta.setLore(StringLoreUtils.formatForLore("§%hide-players-item-lore%§"));
								item.setItemMeta(meta);
								item.setDurability((short) 10);
								player.setItemInHand(item);
							} else {
								LobbyMain.getInstance().getPlayerHideManager().hideAllPlayers(player);
								player.sendMessage("§0");
								player.sendMessage("§%players-hided%§");
								player.sendMessage("§0");
								item.getItemMeta().setDisplayName("§%show-players-item%§ §7(§%click%§)");
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName("§%show-players-item%§ §7(§%click%§)");
								meta.setLore(StringLoreUtils.formatForLore("§%show-players-item-lore%§"));
								item.setItemMeta(meta);
								item.setDurability((short) 8);
								player.setItemInHand(item);
							}
							PlayerHideListener.getUuidCooldown().put(player.getUniqueId(),
									System.currentTimeMillis() + 5000);
						} else {
							String replace = LobbyUtils.getTimeUtils()
									.formatTime((int) (((PlayerHideListener.getUuidCooldown().get(player.getUniqueId())
											- System.currentTimeMillis()) / 1000)) + 1);
							player.sendMessage("§0");
							player.sendMessage(T.t(BattlePlayer.getLanguage(player.getUniqueId()), "cooldown-wait",
									new String[] { "%time%", replace }));
							player.sendMessage("§0");
						}
						return false;
					}
				}).getItemStack();
		lobbyItem = new ActionItemStack(new ItemBuilder().type(Material.NETHER_STAR).name("§5§lLobbys §7(§%click%§)")
				.lore("§%lobbys-item-lore%§").build(), new InteractHandler() {

					@Override
					public boolean onInteract(Player player, ItemStack item, Action action) {
						LobbyMain.getInstance().getLobbySelector().open(player);
						return false;
					}
				}).getItemStack();
		gameModeItem = new ActionItemStack(new ItemBuilder().type(Material.COMPASS)
				.name("§%gamemode-item%§ §7(§%click%§)").lore("§%gamemode-item-lore%§").glow().build(),
				new InteractHandler() {

					@Override
					public boolean onInteract(Player player, ItemStack item, Action action) {
						LobbyMain.getInstance().getGameModeSelector().open(player);
						return false;
					}
				}).getItemStack();
		parkourItem = new ActionItemStack(new ItemBuilder().type(Material.FEATHER).name("§%fun-item%§ §7(§%soon%§)")
				.lore("§%fun-item-lore%§").build(), new InteractHandler() {
					@Override
					public boolean onInteract(Player player, ItemStack item, Action action) {
						return false;
					}
				}).getItemStack();
		profileItem = new ActionItemStack(new ItemBuilder().type(Material.SKULL_ITEM).durability(3)
				.name("§%prefile-item%§ §7(§%click%§)").lore("§%prefile-item-lore%§").build(), new InteractHandler() {

					@Override
					public boolean onInteract(Player player, ItemStack item, Action action) {
						LobbyMain.getInstance().getYourProfileInventory().open(player);
						return false;
					}
				}).getItemStack();
		profileMeta = (SkullMeta) profileItem.getItemMeta();
		cosmeticsItem = new ActionItemStack(new ItemBuilder().type(Material.ENDER_CHEST)
				.name("§%cosmetics-item%§ §7(§%click%§)").lore("§%cosmetics-item-lore%§").build(), new InteractHandler() {

					@Override
					public boolean onInteract(Player player, ItemStack item, Action action) {
						if (BattlebitsAPI.getAccountCommon().getBattlePlayer(player.getUniqueId())
								.hasGroupPermission(Group.LIGHT)) {
							// TODO
							// yAddonsPlugin.getyAddons().getSelectorInventory().open(e.getPlayer());
						} else {
							player.sendMessage("§%system-not-online%§");
						}
						return false;
					}
				}).getItemStack();
	}

	public void setItems(Player p) {
		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		p.getInventory().setItem(0, gameModeItem);
		profileMeta.setOwner(p.getName());
		profileItem.setItemMeta(profileMeta);
		p.getInventory().setItem(1, profileItem);
		p.getInventory().setItem(3, cosmeticsItem);
		p.getInventory().setItem(6, hideItem);
		p.getInventory().setItem(7, parkourItem);
		p.getInventory().setItem(8, lobbyItem);
		p.getInventory().setHeldItemSlot(0);
	}

}
