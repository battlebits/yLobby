package br.com.battlebits.ylobby.gamemode;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.yutils.character.CharacterType;

public abstract class GameModeMulti extends GameModeBase {

	private BungeeMessage connectBungeeMessage;

	public GameModeMulti(final String servername, String serverdescription, Material iconmaterial, Location npclocation, CharacterType characterType, final BungeeMessage connectMessage) {
		super(servername, serverdescription, iconmaterial, Arrays.asList("§b§lClique esquerdo §bpara ", "§b§lconectar §ba um §b§lservidor§b.", "§0", "§e§lClique direito §epara ver", "§e§ltodos §eos §e§lservidores§e."), npclocation, characterType);
		connectBungeeMessage = connectMessage;
		new BukkitRunnable() {
			@Override
			public void run() {
				getCharacterNPC().getBukkitEntity().setMetadata("GM_CONNECT", new FixedMetadataValue(yLobbyPlugin.getyLobby(), connectMessage.getMessageArgs()));
				getCharacterNPC().getBukkitEntity().setMetadata("GM_TYPE", new FixedMetadataValue(yLobbyPlugin.getyLobby(), "MULTI"));
				getCharacterNPC().getBukkitEntity().setMetadata("GM_NAME", new FixedMetadataValue(yLobbyPlugin.getyLobby(), servername));
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
