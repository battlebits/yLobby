package br.com.battlebits.ylobby.listener;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.ProtocolInjector.PacketTitle;
import org.spigotmc.ProtocolInjector.PacketTitle.Action;

import br.com.battlebits.ylobby.yLobbyPlugin;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;

public class BountifulListener implements Listener {

	private ArrayList<String> playerJoinMessage;
	private PacketTitle joinTitle;
	private PacketTitle joinSubTitle;
	private PacketPlayOutChat joinActionTitle;

	public BountifulListener() {
		playerJoinMessage = new ArrayList<>();
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§0");
		playerJoinMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§6§lBATTLE§F§LBITS"));
		playerJoinMessage.add("§0");
		playerJoinMessage.add(
				yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Seja bem-vindo ao nosso §e§lLobby§7! Aqui você pode se"));
		playerJoinMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
				.centerChatMessage("§7conectar aos todos os nossos §e§lmodos de jogo§7 também"));
		playerJoinMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
				.centerChatMessage("§7pode se §e§ldivertir§7 enquanto espera para §7jogar! Para obter"));
		playerJoinMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
				.centerChatMessage("§7maiores informações sobre o servidor acesse nosso §e§lsite§7!"));
		playerJoinMessage.add("§0");
		playerJoinMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§6§lwww.battlebits.com.br"));
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§0");
		joinTitle = new PacketTitle(Action.TITLE, ChatSerializer.a("{\"text\": \"§6§lBattle§f§lBits\"}"));
		joinSubTitle = new PacketTitle(Action.SUBTITLE, ChatSerializer.a("{\"text\": \"§e§lMinecraft PvP Network\"}"));
		joinActionTitle = new PacketPlayOutChat(ChatSerializer.a("{\"text\": \"§bSeja bem-vindo ao nosso §3§lLobby§b!\"}"), 2);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoinListener(final PlayerJoinEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (String msg : playerJoinMessage) {
					e.getPlayer().sendMessage(msg);
				}
				if (yLobbyPlugin.getyLobby().getzUtils().getPlayerUtils().isPlayerOn18(e.getPlayer())) {
					yLobbyPlugin.getyLobby().getTabHeaderAndFooterManager().send(e.getPlayer());
					yLobbyPlugin.getyLobby().getzUtils().getPlayerUtils().sendPacket(e.getPlayer(), joinTitle);
					yLobbyPlugin.getyLobby().getzUtils().getPlayerUtils().sendPacket(e.getPlayer(), joinSubTitle);
					yLobbyPlugin.getyLobby().getzUtils().getPlayerUtils().sendPacket(e.getPlayer(), joinActionTitle);
				}
			}
		}.runTaskAsynchronously(yLobbyPlugin.getyLobby());
	}

}
