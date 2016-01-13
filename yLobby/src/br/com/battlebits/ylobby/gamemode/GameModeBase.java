package br.com.battlebits.ylobby.gamemode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.yutils.character.CharacterNPC;
import br.com.battlebits.yutils.character.CharacterType;
import de.inventivegames.holograms.Hologram;

public abstract class GameModeBase {

	private String name;
	private ItemStack inventoryitem;
	private int substractLines;
	private ItemMeta inventoryItemMeta;
	private ArrayList<String> inventoryItemLore;
	private CharacterNPC characterNPC;
	private Hologram itemHologram;

	public GameModeBase(String servername, String serverdescription, Material iconmaterial, List<String> connectLines, Location npclocation,
			CharacterType npcType) {
		name = servername;
		inventoryitem = new ItemStack(iconmaterial, 1);
		inventoryItemMeta = inventoryitem.getItemMeta();
		inventoryItemMeta.setDisplayName("§9§l" + servername);
		inventoryItemLore = new ArrayList<>();
		inventoryItemLore.add("§0");
		inventoryItemLore.addAll(yLobbyPlugin.getyLobby().getzUtils().getItemUtils().formatForLore(serverdescription));
		inventoryItemLore.add("§0");
		inventoryItemLore.add("§b§30 §7jogadores online.");
		inventoryItemLore.add("§0");
		for (String s : connectLines) {
			inventoryItemLore.add(s);
		}
		inventoryItemLore.add("§0");
		inventoryItemMeta.setLore(inventoryItemLore);
		inventoryitem.setItemMeta(inventoryItemMeta);
		substractLines = connectLines.size();
		if (!npclocation.getChunk().isLoaded()) {
			npclocation.getChunk().load();
		}
		characterNPC = new CharacterNPC(npcType,
				yLobbyPlugin.getyLobby().getzUtils().getLocationUtils().lookAt(
						yLobbyPlugin.getyLobby().getzUtils().getLocationUtils().getCenter(npclocation, false),
						yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation()),
				inventoryitem);
//		HologramAPI.createHologram(npclocation.clone().add(0, 2.5, 0), "Pirokao").spawn();
//		itemHologram = HologramAPI.createWorldItemHologram(
//				yLobbyPlugin.getyLobby().getzUtils().getLocationUtils().getCenter(npclocation.clone().add(0, 3, 0), false), inventoryitem);
//		itemHologram.spawn();
	}

	public CharacterNPC getCharacterNPC() {
		return characterNPC;
	}

	public Hologram getItemHologram() {
		return itemHologram;
	}

	public String getServerName() {
		return name;
	}

	public ItemStack getInventoryItem() {
		return inventoryitem;
	}

	public void updateOnlinePlayersOnItem() {
		// new BukkitRunnable() {
		// @Override
		// public void run() {
		// playerNPC.getHologram().setText("§3§l" + getOnlinePlayers() + "
		// §7jogadores");
		//
		// }
		// }.runTask(yLobbyPlugin.getyLobby());
		inventoryItemLore.set(inventoryItemLore.size() - (3 + substractLines), "§3§l" + getOnlinePlayers() + " §7jogadores online.");
		inventoryItemMeta.setLore(inventoryItemLore);
		inventoryitem.setItemMeta(inventoryItemMeta);
	}

	public abstract int getOnlinePlayers();

	public GameModeType getGameModeType() {
		if (this instanceof GameModeSimple) {
			return GameModeType.SIMPLE;
		} else if (this instanceof GameModeMatch) {
			return GameModeType.MATCH;
		} else {
			return GameModeType.UNKNOW;
		}
	}

}
