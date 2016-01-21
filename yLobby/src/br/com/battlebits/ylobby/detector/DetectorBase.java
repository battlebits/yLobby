package br.com.battlebits.ylobby.detector;

import br.com.battlebits.ylobby.updater.UpdaterBase;

public abstract class DetectorBase extends UpdaterBase{
	
	public DetectorBase() {
		super(15L, false);
	}
	
	public abstract void detect();

	@Override
	public void update() {
		detect();
	}

}
