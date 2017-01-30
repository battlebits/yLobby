package br.com.battlebits.ylobby.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.LobbyUtils;
import br.com.battlebits.ylobby.yLobbyPlugin;

public class GroupCommand implements CommandExecutor {

	private ArrayList<String> message;

	public GroupCommand() {
		message = new ArrayList<>();
		message.add("§0");
		message.add(LobbyUtils.getMessageUtils()
				.centerChatMessage("§7Abrindo o §9§lmenu §7de informacoes do seu §9§lgrupo atual§7!"));
		message.add("§0");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			for (String msg : message) {
				p.sendMessage(msg);
			}
			yLobbyPlugin.getyLobby().getProfileRanksInventory().open(p);
		} else {
			sender.sendMessage("§c§lComando apenas para jogadores.");
		}
		return true;
	}

}
