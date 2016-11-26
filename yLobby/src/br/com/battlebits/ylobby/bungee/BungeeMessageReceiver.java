package br.com.battlebits.ylobby.bungee;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import br.com.battlebits.ycommon.bungee.servers.HungerGamesServer.HungerGamesState;
import br.com.battlebits.ylobby.yLobbyPlugin;

public class BungeeMessageReceiver implements PluginMessageListener {

	private ArrayList<byte[]> messages;
	private BukkitRunnable updaterRunnable;

	public BungeeMessageReceiver() {
		messages = new ArrayList<>();
		updaterRunnable = new BukkitRunnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				for (byte[] message : (ArrayList<byte[]>) messages.clone()) {
					ByteArrayDataInput input = ByteStreams.newDataInput(message);
					String subchannel = input.readUTF();
					if (subchannel.equals("NetworkCount")) {
						yLobbyPlugin.getyLobby().getPlayerCountManager().setNetworkOnlinePlayers(input.readInt());
					} else if (subchannel.equals("HGCount")) {
						yLobbyPlugin.getyLobby().getPlayerCountManager().setHgOnlinePlayers(input.readInt());
					} else if (subchannel.equals("FPCount")) {
						yLobbyPlugin.getyLobby().getPlayerCountManager().setFpOnlinePlayers(input.readInt());
					} else if (subchannel.equals("SimulatorCount")) {
						yLobbyPlugin.getyLobby().getPlayerCountManager().setSimulatorOnlinePlayers(input.readInt());
					} else if (subchannel.equals("DoubleKitHGCount")) {
						yLobbyPlugin.getyLobby().getPlayerCountManager().setDoubleKitOnlinePlayers(input.readInt());
					} else if (subchannel.equals("FullironCount")) {
						yLobbyPlugin.getyLobby().getPlayerCountManager().setFullIronOnlinePlayers(input.readInt());
					} else if (subchannel.equals("GetServers")) {
						yLobbyPlugin.getyLobby().getBungeeManager().setServers(input.readUTF());
					} else if (subchannel.equals("AddServer")) {
						yLobbyPlugin.getyLobby().getBungeeManager().addServer(input.readUTF());
					} else if (subchannel.equals("RemoveServer")) {
						yLobbyPlugin.getyLobby().getBungeeManager().removeServer(input.readUTF());
					} else if (subchannel.equals("ServerInfo")) {
						String server = input.readUTF();
						int online = input.readInt();
						int max = input.readInt();
						int time = input.readInt();
						HungerGamesState state = HungerGamesState.valueOf(input.readUTF());
						if (yLobbyPlugin.getyLobby().getGameServerInfoManager().isRegistred(server)) {
							yLobbyPlugin.getyLobby().getGameServerInfoManager().get(server).setOnlinePlayers(online);
							yLobbyPlugin.getyLobby().getGameServerInfoManager().get(server).setMaxPlayers(max);
							yLobbyPlugin.getyLobby().getGameServerInfoManager().get(server).setTime(time);
							yLobbyPlugin.getyLobby().getGameServerInfoManager().get(server).setState(state);
						} else if (yLobbyPlugin.getyLobby().getServerInfoManager().isRegistred(server)) {
							yLobbyPlugin.getyLobby().getServerInfoManager().get(server).setOnlinePlayers(online);
							yLobbyPlugin.getyLobby().getServerInfoManager().get(server).setMaxPlayers(max);
						}
					}
					input = null;
					messages.remove(message);
				}

			}
		};
		updaterRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 10l, 10L);
	}

	public void stop() {
		if (updaterRunnable != null) {
			updaterRunnable.cancel();
		}
		messages.clear();
	}

	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] message) {
		if (channel.equals("BungeeCord")) {
			messages.add(message);
		} else {
			yLobbyPlugin.getyLobby().getLogger().warning("AVISO: O jogador " + p.getName() + " enviou uma PluginMessage sem ser pelo canal BungeeCord!");
		}

	}

}
