package br.com.battlebits.ylobby.gamemode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.commons.util.string.StringLoreUtils;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.LobbyMain;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Gravity;
import net.md_5.bungee.api.ChatColor;

public abstract class GameModeBase {

	private String name;
	private ItemStack inventoryitem;
	private int substractLines;
	private ItemMeta inventoryItemMeta;
	private ArrayList<String> inventoryItemLore;
	@Getter
	private NPC characterNPC;

	@Getter
	private ServerType serverType;

	public GameModeBase(String servername, String serverdescription, Material iconmaterial, List<String> connectLines,
			Location npclocation, ServerType serverType, EntityType type) {
		name = servername;
		this.serverType = serverType;
		inventoryitem = new ItemStack(iconmaterial, 1);
		inventoryItemMeta = inventoryitem.getItemMeta();
		inventoryItemMeta.setDisplayName("§9§l" + servername);
		inventoryItemLore = new ArrayList<>();
		inventoryItemLore.addAll(StringLoreUtils.formatForLore(ChatColor.GRAY + serverdescription));
		inventoryItemLore.add("§0");
		inventoryItemLore.add("§b§30 §7§%players-online%§.");
		inventoryItemLore.add("§0");
		for (String s : connectLines) {
			inventoryItemLore.add(s);
		}
		inventoryItemMeta.setLore(inventoryItemLore);
		inventoryitem.setItemMeta(inventoryItemMeta);
		substractLines = connectLines.size();
		characterNPC = CitizensAPI.getNamedNPCRegistry("lobby").createNPC(type, servername);
		characterNPC.data().set(NPC.NAMEPLATE_VISIBLE_METADATA, false);
		characterNPC.data().set(NPC.AMBIENT_SOUND_METADATA, "");
		characterNPC.data().set(NPC.HURT_SOUND_METADATA, "");
		characterNPC.data().set(NPC.DEATH_SOUND_METADATA, "");
		Gravity gravity = characterNPC.getTrait(Gravity.class);
		if (gravity != null)
			gravity.gravitate(true);
		characterNPC.setProtected(true);
		if (!npclocation.getChunk().isLoaded()) {
			npclocation.getChunk().load();
		}
		characterNPC.spawn(LobbyUtils.getLocationUtils().lookAt(
				LobbyUtils.getLocationUtils().getCenter(npclocation.clone(), false),
				LobbyMain.getInstance().getLocationManager().getSpawnLocation()));
		Hologram holo = HologramsAPI.createHologram(LobbyMain.getInstance(),
				LobbyUtils.getLocationUtils().getCenter(npclocation.clone().add(0, 2.54, 0), false));
		holo.appendTextLine("§9§l" + servername.toUpperCase());
		holo.appendTextLine("§b§l§%click-to-connect%§!");
		if (!npclocation.getChunk().isLoaded()) {
			npclocation.getChunk().load();
		}
	}

	public String getServerName() {
		return name;
	}

	public ItemStack getInventoryItem() {
		return inventoryitem;
	}

	public void updateOnlinePlayersOnItem() {
		inventoryItemLore.set(inventoryItemLore.size() - (2 + substractLines),
				"§3§l" + getOnlinePlayers() + " §7§%players-online%§.");
		inventoryItemMeta.setLore(inventoryItemLore);
		inventoryitem.setItemMeta(inventoryItemMeta);
	}

	public abstract int getOnlinePlayers();

	public GameModeType getGameModeType() {
		if (this instanceof GameModeSimple) {
			return GameModeType.SIMPLE;
		} else if (this instanceof GameModeMatch) {
			return GameModeType.MATCH;
		} else if (this instanceof GameModeMulti) {
			return GameModeType.MULTI;
		} else {
			return GameModeType.UNKNOW;
		}
	}

}
