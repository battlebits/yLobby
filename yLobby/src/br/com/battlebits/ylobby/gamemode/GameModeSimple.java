package br.com.battlebits.ylobby.gamemode;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.commons.core.server.loadbalancer.server.BattleServer;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public class GameModeSimple extends GameModeBase {

	private String bungeeId;
	private BungeeMessage connectMessage;

	public GameModeSimple(final String servername, String serverdescription, Material iconmaterial,
			String serverbungeeid, Location npclocation, ServerType serverType, EntityType type) {
		super(servername, serverdescription, iconmaterial, Arrays.asList("§b§l§%click-to-connect%§"), npclocation,
				serverType, type);
		bungeeId = serverbungeeid;
		connectMessage = new BungeeMessage("Connect", bungeeId);
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
						new FixedMetadataValue(LobbyMain.getInstance(), "SIMPLE"));
				getCharacterNPC().getEntity().setMetadata("GM_NAME",
						new FixedMetadataValue(LobbyMain.getInstance(), servername));
			}
		}.runTask(LobbyMain.getInstance());
	}

	@Override
	public int getOnlinePlayers() {
		return getServerInfo().getOnlinePlayers();
	}

	@Override
	public void updateOnlinePlayersOnItem() {
		super.updateOnlinePlayersOnItem();
	}

	public void connect(Player p) {
		p.sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", connectMessage.getDataOutput().toByteArray());
	}

	public BattleServer getServerInfo() {
		return LobbyMain.getInstance().getServerManager().getServer(bungeeId);
	}

}
