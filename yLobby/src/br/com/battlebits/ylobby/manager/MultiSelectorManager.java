package br.com.battlebits.ylobby.manager;

import java.util.HashMap;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.selector.multi.MultiSelector;

public class MultiSelectorManager {

	private HashMap<ServerType, MultiSelector> multiSelectors;

	public MultiSelectorManager() {
		multiSelectors = new HashMap<>();
		loadSelectors();
	}

	public void loadSelectors() {
		addMultiSelector(
				new MultiSelector(ServerType.PVP_FULLIRON, "§n§%fulliron-servers%§", new BungeeMessage("PVPFulliron")) {
					@Override
					public int getMultiOnlinePlayers() {
						return LobbyMain.getInstance().getServerManager().getBalancer(ServerType.PVP_FULLIRON)
								.getTotalNumber();
					}
				});
		addMultiSelector(new MultiSelector(ServerType.PVP_SIMULATOR, "§n§%simulator-servers%§",
				new BungeeMessage("PVPSimulator")) {
			@Override
			public int getMultiOnlinePlayers() {
				return LobbyMain.getInstance().getServerManager().getBalancer(ServerType.PVP_SIMULATOR)
						.getTotalNumber();
			}
		});
	}

	public boolean isMultiSelector(ServerType type) {
		return multiSelectors.containsKey(type);
	}

	public MultiSelector getMultiSelector(ServerType type) {
		return multiSelectors.get(type);
	}

	public void addMultiSelector(MultiSelector selector) {
		multiSelectors.put(selector.getServerType(), selector);
	}

	public void stop() {
		for (MultiSelector selector : multiSelectors.values()) {
			selector.stop();
		}
		multiSelectors.clear();
	}

}
