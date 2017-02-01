package br.com.battlebits.ylobby.listener;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.battlebits.commons.api.actionbar.ActionBarAPI;
import br.com.battlebits.commons.api.title.TitleAPI;
import br.com.battlebits.ylobby.updater.TabAndHeaderUpdater;

public class BountifulListener implements Listener {

	private ArrayList<String> playerJoinMessage;

	public BountifulListener() {
		playerJoinMessage = new ArrayList<>();
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§6§lBATTLE§F§LBITS");
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§%welcome-lobby-message%§");
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§6§lwww.battlebits.com.br");
		playerJoinMessage.add("§0");
		playerJoinMessage.add("§0");
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoinListener(final PlayerJoinEvent e) {
		for (String msg : playerJoinMessage) {
			e.getPlayer().sendMessage(msg);
		}
		TitleAPI.setTitle(e.getPlayer(), "§6§lBattle§f§lBits", "§e§lMinecraft Network");
		ActionBarAPI.send(e.getPlayer(), "§%welcome-lobby-title%§");
		TabAndHeaderUpdater.send(e.getPlayer());
	}

}
