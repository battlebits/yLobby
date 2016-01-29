package br.com.battlebits.ylobby.command;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TellCommand implements CommandExecutor {

	private ArrayList<String> notFound;
	private ArrayList<String> tellIncorrectUse;
	private ArrayList<String> rIncorrectUse;
	private ArrayList<String> disableTarget;
	private ArrayList<String> disablePlayer;
	private ArrayList<String> rNotFound;
	private ArrayList<String> isYou;

	public TellCommand() {
		notFound = new ArrayList<>();
		notFound.add("§0");
		notFound.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7O §e§ljogador §7não foi §e§lencontrado§7!"));
		notFound.add("§0");
		tellIncorrectUse = new ArrayList<>();
		tellIncorrectUse.add("§0");
		tellIncorrectUse.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
				.centerChatMessage("§7Você §e§lprecisa§7 especificar o §e§ljogador§7 e a §e§lmensagem§7!"));
		tellIncorrectUse.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Use: §e/tell <jogador> <mensagem>"));
		tellIncorrectUse.add("§0");
		disableTarget = new ArrayList<>();
		disableTarget.add("§0");
		disableTarget.add(
				yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Esse §c§ljogador §7está com o §c§ltell desativado§7!"));
		disableTarget.add("§0");
		disablePlayer = new ArrayList<>();
		disablePlayer.add("§0");
		disablePlayer.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7O seu §c§ltell §7esta §c§ldesativado§7!"));
		disablePlayer.add("§0");
		isYou = new ArrayList<>();
		isYou.add("§0");
		isYou.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils()
				.centerChatMessage("§7Voce §c§lnao §7pode enviar §c§lmensagens§7 para §c§lvoce §7mesmo!"));
		isYou.add("§0");
		rIncorrectUse = new ArrayList<>();
		rIncorrectUse.add("§0");
		rIncorrectUse
				.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Você §e§lprecisa§7 especificar a §e§lmensagem§7!"));
		rIncorrectUse.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Use: §e/r <mensagem>"));
		rIncorrectUse.add("§0");
		rNotFound = new ArrayList<>();
		rNotFound.add("§0");
		rNotFound.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Voce §e§lnao §7tem ninguem para §e§lresponder§7."));
		rNotFound.add("§0");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("tell")) {
				if (args.length > 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if (t != null && t.isOnline()) {
						if (!t.getName().equalsIgnoreCase(p.getName())) {
							if (yLobbyPlugin.getyLobby().getChatManager().isTellEnabled(p.getUniqueId())) {
								if (yLobbyPlugin.getyLobby().getChatManager().isTellEnabled(t.getUniqueId())) {
									String msg = "";
									for (int i = 1; i < args.length; i += 1) {
										if (!msg.isEmpty()) {
											msg = msg + " ";
										}
										msg = msg + args[i];
									}
									TextComponent toPlayer = new TextComponent("§7[eu -> " + t.getDisplayName() + "§7] §r" + msg);
									toPlayer.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/tell " + t.getName() + " "));
									toPlayer.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BaseComponent[] {
											new TextComponent("§7Clique para enviar outra mensagem para o jogador §b" + t.getName()) }));
									p.spigot().sendMessage(toPlayer);
									TextComponent toTarget = new TextComponent("§7[" + p.getDisplayName() + " §7-> eu] §r" + msg);
									toTarget.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/responder "));
									toTarget.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT,
											new BaseComponent[] { new TextComponent("§7Clique para responder o jogador §b" + p.getName()) }));
									t.spigot().sendMessage(toTarget);
									yLobbyPlugin.getyLobby().getChatManager().lastTell.put(t.getUniqueId(), p.getUniqueId());
									yLobbyPlugin.getyLobby().getChatManager().lastTell.put(p.getUniqueId(), t.getUniqueId());
								} else {
									for (String msg : disableTarget) {
										p.sendMessage(msg);
									}
								}
							} else {
								for (String msg : disablePlayer) {
									p.sendMessage(msg);
								}
							}

						} else {
							for (String msg : isYou) {
								p.sendMessage(msg);
							}
						}
					} else {
						for (String msg : notFound) {
							p.sendMessage(msg);
						}
					}
				} else {
					for (String msg : tellIncorrectUse) {
						p.sendMessage(msg);
					}
				}
			} else if (cmd.getName().equalsIgnoreCase("r")) {
				if (yLobbyPlugin.getyLobby().getChatManager().lastTell.containsKey(p.getUniqueId())) {
					if (args.length > 0) {
						Player t = Bukkit.getPlayer(yLobbyPlugin.getyLobby().getChatManager().lastTell.get(p.getUniqueId()));
						if (t != null && t.isOnline()) {
							if (!t.getName().equalsIgnoreCase(p.getName())) {
								if (yLobbyPlugin.getyLobby().getChatManager().isTellEnabled(p.getUniqueId())) {
									if (yLobbyPlugin.getyLobby().getChatManager().isTellEnabled(t.getUniqueId())) {
										String msg = "";
										for (int i = 0; i < args.length; i += 1) {
											if (!msg.isEmpty()) {
												msg = msg + " ";
											}
											msg = msg + args[i];
										}
										TextComponent toPlayer = new TextComponent("§7[eu -> " + t.getDisplayName() + "§7] §r" + msg);
										toPlayer.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/tell " + t.getName() + " "));
										toPlayer.setHoverEvent(
												new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BaseComponent[] {
														new TextComponent("§7Clique para enviar outra mensagem para o jogador §b" + t.getName()) }));
										p.spigot().sendMessage(toPlayer);
										TextComponent toTarget = new TextComponent("§7[" + p.getDisplayName() + " §7-> eu] §r" + msg);
										toTarget.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/responder "));
										toTarget.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT,
												new BaseComponent[] { new TextComponent("§7Clique para responder o jogador §b" + p.getName()) }));
										t.spigot().sendMessage(toTarget);
										yLobbyPlugin.getyLobby().getChatManager().lastTell.put(t.getUniqueId(), p.getUniqueId());
										yLobbyPlugin.getyLobby().getChatManager().lastTell.put(p.getUniqueId(), t.getUniqueId());
									} else {
										for (String msg : disableTarget) {
											p.sendMessage(msg);
										}
									}
								} else {
									for (String msg : disablePlayer) {
										p.sendMessage(msg);
									}
								}

							} else {
								for (String msg : isYou) {
									p.sendMessage(msg);
								}
							}
						} else {
							for (String msg : notFound) {
								p.sendMessage(msg);
							}
						}
					} else {
						for (String msg : rIncorrectUse) {
							p.sendMessage(msg);
						}
					}
				} else {
					for (String msg : rNotFound) {
						p.sendMessage(msg);
					}
				}
			}
		} else {
			sender.sendMessage("§c§lComando apenas para jogadores.");
		}
		return false;
	}

}
