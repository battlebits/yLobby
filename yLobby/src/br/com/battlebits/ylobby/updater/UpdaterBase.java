package br.com.battlebits.ylobby.updater;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.LobbyMain;

public abstract class UpdaterBase {

	private BukkitRunnable updaterRunnable;
	private long delay;
	private boolean sync;

	public UpdaterBase(Long delay, boolean sync) {
		updaterRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				update();
			}
		};
		this.delay = delay;
		this.sync = sync;
	}

	public void start() {
		if (sync) {
			updaterRunnable.runTaskTimer(LobbyMain.getInstance(), delay, delay);
		} else {
			updaterRunnable.runTaskTimerAsynchronously(LobbyMain.getInstance(), delay, delay);
		}
	}

	public void stop() {
		try {
			if (updaterRunnable != null) {
				updaterRunnable.cancel();
			}
		} catch (Exception e) {
		}
	}

	public abstract void update();

}
