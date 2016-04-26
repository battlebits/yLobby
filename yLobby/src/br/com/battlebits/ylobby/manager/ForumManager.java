package br.com.battlebits.ylobby.manager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.iw4.json.HTTP;
import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;

public class ForumManager {

	public String[] characters;
	public String[] letters;
	private SecureRandom random;
	private String connecingMsg;
	private String processingMsg;
	private HashMap<UUID, String> forumID;
	private boolean processing;

	public ForumManager() {
		forumID = new HashMap<>();
		letters = new String[] { "A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "i", "I", "j", "J", "k", "K", "L",
				"l", "M", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S", "s", "t", "T", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z",
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		connecingMsg = yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Conectando ao §9§lforum§7!");
		connecingMsg = yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Ja existe um §9§lprocesso§7 sendo executado!");
		random = new SecureRandom();
		processing = false;
	}

	public void loadForumID(UUID id) throws Exception {
//		Statement stmt = yLobbyPlugin.getyLobby().getMySQLConnection().getConnection().createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT forumid FROM `uuid_forumid` WHERE `uuid` = '" + id.toString().replace("-", "") + "'");
//		if (rs.next()) {
//			forumID.put(id, rs.getString("forumid"));
//		}
	}

	public void handleQuit(UUID id) {
		forumID.remove(id);
	}

	public void updateProfile(Player p) {
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendMessage("§0");
				if (!processing) {
					processing = true;
					p.sendMessage(connecingMsg);
					p.sendMessage("§0");
					try {
						Group group = Main.getPlugin().getPermissionManager().getPlayerGroup(p.getUniqueId());
						HttpURLConnection conn = (HttpURLConnection) new URL(
								"http://forum.battlebits.com.br/sharingan/forumApi.php?" + "key=server@secret&" + "module=update&" + "username="
										+ getForumUUID(p.getUniqueId()) + "&name=" + p.getName() + "&group=" + group.name().toUpperCase())
												.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(15000);
						conn.setReadTimeout(15000);
						conn.setInstanceFollowRedirects(false);
						conn.setAllowUserInteraction(false);
						InputStream stream = conn.getInputStream();
						InputStreamReader streamReader = new InputStreamReader(stream);
						BufferedReader reader = new BufferedReader(streamReader);
						String msg = reader.readLine().split("#")[1];
						p.sendMessage("§0");
						if (msg.contains("@")) {
							for (String s : msg.split("@")) {
								p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage(s));
							}
						} else {
							p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage(msg));
						}
					} catch (Exception e) {
						e.printStackTrace();
						p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Um §c§lerro §7ocorreu!"));
					}
					processing = false;
				} else {
					p.sendMessage(processingMsg);
				}
				p.sendMessage("§0");
			}
		}.runTaskAsynchronously(yLobbyPlugin.getyLobby());
	}

	public void createProfile(Player p, String email) {
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendMessage("§0");
				if (!processing) {
					processing = true;
					p.sendMessage(connecingMsg);
					p.sendMessage("§0");
					try {
						Group group = Main.getPlugin().getPermissionManager().getPlayerGroup(p.getUniqueId());
						HttpURLConnection conn = (HttpURLConnection) new URL(
								"http://forum.battlebits.com.br/sharingan/forumApi.php?" + "key=server@secret&" + "module=register&" + "email=" + email
										+ "&uuid=" + p.getUniqueId().toString().replace("-", "") + "&pass=" + generateRandom(8) + "&ip="
										+ p.getAddress().getHostName() + "&name=" + p.getName() + "&group=" + group.name().toUpperCase())
												.openConnection();
						p.sendMessage(conn.getURL().toString());
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(15000);
						conn.setReadTimeout(15000);
						conn.setInstanceFollowRedirects(false);
						conn.setAllowUserInteraction(false);
						InputStream stream = conn.getInputStream();
						InputStreamReader streamReader = new InputStreamReader(stream);
						BufferedReader reader = new BufferedReader(streamReader);
						String msg = reader.readLine();
						p.sendMessage(msg);
						if (!msg.split("#")[0].isEmpty()) {
//							yLobbyPlugin.getyLobby().getMySQLConnection().sendUpdate("INSERT INTO `uuid_forumid` (`uuid`, `forumid`) VALUES ('"
//									+ p.getUniqueId().toString().replace("-", "") + "', '" + msg.split("#")[0] + "');");
							forumID.put(p.getUniqueId(), msg.split("#")[0]);
						}
						p.sendMessage("§0");
						if (msg.contains("@")) {
							for (String s : msg.split("#")[1].split("@")) {
								p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage(s));
							}
						} else {
							p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage(msg.split("#")[1]));
						}
					} catch (Exception e) {
						e.printStackTrace();
						p.sendMessage(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Um §c§lerro §7ocorreu!"));
					}
					processing = false;
				} else {
					p.sendMessage(processingMsg);
				}
				p.sendMessage("§0");
			}
		}.runTaskAsynchronously(yLobbyPlugin.getyLobby());
	}

	public boolean hasForumProfile(UUID id) {
		return forumID.containsKey(id);
	}

	public String getForumUUID(UUID id) {
		return forumID.get(id);
	}

	public String generateRandom(int lenght) {
		String r = "";
		while (r.length() < lenght) {
			r += letters[random.nextInt(letters.length - 1)];
		}
		return r;
	}

}
