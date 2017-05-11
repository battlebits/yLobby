package br.com.battlebits.ylobby.manager;

import java.util.HashMap;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.selector.match.MatchSelector;

public class MatchSelectorManager {

	private HashMap<ServerType, MatchSelector> matchSelectors;

	public MatchSelectorManager() {
		matchSelectors = new HashMap<>();
		loadSelectors();
	}

	public void loadSelectors() {
		addMacthSelector(new MatchSelector(ServerType.HUNGERGAMES, "§n§%battle-hg-servers%§",
				new BungeeMessage("HungerGames")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(ServerType.HUNGERGAMES).getTotalNumber();
			}
		});

		addMacthSelector(new MatchSelector(ServerType.CUSTOMHG, "§n§%custom-hg-servers%§",
				new BungeeMessage("CustomHungergames")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(ServerType.CUSTOMHG).getTotalNumber();
			}
		});

		addMacthSelector(new MatchSelector(ServerType.DOUBLEKITHG, "§n§%doublekit-hg-servers%§",
				new BungeeMessage("DoubleKitHungergames")) {
			@Override
			public int getMatchsOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(ServerType.DOUBLEKITHG).getTotalNumber();
			}
		});
//		addMacthSelector(
//				new MatchSelector(ServerType.FAIRPLAY, "§nServidores do FairPlayHG", new BungeeMessage("Fairplayhg")) {
//					@Override
//					public int getMatchsOnlinePlayers() {
//						return yLobbyPlugin.getInstance().getServerManager().getBalancer(ServerType.FAIRPLAY)
//								.getTotalNumber();
//					}
//				});
	}

	public boolean isMatchSelector(ServerType type) {
		return matchSelectors.containsKey(type);
	}

	public MatchSelector getMatchSelector(ServerType type) {
		return matchSelectors.get(type);
	}

	public void addMacthSelector(MatchSelector selector) {
		matchSelectors.put(selector.getServerType(), selector);
	}

	public void stop() {
		for (MatchSelector selector : matchSelectors.values()) {
			selector.stop();
		}
		matchSelectors.clear();
	}

}
