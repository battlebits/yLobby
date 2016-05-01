package br.com.battlebits.ylobby.profile;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.battlebits.ycommon.common.BattlebitsAPI;
import br.com.battlebits.ycommon.common.account.BattlePlayer;
import br.com.battlebits.ylobby.yLobbyPlugin;

public class YourProfileInventory {

	private ItemStack ranksItem;
	private ItemStack tagsItem;
	private ItemStack configItem;

	public YourProfileInventory() {
		ranksItem = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta ranksMeta = ranksItem.getItemMeta();
		ranksMeta.setDisplayName("§e§lInformacoes do seu Grupo");
		ranksMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para saber mais informacoes sobre o seu e os demais grupos do servidor!"));
		ranksItem.setItemMeta(ranksMeta);

		tagsItem = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta tagsMeta = tagsItem.getItemMeta();
		tagsMeta.setDisplayName("§5§lTags disponiveis no servidor");
		tagsMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para saber mais informacoes sobre todas as Tags existentes no servidor!"));
		tagsItem.setItemMeta(tagsMeta);

		configItem = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
		ItemMeta configMeta = configItem.getItemMeta();
		configMeta.setDisplayName("§c§lPreferencias");
		configMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para editar suas preferencias no servidor!"));
		configItem.setItemMeta(configMeta);
	}

	public void open(Player p) {
		String name = p.getName();
		BattlePlayer account = BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId());
		Inventory inv = Bukkit.createInventory(null, 27, "              §nSeu Perfil");

		ItemStack mainItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta mainMeta = (SkullMeta) mainItem.getItemMeta();
		mainMeta.setOwner(name);
		mainMeta.setDisplayName("§9§l" + name);

		mainMeta.setLore(Arrays.asList("§0", //
				"§7Rank: " + account.getTag().getPrefix(account.getLanguage()), //
				"§7Liga: " + account.getLiga().getSymbol() + " " + account.getLiga().toString(), //
				"§7XP: §b" + account.getXp(), //
				"§7Moedas: §b" + account.getMoney(), //
				"§7Fichas: §b" + account.getFichas(), //
				"§0"));
		/**
		 * "§b§lClique §bpara ver", "§bmais §b§lestatisticas§b." , "§0"));
		 */

		mainItem.setItemMeta(mainMeta);
		inv.setItem(11, ranksItem);
		inv.setItem(13, mainItem);
		// inv.setItem(15, tagsItem);
		inv.setItem(15, configItem);

		p.openInventory(inv);

	}

}
