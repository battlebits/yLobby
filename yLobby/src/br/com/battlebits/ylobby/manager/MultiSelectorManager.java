package br.com.battlebits.ylobby.manager;

import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

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
		pvpFullironSelector = new MultiSelector(yLobbyPlugin.getyLobby().getBungeeManager().getFullIronServers(), 100, "     §nBattlecraft Fulliron", new BungeeMessage("PVPFulliron")) {
			@Override
			public int getMultiOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getPlayerCountManager().getFullIronOnlinePlayers();
			}
		};
		addMultiSelector(pvpFullironSelector);

		pvpSimultorSelector = new MultiSelector(yLobbyPlugin.getyLobby().getBungeeManager().getSimulatorServers(), 100, "     §nBattlecraft Simulator", new BungeeMessage("PVPSimulator")) {
			@Override
			public int getMultiOnlinePlayers() {
				return yLobbyPlugin.getyLobby().getPlayerCountManager().getSimulatorOnlinePlayers();
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
		updaterRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 15l, 30L);
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
