package br.com.battlebits.ylobby.customplayer;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.WorldServer;

public class CustomPlayerManager {

	private JavaPlugin javaPlugin;
	private MinecraftServer nmsServer;
	private ArrayList<CustomPlayerNPC> customPlayerNPCs;

	public CustomPlayerManager(JavaPlugin plugin) {
		nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		javaPlugin = plugin;
		customPlayerNPCs = new ArrayList<>();
	}

	public ArrayList<CustomPlayerNPC> getAllNPCS() {
		return customPlayerNPCs;
	}

	public MinecraftServer getNmsServer() {
		return nmsServer;
	}

	public WorldServer getNmsWorld(World world) {
		return ((CraftWorld) world).getHandle();
	}

	public void setNPCHeadYaw(Entity en, float yaw) {
		if (!(en instanceof EntityLiving))
			return;
		EntityLiving handle = (EntityLiving) en;
		while (yaw < -180.0F) {
			yaw += 360.0F;
		}
		while (yaw >= 180.0F) {
			yaw -= 360.0F;
		}
		handle.aO = yaw;
		if (!(handle instanceof EntityHuman))
			handle.aM = yaw;
		handle.aP = yaw;
	}

}
