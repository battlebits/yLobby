package br.com.battlebits.ylobby.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChatManager implements ManagerBase{

	private ArrayList<UUID> tellDisabled;
	public HashMap<UUID, UUID> lastTell;
	
	public ChatManager() {
		tellDisabled = new ArrayList<>();
		lastTell = new HashMap<>();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		tellDisabled.clear();
		tellDisabled = null;
	}
	
	public boolean isTellEnabled(UUID id){
		return !tellDisabled.contains(id);
	}
	
	public void enableTell(UUID id){
		removeFromTellList(id);
	}
	
	public void disableTell(UUID id){
		removeFromTellList(id);
		tellDisabled.add(id);
	}
	
	public void removeFromTellList(UUID id){
		tellDisabled.remove(id);
	}
	
	public void removeFromList(UUID id){
		tellDisabled.remove(id);
		lastTell.remove(id);
	}
	
}
