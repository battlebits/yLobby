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
import br.com.battlebits.ylobby.yLobbyPlugin;

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
	private ItemStack tellEnabledIcon;
	private ItemStack tellDisabledIcon;
	private ItemStack tellEnabledItem;
	private ItemStack tellDisabledItem;
	private ItemStack chatEnabledIcon;
	private ItemStack chatDisabledIcon;
	private ItemStack chatEnabledItem;
	private ItemStack chatDisabledItem;
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
		tellEnabledIcon = createEnabledIconItem(Material.BOOK_AND_QUILL, "Mensagens privadas");
		tellDisabledIcon = createDisabledIconItem(Material.BOOK, "Mensagens privadas");
		tellEnabledItem = createEnabledItem("Mensagens privadas");
		tellDisabledItem = createDisabledItem("Mensagens privadas");
		chatEnabledIcon = createEnabledIconItem(Material.PAPER, "Chat geral");
		chatEnabledIcon = ItemBuilder.glow(chatEnabledIcon);
		chatDisabledIcon = createDisabledIconItem(Material.PAPER, "Chat geral");
		chatEnabledItem = createEnabledItem("Chat geral");
		chatDisabledItem = createDisabledItem("Chat geral");
		backToProfile = new ItemBuilder().type(Material.ARROW).name("§5§lPerfil")
				.lore("Clique aqui para voltar ao seu perfil.").build();
	}

	public void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36, "        §nSuas preferencias");
		setHideItens(inv, p);
		setFlyItens(inv, p);
		setTellItens(inv, p);
		setChatItens(inv, p);
		inv.setItem(31, backToProfile);
		p.openInventory(inv);
	}

	public void setHideItens(Inventory inv, Player p) {
		if (yLobbyPlugin.getyLobby().getPlayerHideManager().isHiding(p)) {
			inv.setItem(10, hideDisabledIcon);
			inv.setItem(19, hideDisabledItem);
			p.getInventory().setItem(6, yLobbyPlugin.getyLobby().getLobbyItensManager().getShowPlayersItem());
		} else {
			inv.setItem(10, hideEnabledIcon);
			inv.setItem(19, hideEnabledItem);
			p.getInventory().setItem(6, yLobbyPlugin.getyLobby().getLobbyItensManager().getHidePlayersItem());
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

	public void setTellItens(Inventory inv, Player p) {
		if (yLobbyPlugin.getyLobby().getChatManager().isTellEnabled(p.getUniqueId())) {
			inv.setItem(14, tellEnabledIcon);
			inv.setItem(23, tellEnabledItem);
		} else {
			inv.setItem(14, tellDisabledIcon);
			inv.setItem(23, tellDisabledItem);
		}
	}

	public void setChatItens(Inventory inv, Player p) {
		if (yLobbyPlugin.getyLobby().getChatManager().isChatEnabled(p.getUniqueId())) {
			inv.setItem(16, chatEnabledIcon);
			inv.setItem(25, chatEnabledItem);
		} else {
			inv.setItem(16, chatDisabledIcon);
			inv.setItem(25, chatDisabledItem);
		}
	}

	public ItemStack createEnabledIconItem(Material mat, String name) {
		ItemStack stack = new ItemStack(mat, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§a§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Está atualmente §a§lativado.\\n§7Clique aqui para §e§ldesativar§7."));
		stack.setItemMeta(meta);
		return stack;
	}

	public ItemStack createDisabledIconItem(Material mat, String name) {
		ItemStack stack = new ItemStack(mat, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§c§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Está atualmente §e§ldesativado.\\n§7Clique aqui para §a§lativar§7."));
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
				StringLoreUtils.getLore(30, "§7Está atualmente §a§lativado.\\n§7Clique aqui para §e§ldesativar§7."));
		stack.setItemMeta(meta);
		return stack;
	}

	public ItemStack createDisabledItem(String name) {
		ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§c§l" + name);
		meta.setLore(
				StringLoreUtils.getLore(30, "§7Está atualmente §e§ldesativado.\\n§7Clique aqui para §a§lativar§7."));
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
