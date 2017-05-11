package br.com.battlebits.ylobby.profile;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.api.item.ItemBuilder;
import br.com.battlebits.commons.core.permission.Group;
import br.com.battlebits.commons.util.string.StringLoreUtils;
import br.com.battlebits.ylobby.LobbyMain;

public class ProfileConfigurationInventory {

	private ItemStack flyEnabledIcon;
	private ItemStack flyDisabledIcon;
	private ItemStack flyForLightIcon;
	private ItemStack flyEnabledItem;
	private ItemStack flyDisabledItem;
	private ItemStack flyForLightItem;
	private ItemStack hideEnabledIcon;
	private ItemStack hideDisabledIcon;
	private ItemStack hideEnabledItem;
	private ItemStack hideDisabledItem;
	private ItemStack chatEnabledIcon;
	private ItemStack backToProfile;

	public ProfileConfigurationInventory() {
		flyEnabledIcon = createEnabledIconItem(Material.FEATHER, "Modo voar");
		flyEnabledIcon = ItemBuilder.glow(flyEnabledIcon);
		flyDisabledIcon = createDisabledIconItem(Material.FEATHER, "Modo voar");
		flyForLightIcon = createForLightIconItem(Material.FEATHER, "Modo voar");
		flyEnabledItem = createEnabledItem("Modo voar");
		flyDisabledItem = createDisabledItem("Modo voar");
		flyForLightItem = createForLightItem("Modo voar");
		hideEnabledIcon = createEnabledIconItem(Material.EYE_OF_ENDER, "Mostrar jogadores");
		hideDisabledIcon = createDisabledIconItem(Material.ENDER_PEARL, "Mostrar jogadores");
		hideEnabledItem = createEnabledItem("Mostrar jogadores");
		hideDisabledItem = createDisabledItem("Mostrar jogadores");
		chatEnabledIcon = createEnabledIconItem(Material.PAPER, "Chat geral");
		chatEnabledIcon = ItemBuilder.glow(chatEnabledIcon);
		backToProfile = new ItemBuilder().type(Material.ARROW).name("§5§lPerfil")
				.lore("Clique aqui para voltar ao seu perfil.").build();
	}

	public void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36, "        §nSuas preferencias");
		setHideItens(inv, p);
		setFlyItens(inv, p);
		inv.setItem(31, backToProfile);
		p.openInventory(inv);
	}

	public void setHideItens(Inventory inv, Player p) {
		if (LobbyMain.getInstance().getPlayerHideManager().isHiding(p)) {
			inv.setItem(10, hideDisabledIcon);
			inv.setItem(19, hideDisabledItem);
			ItemStack item = LobbyMain.getInstance().getLobbyItensManager().getHideItem();
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§%hide-players-item%§ §7(§%click%§)");
			meta.setLore(StringLoreUtils.formatForLore("§%hide-players-item-lore%§"));
			item.setItemMeta(meta);
			p.getInventory().setItem(6, item);
			item.setDurability((short) 10);
		} else {
			inv.setItem(10, hideEnabledIcon);
			inv.setItem(19, hideEnabledItem);
			ItemStack item = LobbyMain.getInstance().getLobbyItensManager().getHideItem();
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§%show-players-item%§ §7(§%click%§)");
			meta.setLore(StringLoreUtils.formatForLore("§%show-players-item-lore%§"));
			item.setItemMeta(meta);
			p.getInventory().setItem(6, item);
			item.setDurability((short) 8);
		}
	}

	public void setFlyItens(Inventory inv, Player p) {
		if (p.getAllowFlight()) {
			inv.setItem(12, flyEnabledIcon);
			inv.setItem(21, flyEnabledItem);
		} else {
			if (BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId()).hasGroupPermission(Group.LIGHT)) {
				inv.setItem(12, flyDisabledIcon);
				inv.setItem(21, flyDisabledItem);
			} else {
				inv.setItem(12, flyForLightIcon);
				inv.setItem(21, flyForLightItem);
			}
		}
	}

	public ItemStack createEnabledIconItem(Material mat, String name) {
		ItemStack stack = new ItemStack(mat, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§a§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Est§ atualmente §a§lativado.\\n§7Clique aqui para §e§ldesativar§7."));
		stack.setItemMeta(meta);
		return stack;
	}

	public ItemStack createDisabledIconItem(Material mat, String name) {
		ItemStack stack = new ItemStack(mat, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§c§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Est§ atualmente §e§ldesativado.\\n§7Clique aqui para §a§lativar§7."));
		stack.setItemMeta(meta);
		return stack;
	}

	public ItemStack createForLightIconItem(Material mat, String name) {
		ItemStack stack = new ItemStack(mat, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§e§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Para §a§lativar §7e necessario que voce seja §a§lLIGHT§7 ou superior!"));
		stack.setItemMeta(meta);
		return stack;
	}

	public ItemStack createEnabledItem(String name) {
		ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§a§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Est§ atualmente §a§lativado.\\n§7Clique aqui para §e§ldesativar§7."));
		stack.setItemMeta(meta);
		return stack;
	}

	public ItemStack createDisabledItem(String name) {
		ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§c§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Est§ atualmente §e§ldesativado.\\n§7Clique aqui para §a§lativar§7."));
		stack.setItemMeta(meta);
		return stack;
	}

	public ItemStack createForLightItem(String name) {
		ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§e§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Para §a§lativar §7e necessario que voce seja §a§lLIGHT§7 ou superior!"));
		stack.setItemMeta(meta);
		return stack;
	}

}
