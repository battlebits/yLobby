package br.com.battlebits.ylobby.manager;

import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.selector.match.MatchSelector;

public class MatchSelectorManager {

	private HashMap<String, MatchSelector> matchSelectorNames;
	private MatchSelector hardcoreGamesSelector;
	private MatchSelector customHGSelector;
	private MatchSelector doubleKitHGSelector;
	private MatchSelector fairPlaySelector;
	private BukkitRunnable updaterRunnable;

	public MatchSelectorManager() {
		matchSelectorNames = new HashMap<>();
	}

	public void loadSelectors() {
		hardcoreGamesSelector = new MatchSelector(ServerType.HUNGERGAMES, "     §nServidores do Battle-HG",
				new BungeeMessage("HungerGames")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.HUNGERGAMES).getTotalNumber();
			}
		};
		addMacthSelector(hardcoreGamesSelector);

		customHGSelector = new MatchSelector(ServerType.CUSTOMHG, "     §nServidores do CustomHG",
				new BungeeMessage("CustomHungergames")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.CUSTOMHG).getTotalNumber();
			}
		};
		addMacthSelector(customHGSelector);

		doubleKitHGSelector = new MatchSelector(ServerType.DOUBLEKITHG, "    §nServidores do DoubleKit-HG",
				new BungeeMessage("DoubleKitHungergames")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.DOUBLEKITHG).getTotalNumber();
			}
		};
		addMacthSelector(doubleKitHGSelector);
		fairPlaySelector = new MatchSelector(ServerType.FAIRPLAY, "    §nServidores do FairPlayHG",
				new BungeeMessage("Fairplayhg")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.FAIRPLAY).getTotalNumber();
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
		updaterRunnable.runTaskTimer(yLobbyPlugin.getyLobby(), 15l, 30L);
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

	public MatchSelector getDoubleKitHgSelector() {
		return doubleKitHGSelector;
	}

	public MatchSelector getHardcoreGamesSelector() {
		return hardcoreGamesSelector;
	}

	public MatchSelector getCustomHGSelector() {
		return customHGSelector;
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
