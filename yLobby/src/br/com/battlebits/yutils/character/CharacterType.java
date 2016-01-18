package br.com.battlebits.yutils.character;

import org.bukkit.entity.EntityType;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.yutils.character.types.BatCharacter;
import br.com.battlebits.yutils.character.types.BlazeCharacter;
import br.com.battlebits.yutils.character.types.CaveSpiderCharacter;
import br.com.battlebits.yutils.character.types.ChickenCharacter;
import br.com.battlebits.yutils.character.types.CowCharacter;
import br.com.battlebits.yutils.character.types.CreeperCharacter;
import br.com.battlebits.yutils.character.types.EndermanCharacter;
import br.com.battlebits.yutils.character.types.HorseCharacter;
import br.com.battlebits.yutils.character.types.IronGolemCharacter;
import br.com.battlebits.yutils.character.types.MagmaCubeCharacter;
import br.com.battlebits.yutils.character.types.MushroomCowCharacter;
import br.com.battlebits.yutils.character.types.OcelotCharacter;
import br.com.battlebits.yutils.character.types.PigCharacter;
import br.com.battlebits.yutils.character.types.PigZombieCharacter;
import br.com.battlebits.yutils.character.types.SheepCharacter;
import br.com.battlebits.yutils.character.types.SilverfishCharacter;
import br.com.battlebits.yutils.character.types.SkeletonCharacter;
import br.com.battlebits.yutils.character.types.SlimeCharacter;
import br.com.battlebits.yutils.character.types.SnowmanCharacter;
import br.com.battlebits.yutils.character.types.SpiderCharacter;
import br.com.battlebits.yutils.character.types.SquidCharacter;
import br.com.battlebits.yutils.character.types.VillagerCharacter;
import br.com.battlebits.yutils.character.types.WitchCharacter;
import br.com.battlebits.yutils.character.types.WitherCharacter;
import br.com.battlebits.yutils.character.types.WitherSkeletonCharacter;
import br.com.battlebits.yutils.character.types.WolfCharacter;
import br.com.battlebits.yutils.character.types.ZombieCharacter;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.World;

public enum CharacterType {

	BAT(BatCharacter.class, EntityType.BAT), BLAZE(BlazeCharacter.class, EntityType.BLAZE), CAVESPIDER(CaveSpiderCharacter.class,
			EntityType.CAVE_SPIDER), CHICKEN(ChickenCharacter.class, EntityType.CHICKEN), COW(CowCharacter.class, EntityType.COW), CREEPER(
					CreeperCharacter.class, EntityType.CREEPER), ENDERMAN(EndermanCharacter.class, EntityType.ENDERMAN), HORSE(HorseCharacter.class,
							EntityType.HORSE), IRONGOLEM(IronGolemCharacter.class, EntityType.IRON_GOLEM), MAGMACUBE(MagmaCubeCharacter.class,
									EntityType.MAGMA_CUBE), MUSHROOMCOW(MushroomCowCharacter.class, EntityType.MUSHROOM_COW), OCELOT(
											OcelotCharacter.class,
											EntityType.OCELOT), SHEEP(SheepCharacter.class, EntityType.SHEEP), SILVERFISH(SilverfishCharacter.class,
													EntityType.SILVERFISH), SKELETON(SkeletonCharacter.class, EntityType.SKELETON), SLIME(
															SlimeCharacter.class, EntityType.SLIME), SNOWMAN(SnowmanCharacter.class,
																	EntityType.SNOWMAN), SPIDER(SpiderCharacter.class, EntityType.SPIDER), SQUID(
																			SquidCharacter.class,
																			EntityType.SQUID), PIG(PigCharacter.class, EntityType.PIG), PIGZOMBIE(
																					PigZombieCharacter.class,
																					EntityType.PIG_ZOMBIE), VILLAGER(VillagerCharacter.class,
																							EntityType.VILLAGER), WITCH(WitchCharacter.class,
																									EntityType.WITCH), WITHER(WitherCharacter.class,
																											EntityType.WITCH), WITHERSKELETON(
																													WitherSkeletonCharacter.class,
																													EntityType.SKELETON), WOLF(
																															WolfCharacter.class,
																															EntityType.WOLF), ZOMBIE(
																																	ZombieCharacter.class,
																																	EntityType.ZOMBIE);

	@SuppressWarnings("deprecation")
	private CharacterType(Class<? extends EntityInsentient> eClass, EntityType type) {
		try {
			yLobbyPlugin.getyLobby().getzUtils().getNMSUtils().registerCustomEntity(eClass, name().toUpperCase() + "CHARACTER", type.getTypeId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EntityLiving getEntity(World world) {
		switch (this) {
		case BAT:
			return new BatCharacter(world);
		case BLAZE:
			return new BlazeCharacter(world);
		case CAVESPIDER:
			return new CaveSpiderCharacter(world);
		case CHICKEN:
			return new ChickenCharacter(world);
		case COW:
			return new CowCharacter(world);
		case CREEPER:
			return new CreeperCharacter(world);
		case ENDERMAN:
			return new EndermanCharacter(world);
		case HORSE:
			return new HorseCharacter(world);
		case IRONGOLEM:
			return new IronGolemCharacter(world);
		case MAGMACUBE:
			return new MagmaCubeCharacter(world);
		case MUSHROOMCOW:
			return new MushroomCowCharacter(world);
		case OCELOT:
			return new OcelotCharacter(world);
		case SHEEP:
			return new SheepCharacter(world);
		case SILVERFISH:
			return new SilverfishCharacter(world);
		case SKELETON:
			return new SkeletonCharacter(world);
		case SLIME:
			return new SlimeCharacter(world);
		case SNOWMAN:
			return new SnowmanCharacter(world);
		case SPIDER:
			return new SpiderCharacter(world);
		case SQUID:
			return new SquidCharacter(world);
		case PIG:
			return new PigCharacter(world);
		case PIGZOMBIE:
			return new PigZombieCharacter(world);
		case VILLAGER:
			return new VillagerCharacter(world);
		case WITCH:
			return new WitchCharacter(world);
		case WITHER:
			return new WitherCharacter(world);
		case WITHERSKELETON:
			return new WitherSkeletonCharacter(world);
		case WOLF:
			return new WolfCharacter(world);
		case ZOMBIE:
			return new ZombieCharacter(world);
		}
		return new VillagerCharacter(world);
	}

}
