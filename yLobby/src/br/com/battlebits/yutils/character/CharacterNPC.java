package br.com.battlebits.yutils.character;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

import br.com.battlebits.ylobby.yLobbyPlugin;
import net.minecraft.server.v1_7_R4.EntityLiving;

public class CharacterNPC {

	private EntityLiving entity;

	public CharacterNPC(CharacterType type, Location loc, ItemStack itemInHand) {
		entity = type.getEntity();
		yLobbyPlugin.getyLobby().getzUtils().getNMSUtils().setHeadYaw(entity, loc.getYaw());
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		yLobbyPlugin.getyLobby().getzUtils().getNMSUtils().getWorldServer(loc.getWorld()).addEntity(entity, SpawnReason.CUSTOM);
		entity.setInvisible(false);
		entity.setEquipment(0, CraftItemStack.asNMSCopy(itemInHand));
	}

	public EntityLiving getNMSEntity() {
		return entity;
	}

	public CraftEntity getBukkitEntity() {
		return entity.getBukkitEntity();
	}

	public void remove() {
		entity.getBukkitEntity().remove();
	}

}