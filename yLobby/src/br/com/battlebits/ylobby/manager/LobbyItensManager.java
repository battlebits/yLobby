package br.com.battlebits.ylobby.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.battlebits.commons.api.item.ItemBuilder;

public class LobbyItensManager {

	private ItemStack showPlayersItem;
	private ItemStack hidePlayersItem;
	private ItemStack lobbyItem;
	private ItemStack gameModeItem;
	private ItemStack parkourItem;
	private ItemStack profileItem;
	private SkullMeta profileMeta;
	private ItemStack cosmeticsItem;

	public LobbyItensManager() {
		showPlayersItem = new ItemBuilder().type(Material.INK_SACK).durability(10)
				.name("§a§lMostrar Jogadores §7(Clique)").lore("§7Clique aqui para mostrar todos os jogadores").build();

		hidePlayersItem = new ItemBuilder().type(Material.INK_SACK).durability(8)
				.name("§e§lEsconder Jogadores §7(Clique)").lore("§7Clique aqui para esconder todos os jogadores")
				.build();
		lobbyItem = new ItemBuilder().type(Material.NETHER_STAR).name("§5§lLobbys §7(Clique)")
				.lore("§7Clique aqui para ver e trocar entre de Lobby").build();
		gameModeItem = new ItemBuilder().type(Material.COMPASS).name("§9§lModos de Jogo §7(Clique)")
				.lore("§7Clique aqui para ver os modos de jogo disponiveis!").glow().build();
		parkourItem = new ItemBuilder().type(Material.FEATHER).name("§b§lDiversão §7(Em Breve)")
				.lore("§7Clique aqui para ver os modos de jogo disponiveis!").build();
		profileItem = new ItemBuilder().type(Material.SKULL_ITEM).durability(3).name("§6§lPerfil §7(Clique)")
				.lore("§7Clique aqui para ver o seu perfil!").build();
		profileMeta = (SkullMeta) profileItem.getItemMeta();
		cosmeticsItem = new ItemBuilder().type(Material.ENDER_CHEST).name("§a§lCosmeticos §7(Clique)")
				.lore("§7Clique aqui para abrir o menu de Cosmeticos.").build();
	}

	public void setItems(Player p) {
		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		p.getInventory().setItem(0, gameModeItem);
		profileMeta.setOwner(p.getName());
		profileItem.setItemMeta(profileMeta);
		p.getInventory().setItem(1, profileItem);
		p.getInventory().setItem(3, cosmeticsItem);
		p.getInventory().setItem(6, hidePlayersItem);
		p.getInventory().setItem(7, parkourItem);
		p.getInventory().setItem(8, lobbyItem);
		p.getInventory().setHeldItemSlot(0);
	}

	public ItemStack getHidePlayersItem() {
		return hidePlayersItem;
	}

	public ItemStack getShowPlayersItem() {
		return showPlayersItem;
	}

	public ItemStack getLobbyItem() {
		return lobbyItem;
	}

	public ItemStack getGameModeItem() {
		return gameModeItem;
	}

	public ItemStack getParkourItem() {
		return parkourItem;
	}

	public ItemStack getProfileItem() {
		return profileItem;
	}

}
