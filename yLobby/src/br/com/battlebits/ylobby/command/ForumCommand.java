package br.com.battlebits.ylobby.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class ForumCommand implements CommandExecutor {

	private TextComponent header;
	private TextComponent create;
	private TextComponent update;
	private TextComponent needToCreate;
	private TextComponent haveProfile;
	private TextComponent needEmail;
	private TextComponent validEmail;

	public ForumCommand() {
		header = new TextComponent(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§9§l» §7Comandos para o forum §9§l«"));
		create = new TextComponent(
				yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7/forum §9§lcriar §7<email> §9§l» §7Cria uma conta"));
		create.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new TextComponent[] { new TextComponent("§7Clique aqui para digitar o comando") }));
		create.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/forum criar <email>"));
		update = new TextComponent(
				yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7/forum §9§latualizar » §7Atualizar sua conta"));
		update.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new TextComponent[] { new TextComponent("§7Clique aqui para digitar o comando") }));
		update.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/forum atualizar"));
		needToCreate = new TextComponent(
				yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Voce precisa §9§lcriar §7sua conta §9§lantes§7!"));
		haveProfile = new TextComponent(
				yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Voce ja §9§lpossui §7sua §9§lconta§7!"));
		needEmail = new TextComponent(
				yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Voce §9§lprecisa §7expecificar o §9§lemail§7!"));
		validEmail = new TextComponent(
				yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Voce §9§lprecisa §de um §9§lemail§7 valido!"));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Main.getPlugin().getPermissionManager().getPlayerGroup(p) == Group.ADMIN) {
				if (args.length == 0) {
					p.sendMessage("§0");
					p.spigot().sendMessage(header);
					if (!yLobbyPlugin.getyLobby().getForumManager().hasForumProfile(p.getUniqueId())) {
						p.spigot().sendMessage(create);
					} else {
						p.spigot().sendMessage(update);
					}
					p.sendMessage("§0");
				} else if (args[0].equalsIgnoreCase("atualizar")) {
					if (yLobbyPlugin.getyLobby().getForumManager().hasForumProfile(p.getUniqueId())) {
						yLobbyPlugin.getyLobby().getForumManager().updateProfile(p);
					} else {
						p.sendMessage("§0");
						p.spigot().sendMessage(needToCreate);
						p.sendMessage("§0");
					}
				} else if (args[0].equalsIgnoreCase("criar")) {
					if (!yLobbyPlugin.getyLobby().getForumManager().hasForumProfile(p.getUniqueId())) {
						if (args.length > 1) {
							String email = args[1];
							if (email.contains("@")) {
								if (email.toLowerCase().contains(".com") || email.toLowerCase().contains(".net")) {
									if (email.length() >= 12) {
										if (email.split("@")[0].length() >= 4) {
											yLobbyPlugin.getyLobby().getForumManager().createProfile(p, email);
											return true;
										}
									}
								}
							}
							p.sendMessage("§0");
							p.spigot().sendMessage(validEmail);
							p.sendMessage("§0");
						} else {
							p.sendMessage("§0");
							p.spigot().sendMessage(needEmail);
							p.sendMessage("§0");
						}
					} else {
						p.sendMessage("§0");
						p.spigot().sendMessage(haveProfile);
						p.sendMessage("§0");
					}
				} else {
					p.sendMessage("§0");
					p.spigot().sendMessage(header);
					if (!yLobbyPlugin.getyLobby().getForumManager().hasForumProfile(p.getUniqueId())) {
						p.spigot().sendMessage(create);
					} else {
						p.spigot().sendMessage(update);
					}
					p.sendMessage("§0");
				}
			}
		} else {
			sender.sendMessage("§c§lComando apenas para jogadores.");
		}
		return true;
	}
}
