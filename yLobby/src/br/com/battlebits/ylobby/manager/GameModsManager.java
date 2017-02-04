package br.com.battlebits.ylobby.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.gamemode.GameModeBase;
import br.com.battlebits.ylobby.gamemode.GameModeMatch;
import br.com.battlebits.ylobby.gamemode.GameModeMulti;
import br.com.battlebits.ylobby.gamemode.GameModeSimple;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.citizensnpcs.trait.Gravity;

public class GameModsManager {

	private Map<ServerType, GameModeBase> gameModeBases;

	public GameModsManager() {
		gameModeBases = new HashMap<>();
		loadGameMods();
	}

	public void addGameMode(GameModeBase gameModeBase) {
		gameModeBases.put(gameModeBase.getServerType(), gameModeBase);
	}

	public Collection<GameModeBase> getGameModes() {
		return gameModeBases.values();
	}

	public boolean isGameMode(ServerType type) {
		return gameModeBases.containsKey(type);
	}

	public GameModeBase getGameMode(ServerType type) {
		return gameModeBases.get(type);
	}

	public void loadGameMods() {

		addGameMode(new GameModeMulti("§%pvpfulliron-item%§", "§%pvpfulliron-item-lore%§", Material.DIAMOND_SWORD,
				new Location(Bukkit.getWorlds().get(0), 4, 67, 26), new BungeeMessage("PVPFulliron"),
				ServerType.PVP_FULLIRON, EntityType.SKELETON) {
			@Override
			public int getOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				LobbyMain.getInstance().getMultiSelectorManager().getMultiSelector(getServerType()).open(p);
			}
		});

		addGameMode(new GameModeMulti("§%pvpsimulator-item%§", "§%pvpsimulator-item-lore%§", Material.WOOD_SWORD,
				new Location(Bukkit.getWorlds().get(0), 8, 67, 25), new BungeeMessage("PVPSimulator"),
				ServerType.PVP_SIMULATOR, EntityType.SKELETON) {
			@Override
			public int getOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				LobbyMain.getInstance().getMultiSelectorManager().getMultiSelector(getServerType()).open(p);
			}
		});
		addGameMode(new GameModeMatch("§%hungergames-item%§", "§%hungergames-item-lore%§", Material.MUSHROOM_SOUP,
				new Location(Bukkit.getWorlds().get(0), -7, 67, 25), new BungeeMessage("Hungergames"),
				ServerType.HUNGERGAMES, EntityType.BLAZE) {
			@Override
			public int getOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				LobbyMain.getInstance().getMatchSelectorManager().getMatchSelector(getServerType()).open(p);
			}
		});

		addGameMode(new GameModeMatch("§%hungergames-custom-item%§", "§%hungergames-custom-item-lore%§",
				Material.WORKBENCH, new Location(Bukkit.getWorlds().get(0), 0, 67, 27),
				new BungeeMessage("CustomHungergames"), ServerType.CUSTOMHG, EntityType.WITCH) {
			@Override
			public int getOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				LobbyMain.getInstance().getMatchSelectorManager().getMatchSelector(getServerType()).open(p);
			}
		});

		addGameMode(new GameModeMatch("§%hungergames-doublekit-item%§", "§%hungergames-doublekit-item-lore%§",
				Material.RED_MUSHROOM, new Location(Bukkit.getWorlds().get(0), -11, 67, 23),
				new BungeeMessage("DoubleKitHungergames"), ServerType.DOUBLEKITHG, EntityType.CREEPER) {
			@Override
			public int getOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(getServerType()).getTotalNumber();
			}

			@Override
			public void onRightClick(Player p) {
				LobbyMain.getInstance().getMatchSelectorManager().getMatchSelector(getServerType()).open(p);
			}
		});
		addGameMode(new GameModeSimple("§%raid-item%§", "§%raid-item-lore%§", Material.DIAMOND_PICKAXE,
				"raid.battlebits.com.br", new Location(Bukkit.getWorlds().get(0), 12, 67, 23), ServerType.RAID,
				EntityType.ZOMBIE));

		Location npclocation = new Location(Bukkit.getWorlds().get(0), -3, 67, 26);
		NPC characterNPC = CitizensAPI.getNamedNPCRegistry("lobby").createNPC(EntityType.PLAYER, " ");
		characterNPC.data().set(NPC.NAMEPLATE_VISIBLE_METADATA, true);
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
		SkinnableEntity skinnable = characterNPC.getEntity() instanceof SkinnableEntity
				? (SkinnableEntity) characterNPC.getEntity() : null;
		if (skinnable != null) {
			skinnable.setSkinName("Hypixel");
		}
		Hologram holo = HologramsAPI.createHologram(LobbyMain.getInstance(),
				LobbyUtils.getLocationUtils().getCenter(npclocation.clone().add(0, 2.54, 0), false));
		holo.appendTextLine("§b§l§%soon%§");
		holo.appendTextLine("§e§lNEW MINIGAME.");
	}

	public void stop() {
		for (GameModeBase base : gameModeBases.values()) {
			base.getCharacterNPC().despawn();
		}

		gameModeBases.clear();
	}

}
