package br.com.battlebits.ylobby.customplayer;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;

public class CustomPlayerNPC {

	private EntityPlayer entityPlayer;
	private CraftPlayer craftPlayer;
	private GameProfile gameProfile;
	private CustomPlayerManager manager;

	public CustomPlayerNPC(String npcName, String npcSkinUrl, Location npcLocation, CustomPlayerManager playerManager) {
		manager = playerManager;

		gameProfile = new GameProfile(UUID.randomUUID(), npcName);
		gameProfile.getProperties().removeAll("textures");
		gameProfile.getProperties().put("textures", new Property("textures", npcSkinUrl));

		entityPlayer = new EntityPlayer(manager.getNmsServer(), manager.getNmsWorld(npcLocation.getWorld()), gameProfile,
				new PlayerInteractManager(manager.getNmsWorld(npcLocation.getWorld())));
		entityPlayer.playerConnection = new PlayerConnection(manager.getNmsServer(), new CustomPlayerNetworkManager(), entityPlayer);
		entityPlayer.setLocation(npcLocation.getX(), npcLocation.getY(), npcLocation.getZ(), npcLocation.getYaw(), npcLocation.getPitch());
		manager.setNPCHeadYaw(entityPlayer, npcLocation.getYaw());

		craftPlayer = entityPlayer.getBukkitEntity();

		entityPlayer.spawnIn(manager.getNmsWorld(npcLocation.getWorld()));
		manager.getNmsWorld(npcLocation.getWorld()).players.remove(entityPlayer);

	}

	public CraftPlayer getBukkitEntity() {
		return craftPlayer;
	}

	public EntityPlayer getNMSEntity() {
		return entityPlayer;
	}

	public GameProfile getGameProfile() {
		return gameProfile;
	}

}
