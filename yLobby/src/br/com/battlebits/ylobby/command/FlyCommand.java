package br.com.battlebits.ylobby.command;

import org.bukkit.entity.Player;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.core.command.CommandClass;
import br.com.battlebits.commons.core.command.CommandFramework.Command;
import br.com.battlebits.commons.core.permission.Group;

public class FlyCommand implements CommandClass {

	@Command(name = "fly")
	public void onCommand(BukkitCommandArgs cmdArgs) {
		if (cmdArgs.isPlayer()) {
			Player p = cmdArgs.getPlayer();
			if (BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId()).hasGroupPermission(Group.LIGHT)) {
				if (p.getAllowFlight()) {
					p.setAllowFlight(false);
					p.setFlying(false);
					p.sendMessage("§%command-fly-disabled%§");
				} else {
					p.setAllowFlight(true);
					p.setFlying(true);
					p.sendMessage("§%command-fly-enabled%§");
				}
			} else {
				p.sendMessage("§%command-fly-vip%§");
			}
		} else {
			cmdArgs.getSender().sendMessage("§c§lComando apenas para jogadores.");
		}
	}

}
