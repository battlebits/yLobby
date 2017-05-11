package br.com.battlebits.ylobby.command;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.core.command.CommandClass;
import br.com.battlebits.commons.core.command.CommandFramework.Command;
import br.com.battlebits.ylobby.LobbyMain;

public class SpawnCommand implements CommandClass {

	private ArrayList<String> message;

	public SpawnCommand() {
		message = new ArrayList<>();
		message.add("§0");
		message.add("§%command-spawn-teleported%§");
		message.add("§0");
	}

	@Command(name = "spawn")
	public boolean onCommand(BukkitCommandArgs args) {
		if (args.isPlayer()) {
			Player p = args.getPlayer();
			p.teleport(LobbyMain.getInstance().getLocationManager().getSpawnLocation());
			for (String msg : message) {
				p.sendMessage(msg);
			}
		} else {
			args.getSender().sendMessage("§c§lComando apenas para jogadores.");
		}
		return true;
	}

}
