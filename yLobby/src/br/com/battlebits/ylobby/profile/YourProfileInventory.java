package br.com.battlebits.ylobby.profile;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.ranking.constructors.Account;

public class YourProfileInventory {

	private ItemStack ranksItem;
	private ItemStack tagsItem;

	public YourProfileInventory() {
		ranksItem = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta ranksMeta = ranksItem.getItemMeta();
		ranksMeta.setDisplayName("§e§lInformacoes do seu Grupo");
		ranksMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils()
				.formatForLore("§7Clique aqui para saber mais informacoes sobre o seu e os demais grupos do servidor!"));
		ranksItem.setItemMeta(ranksMeta);

		tagsItem = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta tagsMeta = tagsItem.getItemMeta();
		tagsMeta.setDisplayName("§5§lTags disponiveis no servidor");
		tagsMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils()
				.formatForLore("§7Clique aqui para saber mais informacoes sobre todas as Tags existentes no servidor!"));
		tagsItem.setItemMeta(tagsMeta);
	}

	public void open(Player p) {
		String name = yLobbyPlugin.getyLobby().getzUtils().getUUIDUtils().getNameByUUID(p.getUniqueId().toString());
		Account account = Main.getPlugin().getRankingManager().getAccount(p.getUniqueId());
		Inventory inv = Bukkit.createInventory(null, 27, "              §nSeu Perfil");

		ItemStack mainItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta mainMeta = (SkullMeta) mainItem.getItemMeta();
		mainMeta.setOwner(name);
		mainMeta.setDisplayName("§9§l" + name);
		mainMeta.setLore(Arrays.asList("§0", "§7Grupo: §3§l" + Main.getPlugin().getPermissionManager().getPlayerGroup(p.getUniqueId()).name(),
				"§7Fichas: §3§l" + account.getFichas(), "§7Moedas: §3§l" + account.getMoney(), "§7Liga: §3§l" + account.getLiga().name(),
				"§7XP: §3§l" + account.getXp(),
				"§0")); /**
						 * "§b§lClique §bpara ver", "§bmais §b§lestatisticas§b."
						 * , "§0"));
						 */
		mainItem.setItemMeta(mainMeta);
		inv.setItem(11, ranksItem);
		inv.setItem(13, mainItem);
		inv.setItem(15, tagsItem);

		p.openInventory(inv);

	}

}
