package br.com.battlebits.ylobby.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class SpawnCommand implements CommandExecutor {

	private ArrayList<String> message;

	public SpawnCommand() {
		message = new ArrayList<>();
		message.add("§0");
		message.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você foi teleportado para o §e§lSPAWN§7!"));
		message.add("§0");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.teleport(yLobbyPlugin.getyLobby().getLocationManager().getSpawnLocation());
			for (String msg : message) {
				p.sendMessage(msg);
			}
		} else {
			sender.sendMessage("§c§lComando apenas para jogadores.");
		}
		return true;
	}

}
