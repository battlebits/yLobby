package br.com.battlebits.ylobby.command;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.api.item.ActionItemStack;
import br.com.battlebits.commons.api.item.ActionItemStack.InteractHandler;
import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.core.command.CommandClass;
import br.com.battlebits.commons.core.command.CommandFramework.Command;
import br.com.battlebits.commons.core.permission.Group;

public class FlyCommand implements CommandClass {

	private ArrayList<String> onlyForLights;
	private ArrayList<String> flyEnable;
	private ArrayList<String> flyDisable;

	public FlyCommand() {
		onlyForLights = new ArrayList<>();
		onlyForLights.add("§0");
		onlyForLights.add("§%command-fly-vip%§");
		onlyForLights.add("§0");
		flyEnable = new ArrayList<>();
		flyEnable.add("§0");
		flyEnable.add("§%command-fly-enabled%§");
		flyEnable.add("§0");
		flyDisable = new ArrayList<>();
		flyDisable.add("§0");
		flyDisable.add("§%command-fly-disabled%§");
		flyDisable.add("§0");
	}

	@Command(name = "fly")
	public void onCommand(BukkitCommandArgs cmdArgs) {
		if (cmdArgs.isPlayer()) {
			Player p = cmdArgs.getPlayer();
			if (BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId()).hasGroupPermission(Group.LIGHT)) {
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
			cmdArgs.getSender().sendMessage("§c§lComando apenas para jogadores.");
		}
	}

	@Command(name = "updatescoreboard")
	public void scoreboard(BukkitCommandArgs args) {
		if (args.isPlayer()) {
			Player p = args.getPlayer();
			Scoreboard board = p.getScoreboard();
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			p.setScoreboard(board);
		}
	}

	@Command(name = "giveitem")
	public void giveItem(BukkitCommandArgs args) {
		if (args.isPlayer()) {
			Player p = args.getPlayer();
			p.getInventory().addItem(new ActionItemStack(new ItemStack(Material.STONE), new InteractHandler() {

				@Override
				public boolean onInteract(Player player, ItemStack item, Action action) {
					player.sendMessage("Interact!");
					return false;
				}
			}).getItemStack());
		}
	}

}
