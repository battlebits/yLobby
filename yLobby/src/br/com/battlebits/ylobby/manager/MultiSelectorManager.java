package br.com.battlebits.ylobby.manager;

import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.selector.multi.MultiSelector;

public class MultiSelectorManager {

	private HashMap<String, MultiSelector> multiSelectorNames;
	private MultiSelector pvpFullironSelector;
	private MultiSelector pvpSimultorSelector;
	private BukkitRunnable updaterRunnable;

	public MultiSelectorManager() {
		multiSelectorNames = new HashMap<>();
	}

	public void loadSelectors() {
		pvpFullironSelector = new MultiSelector(ServerType.PVP_FULLIRON, "§nBattlecraft Fulliron",
				new BungeeMessage("PVPFulliron")) {
			@Override
			public int getMultiOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.PVP_FULLIRON)
						.getTotalNumber();
			}
		};
		addMultiSelector(pvpFullironSelector);

		pvpSimultorSelector = new MultiSelector(ServerType.PVP_SIMULATOR, "§nBattlecraft Simulator",
				new BungeeMessage("PVPSimulator")) {
			@Override
			public int getMultiOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getServerManager().getBalancer(ServerType.PVP_SIMULATOR)
						.getTotalNumber();
			}
		};
		addMultiSelector(pvpSimultorSelector);

		updaterRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				for (MultiSelector selector : multiSelectorNames.values()) {
					selector.update();
				}
			}
		};
		updaterRunnable.runTaskTimer(yLobbyPlugin.getyLobby(), 15l, 30L);
	}

	public boolean isMultiSelector(String title) {
		return multiSelectorNames.containsKey(title);
	}

	public MultiSelector getMultiSelector(String title) {
		return multiSelectorNames.get(title);
	}

	public void addMultiSelector(MultiSelector selector) {
		multiSelectorNames.put(selector.getInventoryTitle(), selector);
	}

	public MultiSelector getPvPFullironSelector() {
		return pvpFullironSelector;
	}

	public MultiSelector getPvPSimulatorSelector() {
		return pvpSimultorSelector;
	}

	public void stop() {
		if (updaterRunnable != null) {
			updaterRunnable.cancel();
		}
		for (MultiSelector selector : multiSelectorNames.values()) {
			selector.stop();
		}
		multiSelectorNames.clear();
	}

}
