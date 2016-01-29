package br.com.battlebits.ylobby.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;

public class FlyCommand implements CommandExecutor {

	private ArrayList<String> onlyForLights;
	private ArrayList<String> flyEnable;
	private ArrayList<String> flyDisable;

	public FlyCommand() {
		onlyForLights = new ArrayList<>();
		onlyForLights.add("§0");
		onlyForLights.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7O modo §e§lvoar §7so pode ser usado por um"));
		onlyForLights.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7jogador com o grupo §a§lLIGHT §7ou superior!"));
		onlyForLights.add("§0");
		flyEnable = new ArrayList<>();
		flyEnable.add("§0");
		flyEnable.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Seu §a§lfly §7foi §a§lativado§7!"));
		flyEnable.add("§0");
		flyDisable = new ArrayList<>();
		flyDisable.add("§0");
		flyDisable.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Seu §e§lfly §7foi §e§ldesativado§7!"));
		flyDisable.add("§0");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Main.getPlugin().getPermissionManager().hasGroupPermission(p, Group.LIGHT)) {
				if (p.getAllowFlight()) {
					p.setAllowFlight(false);
					p.setFlying(false);
					for (String msg : flyDisable) {
						p.sendMessage(msg);
					}
				} else {
					p.setAllowFlight(true);
					p.setFlying(true);
					for (String msg : flyEnable) {
						p.sendMessage(msg);
					}
				}
			} else {
				for (String msg : onlyForLights) {
					p.sendMessage(msg);
				}
			}
		} else {
			sender.sendMessage("§c§lComando apenas para jogadores.");
		}
		return false;
	}

}
