package br.com.battlebits.ylobby.manager;

import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.selector.match.MatchSelector;

public class MatchSelectorManager {

	private HashMap<String, MatchSelector> matchSelectorNames;
	private MatchSelector hardcoreGamesSelector;
	private MatchSelector fairPlaySelector;
	private BukkitRunnable updaterRunnable;

	public MatchSelectorManager() {
		matchSelectorNames = new HashMap<>();
	}

	public void loadSelectors() {
		hardcoreGamesSelector = new MatchSelector(yLobbyPlugin.getyLobby().getBungeeManager().getHGServers(), 100, "     §nServidores do Battle-HG",
				new BungeeMessage("HungerGames")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getPlayerCountManager().getHgOnlinePlayers();
			}
		};
		addMacthSelector(hardcoreGamesSelector);
		fairPlaySelector = new MatchSelector(yLobbyPlugin.getyLobby().getBungeeManager().getFairPlayServers(), 100, "    §nServidores do FairPlayHG",
				new BungeeMessage("Fairplayhg")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getPlayerCountManager().getFpOnlinePlayers();
			}
		};
		addMacthSelector(fairPlaySelector);
		updaterRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				for (MatchSelector selector : matchSelectorNames.values()) {
					selector.update();
				}
			}
		};
		updaterRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 15l, 30L);
	}

	public boolean isMatchSelector(String title) {
		return matchSelectorNames.containsKey(title);
	}

	public MatchSelector getMatchSelector(String title) {
		return matchSelectorNames.get(title);
	}

	public void addMacthSelector(MatchSelector selector) {
		matchSelectorNames.put(selector.getInventoryTitle(), selector);
	}

	public MatchSelector getHardcoreGamesSelector() {
		return hardcoreGamesSelector;
	}

	public MatchSelector getFairPlaySelector() {
		return fairPlaySelector;
	}

	public void stop() {
		if (updaterRunnable != null) {
			updaterRunnable.cancel();
		}
		for (MatchSelector selector : matchSelectorNames.values()) {
			selector.stop();
		}
		matchSelectorNames.clear();
	}

}
