package br.com.battlebits.ylobby.gamemode;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public abstract class GameModeMatch extends GameModeBase {

	private BungeeMessage connectBungeeMessage;

	public GameModeMatch(final String servername, String serverdescription, Material iconmaterial, Location npclocation,
			final BungeeMessage connectMessage, ServerType serverType, EntityType type) {
		super(servername, serverdescription, iconmaterial,
				Arrays.asList("§b§l§%left-click-to-connect%§.", "§0", "§e§l§%right-click-to-see%§."), npclocation,
				serverType, type);
		connectBungeeMessage = connectMessage;
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!getCharacterNPC().isSpawned()) {
					getCharacterNPC().spawn(LobbyUtils.getLocationUtils().getCenter(npclocation, true));
					return;
				}
				getCharacterNPC().getEntity().setMetadata("GM_CONNECT",
						new FixedMetadataValue(LobbyMain.getInstance(), connectMessage.getMessageArgs()));
				getCharacterNPC().getEntity().setMetadata("GM_TYPE",
						new FixedMetadataValue(LobbyMain.getInstance(), "MATCH"));
				getCharacterNPC().getEntity().setMetadata("GM_NAME",
						new FixedMetadataValue(LobbyMain.getInstance(), servername));
			}
		}.runTask(LobbyMain.getInstance());
	}

	@Override
	public abstract int getOnlinePlayers();

	public abstract void onRightClick(Player p);

	public void onLeftClick(Player p) {
		p.sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", connectBungeeMessage.getDataOutput().toByteArray());
	}

}
