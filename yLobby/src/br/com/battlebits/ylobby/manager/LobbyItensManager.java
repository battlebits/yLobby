package br.com.battlebits.ylobby.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class LobbyItensManager implements ManagerBase{

	private ItemStack showPlayersItem;
	private ItemStack hidePlayersItem;
	private ItemStack lobbyItem;
	private ItemStack gameModeItem;
	private ItemStack funItem;
	private ItemStack profileItem;
	private SkullMeta profileMeta;

	public void start() {
		showPlayersItem = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta showPlayersMeta = showPlayersItem.getItemMeta();
		showPlayersMeta.setDisplayName("§a§lMostrar Jogadores §7(Clique)");
		showPlayersMeta
				.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para mostrar todos os jogadores"));
		showPlayersItem.setItemMeta(showPlayersMeta);
		hidePlayersItem = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta hidePlayersMeta = hidePlayersItem.getItemMeta();
		hidePlayersMeta.setDisplayName("§e§lEsconder Jogadores §7(Clique)");
		hidePlayersMeta
				.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para esconder todos os jogadores"));
		hidePlayersItem.setItemMeta(hidePlayersMeta);
		lobbyItem = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta lobbyMeta = lobbyItem.getItemMeta();
		lobbyMeta.setDisplayName("§5§lLobbys §7(Clique)");
		lobbyMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para ver e trocar entre de Lobby"));
		lobbyItem.setItemMeta(lobbyMeta);
		gameModeItem = new ItemStack(Material.COMPASS, 1);
		ItemMeta gameModeMeta = gameModeItem.getItemMeta();
		gameModeMeta.setDisplayName("§9§lModos de Jogo §7(Clique)");
		gameModeMeta.setLore(
				yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para ver os modos de jogo disponiveis!"));
		gameModeItem.setItemMeta(gameModeMeta);
		gameModeItem = yLobbyPlugin.getyLobby().getzUtils().getItemUtils().addGlow(gameModeItem);
		funItem = new ItemStack(Material.FEATHER, 1);
		ItemMeta parkourMeta = funItem.getItemMeta();
		parkourMeta.setDisplayName("§b§lDiversão §7(Em Breve)");
		parkourMeta.setLore(
				yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para ver os modos de jogo disponiveis!"));
		funItem.setItemMeta(parkourMeta);
		profileItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		profileMeta = (SkullMeta) profileItem.getItemMeta();
		profileMeta.setDisplayName("§6§lPerfil §7(Clique)");
		profileMeta.setLore(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore("§7Clique aqui para ver o seu perfil!"));
		profileItem.setItemMeta(profileMeta);
	}

	public void setItems(Player p) {
		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		p.getInventory().setItem(0, gameModeItem);
		profileMeta.setOwner(p.getName());
		profileItem.setItemMeta(profileMeta);
		p.getInventory().setItem(1, profileItem);
		p.getInventory().setItem(6, hidePlayersItem);
		p.getInventory().setItem(7, funItem);
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
		return funItem;
	}

	public ItemStack getProfileItem() {
		return profileItem;
	}

	@Override
	public void stop() {
		gameModeItem = null;
		profileItem = null;
		profileMeta = null;
		hidePlayersItem = null;
		funItem = null;
		lobbyItem = null;
		showPlayersItem = null;
	}

}
