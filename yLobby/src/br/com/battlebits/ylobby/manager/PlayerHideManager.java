package br.com.battlebits.ylobby.manager;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;

public class PlayerHideManager {

	private ArrayList<UUID> hideAllPlayers;
	private ArrayList<UUID> hideOnlyNormal;

	public PlayerHideManager() {
		hideAllPlayers = new ArrayList<>();
		hideOnlyNormal = new ArrayList<>();
	}

	public void playerJoin(Player p) {
		for (UUID id : hideAllPlayers) {
			Player hide = Bukkit.getPlayer(id);
			hide.hidePlayer(p);
		}
		if (Main.getPlugin().getPermissionManager().getPlayerGroup(p) == Group.NORMAL) {
			for (UUID id : hideOnlyNormal) {
				Player hide = Bukkit.getPlayer(id);
				hide.hidePlayer(p);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void hideOnlyNormal(Player p) {
		if (hideAllPlayers.contains(p.getUniqueId())) {
			hideAllPlayers.remove(p.getUniqueId());
		}
		hideOnlyNormal.add(p.getUniqueId());
		for (Player hide : Bukkit.getOnlinePlayers()) {
			if (hide.getUniqueId() != p.getUniqueId()) {
				if (Main.getPlugin().getPermissionManager().getPlayerGroup(hide) == Group.NORMAL) {
					p.hidePlayer(hide);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void hideAllPlayers(Player p) {
		if (hideOnlyNormal.contains(p.getUniqueId())) {
			hideOnlyNormal.remove(p.getUniqueId());
		}
		hideAllPlayers.add(p.getUniqueId());
		for (Player hide : Bukkit.getOnlinePlayers()) {
			if (hide.getUniqueId() != p.getUniqueId()) {
				p.hidePlayer(hide);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void showAllPlayers(Player p) {
		if (hideOnlyNormal.contains(p.getUniqueId())) {
			hideOnlyNormal.remove(p.getUniqueId());
		}
		if (hideAllPlayers.contains(p.getUniqueId())) {
			hideAllPlayers.remove(p.getUniqueId());
		}
		for (Player show : Bukkit.getOnlinePlayers()) {
			p.showPlayer(show);
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (Player show : Bukkit.getOnlinePlayers()) {
				p.showPlayer(show);
			}
		}
		hideAllPlayers.clear();
		hideOnlyNormal.clear();
	}

}
