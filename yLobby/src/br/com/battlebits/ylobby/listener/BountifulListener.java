package br.com.battlebits.ylobby.listener;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.battlebits.commons.api.actionbar.ActionBarAPI;
import br.com.battlebits.commons.api.title.TitleAPI;
import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.updater.TabAndHeaderUpdater;

public class BountifulListener implements Listener {

	private ArrayList<String> playerJoinMessage;

	public BountifulListener() {
		playerJoinMessage = new ArrayList<>();
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§0");
		playerJoinMessage.add(LobbyUtils.getMessageUtils().centerChatMessage("§6§lBATTLE§F§LBITS"));
		playerJoinMessage.add("§0");
		playerJoinMessage.add(LobbyUtils.getMessageUtils()
				.centerChatMessage("§7Seja bem-vindo ao nosso §e§lLobby§7! Aqui você pode se"));
		playerJoinMessage.add(LobbyUtils.getMessageUtils()
				.centerChatMessage("§7conectar aos todos os nossos §e§lmodos de jogo§7 também"));
		playerJoinMessage.add(LobbyUtils.getMessageUtils()
				.centerChatMessage("§7pode se §e§ldivertir§7 enquanto espera para §7jogar! Para obter"));
		playerJoinMessage.add(LobbyUtils.getMessageUtils()
				.centerChatMessage("§7maiores informações sobre o servidor acesse nosso §e§lsite§7!"));
		playerJoinMessage.add("§0");
		playerJoinMessage.add(LobbyUtils.getMessageUtils().centerChatMessage("§6§lwww.battlebits.com.br"));
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§0");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoinListener(final PlayerJoinEvent e) {
		for (String msg : playerJoinMessage) {
			e.getPlayer().sendMessage(msg);
		}
		TitleAPI.setTitle(e.getPlayer(), "§6§lBattle§f§lBits", "§e§lMinecraft PvP Network");
		ActionBarAPI.send(e.getPlayer(), "§bSeja bem-vindo ao nosso §3§lLobby§b!");
		TabAndHeaderUpdater.send(e.getPlayer());
	}

}
