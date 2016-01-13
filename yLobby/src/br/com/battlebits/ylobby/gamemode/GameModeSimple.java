package br.com.battlebits.ylobby.gamemode;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.server.ServerInfo;
import br.com.battlebits.yutils.character.CharacterType;

public class GameModeSimple extends GameModeBase {

	private String bungeeId;
	private BungeeMessage connectMessage;

	public GameModeSimple(final String servername, String serverdescription, Material iconmaterial, String serverbungeeid, Location npclocation,
			CharacterType characterType) {
		super(servername, serverdescription, iconmaterial, Arrays.asList("§b§lClique §bpara §b§lconectar§b."), npclocation, characterType);
		bungeeId = serverbungeeid;
		if (!yLobbyPlugin.getyLobby().getServerInfoManager().isRegistred(bungeeId)) {
			yLobbyPlugin.getyLobby().getServerInfoManager().addServer(bungeeId);
		}
		connectMessage = new BungeeMessage("Connect", bungeeId);
		new BukkitRunnable() {
			@Override
			public void run() {
				getCharacterNPC().getBukkitEntity().setMetadata("GM_CONNECT",
						new FixedMetadataValue(yLobbyPlugin.getyLobby(), connectMessage.getMessageArgs()));
				getCharacterNPC().getBukkitEntity().setMetadata("GM_TYPE", new FixedMetadataValue(yLobbyPlugin.getyLobby(), "SIMPLE"));
				getCharacterNPC().getBukkitEntity().setMetadata("GM_NAME", new FixedMetadataValue(yLobbyPlugin.getyLobby(), servername));
			}
		}.runTask(yLobbyPlugin.getyLobby());
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
		p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", connectMessage.getDataOutput().toByteArray());
	}

	public ServerInfo getServerInfo() {
		return yLobbyPlugin.getyLobby().getServerInfoManager().get(bungeeId);
	}

}
