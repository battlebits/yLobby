package br.com.battlebits.ylobby.gamemode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.yutils.character.CharacterNPC;
import br.com.battlebits.yutils.character.CharacterType;
import de.inventivegames.holograms.Hologram;
import de.inventivegames.holograms.HologramAPI;

public abstract class GameModeBase {

	private String name;
	private ItemStack inventoryitem;
	private int substractLines;
	private ItemMeta inventoryItemMeta;
	private ArrayList<String> inventoryItemLore;
	private CharacterNPC characterNPC;
	private Hologram onlinePlayersHologram;

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
						yLobbyPlugin.getyLobby().getzUtils().getLocationUtils().getCenter(npclocation.clone(), false),
						yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation()),
				inventoryitem);
		if (!npclocation.getChunk().isLoaded()) {
			npclocation.getChunk().load();
		}
		onlinePlayersHologram = HologramAPI.createWorldHologram(
				yLobbyPlugin.getyLobby().getzUtils().getLocationUtils().getCenter(npclocation.clone().add(0, 2, 0), true),
				"§b§l0 §bjogadores agora!");
		onlinePlayersHologram.spawn();
		if (!npclocation.getChunk().isLoaded()) {
			npclocation.getChunk().load();
		}
		onlinePlayersHologram.addLineAbove("§9§l" + servername.toUpperCase());
	}

	public CharacterNPC getCharacterNPC() {
		return characterNPC;
	}

	public String getServerName() {
		return name;
	}

	public ItemStack getInventoryItem() {
		return inventoryitem;
	}

	public void updateOnlinePlayersOnItem() {
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
