package br.com.battlebits.yutils.character.types;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

import net.minecraft.server.v1_7_R4.EntitySkeleton;

public class WitherSkeletonCharacter extends EntitySkeleton {

	public WitherSkeletonCharacter() {
		super(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle());
		setSkeletonType(1);
	}

	@Override
	public void h() {
	}

}