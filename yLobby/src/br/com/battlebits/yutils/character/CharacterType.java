package br.com.battlebits.yutils.character;

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
import net.minecraft.server.v1_7_R4.EntityLiving;

public enum CharacterType {

	BAT(BatCharacter.class, 65), BLAZE(BlazeCharacter.class, 61), CAVESPIDER(CaveSpiderCharacter.class, 59), CHICKEN(ChickenCharacter.class,
			93), COW(CowCharacter.class, 92), CREEPER(CreeperCharacter.class, 50), ENDERMAN(EndermanCharacter.class, 58), HORSE(HorseCharacter.class,
					100), IRONGOLEM(IronGolemCharacter.class, 99), MAGMACUBE(MagmaCubeCharacter.class, 62), MUSHROOMCOW(MushroomCowCharacter.class,
							96), OCELOT(OcelotCharacter.class, 98), SHEEP(SheepCharacter.class, 91), SILVERFISH(SilverfishCharacter.class,
									60), SKELETON(SkeletonCharacter.class, 51), SLIME(SlimeCharacter.class, 55), SNOWMAN(SnowmanCharacter.class,
											97), SPIDER(SpiderCharacter.class, 52), SQUID(SquidCharacter.class, 94), PIG(PigCharacter.class,
													90), PIGZOMBIE(PigZombieCharacter.class, 57), VILLAGER(VillagerCharacter.class, 120), WITCH(
															WitchCharacter.class,
															66), WITHER(WitherCharacter.class, 64), WITHERSKELETON(WitherSkeletonCharacter.class,
																	51), WOLF(WolfCharacter.class, 95), ZOMBIE(ZombieCharacter.class, 36);

	private Class<? extends EntityLiving> entityclass;

	private CharacterType(Class<? extends EntityLiving> eClass, int defaultID) {
		try {
			yLobbyPlugin.getyLobby().getzUtils().getNMSUtils().registerCustomEntity(eClass, name() + "CHARACTER", defaultID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		entityclass = eClass;
	}

	public EntityLiving getEntity() {
		try {
			return entityclass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
