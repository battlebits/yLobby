package br.com.battlebits.yutils.character.types;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

import net.minecraft.server.v1_7_R4.EntityWitch;

public class WitchCharacter extends EntityWitch {

	public WitchCharacter() {
		super(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle());
	}

	@Override
	public void h() {
	}

}