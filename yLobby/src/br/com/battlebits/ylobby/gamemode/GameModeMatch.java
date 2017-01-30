package br.com.battlebits.ylobby.gamemode;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public abstract class GameModeMatch extends GameModeBase {

	private BungeeMessage connectBungeeMessage;

	public GameModeMatch(final String servername, String serverdescription, Material iconmaterial, Location npclocation,
			final BungeeMessage connectMessage, EntityType type) {
		super(servername, serverdescription, iconmaterial,
				Arrays.asList("§b§lClique esquerdo §bpara ", "§b§lconectar §ba um §b§lservidor§b.", "§0",
						"§e§lClique direito §epara ver", "§e§ltodos §eos §e§lservidores§e."),
				npclocation, type);
		connectBungeeMessage = connectMessage;
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!getCharacterNPC().isSpawned()) {
					getCharacterNPC().spawn(LobbyUtils.getLocationUtils().getCenter(npclocation, true));
					return;
				}
				getCharacterNPC().getEntity().setMetadata("GM_CONNECT",
						new FixedMetadataValue(yLobbyPlugin.getyLobby(), connectMessage.getMessageArgs()));
				getCharacterNPC().getEntity().setMetadata("GM_TYPE",
						new FixedMetadataValue(yLobbyPlugin.getyLobby(), "MATCH"));
				getCharacterNPC().getEntity().setMetadata("GM_NAME",
						new FixedMetadataValue(yLobbyPlugin.getyLobby(), servername));
			}
		}.runTask(yLobbyPlugin.getyLobby());
	}

	@Override
	public abstract int getOnlinePlayers();

	public abstract void onRightClick(Player p);

	public void onLeftClick(Player p) {
		p.sendPluginMessage(yLobbyPlugin.getyLobby(), "BungeeCord", connectBungeeMessage.getDataOutput().toByteArray());
	}

}
