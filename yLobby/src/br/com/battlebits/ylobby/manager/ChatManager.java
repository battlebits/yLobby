package br.com.battlebits.ylobby.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class ChatManager implements ManagerBase {

	private ArrayList<UUID> tellDisabled;
	private ArrayList<UUID> chatDisabled;
	private HashMap<UUID, UUID> lastTell;
	private ArrayList<String> chatDisabledMessage;

	public ChatManager() {
		tellDisabled = new ArrayList<>();
		lastTell = new HashMap<>();
		chatDisabled = new ArrayList<>();
		chatDisabledMessage = new ArrayList<>();
		chatDisabledMessage.add("§0");
		chatDisabledMessage.add(yLobbyPlugin.getyLobby().getzUtils().getMessageUtils().centerChatMessage("§7Seu §c§lchat §7esta §c§ldesabilitado§7!"));
		chatDisabledMessage.add("§0");
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
		tellDisabled.clear();
		tellDisabled = null;
		lastTell.clear();
		lastTell = null;
		chatDisabled.clear();
		chatDisabled = null;
		chatDisabledMessage.clear();
		chatDisabledMessage = null; 
	}

	public ArrayList<UUID> getChatDisabledPlayers() {
		return chatDisabled;
	}
	
	public void chatDisabledMessage(Player p){
		for(String msg : chatDisabledMessage){
			p.sendMessage(msg);
		}
	}

	public boolean isChatEnabled(UUID id) {
		return !chatDisabled.contains(id);
	}

	public void enableChat(UUID id) {
		removeFromChatList(id);
	}

	public void disableChat(UUID id) {
		removeFromChatList(id);
		chatDisabled.add(id);
	}

	public void removeFromChatList(UUID id) {
		chatDisabled.remove(id);
	}

	public boolean isTellEnabled(UUID id) {
		return !tellDisabled.contains(id);
	}

	public void enableTell(UUID id) {
		removeFromTellList(id);
	}

	public void disableTell(UUID id) {
		removeFromTellList(id);
		tellDisabled.add(id);
	}

	public void removeFromTellList(UUID id) {
		tellDisabled.remove(id);
	}

	public void removeFromList(UUID id) {
		tellDisabled.remove(id);
		lastTell.remove(id);
		chatDisabled.remove(id);
	}

	public UUID getLastTell(UUID id) {
		return lastTell.get(id);
	}

	public boolean hasLastTell(UUID id) {
		return lastTell.containsKey(id);
	}

	public void setLastTell(UUID sender, UUID receiver) {
		lastTell.put(sender, receiver);
		lastTell.put(receiver, sender);
	}

}
