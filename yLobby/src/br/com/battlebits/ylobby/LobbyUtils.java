package br.com.battlebits.ylobby;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import lombok.Getter;

@Getter
public class LobbyUtils {
	@Getter
	private static BlockUtils blockUtils = new BlockUtils();
	@Getter
	private static CooldownUtils cooldownUtils = new CooldownUtils();
	@Getter
	private static InventoryUtils inventoryUtils = new InventoryUtils();
	@Getter
	private static ListenerUtils listenerUtils = new ListenerUtils();
	@Getter
	private static LocationUtils locationUtils = new LocationUtils();
	@Getter
	private static TimeUtils timeUtils = new TimeUtils();

	public static void registerListener(Plugin plugin) {
		listenerUtils.registerListeners(plugin, new zUtilsListener());
	}

	public static class BlockUtils {

		public HashMap<Block, Double> getInRadius(Location loc, double distance) {
			HashMap<Block, Double> blockList = new HashMap<Block, Double>();
			int iR = (int) distance + 1;
			for (int x = -iR; x <= iR; x++)
				for (int z = -iR; z <= iR; z++)
					for (int y = -iR; y <= iR; y++) {
						Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y),
								(int) (loc.getZ() + z));
						double offset = loc.clone().toVector()
								.subtract(curBlock.getLocation().clone().add(0.5, 0.5, 0.5).toVector()).length();
						if (offset <= distance)
							blockList.put(curBlock, 1 - (offset / distance));
					}
			return blockList;
		}

	}

	public static class CooldownUtils {

		private Table<UUID, String, Long> cooldowns;

		public CooldownUtils() {
			cooldowns = HashBasedTable.create();
		}

		public void removeAllCooldowns(UUID id) {
			cooldowns.row(id).clear();
		}

		public boolean hasCooldown(UUID id, String key) {
			if (cooldowns.containsRow(id) && cooldowns.row(id).containsKey(key)) {
				return true;
			}
			return false;
		}

		public void removeCooldown(UUID id, String key) {
			if (hasCooldown(id, key)) {
				cooldowns.row(id).remove(id);
			}
		}

		public void setCooldown(UUID id, String key, int seconds) {
			removeCooldown(id, key);
			cooldowns.put(id, key, System.currentTimeMillis() + seconds * 1000);
		}

		public boolean isOnCooldown(UUID id, String key) {
			if (hasCooldown(id, key)) {
				if (System.currentTimeMillis() - getLongTime(id, key) >= 0) {
					removeCooldown(id, key);
					return false;
				}
			}
			return true;
		}

		public String getCooldwonTimeFormated(UUID id, String key) {
			if (hasCooldown(id, key)) {
				if (!isOnCooldown(id, key)) {
					int i = (int) (getLongTime(id, key) / 1000);
					return timeUtils.formatTime(i);
				}
			}
			return "1 §%second%§";
		}

		private Long getLongTime(UUID id, String key) {
			if (hasCooldown(id, key)) {
				return cooldowns.row(id).get(key);
			} else {
				return System.currentTimeMillis();
			}
		}

	}

	public static class InventoryUtils {

		public int getInventorySizeForItens(int itens) {
			if (itens > 45) {
				return 6;
			} else if (itens > 36) {
				return 5;
			} else if (itens > 27) {
				return 4;
			} else if (itens > 18) {
				return 3;
			} else if (itens > 9) {
				return 2;
			} else {
				return 1;
			}
		}
		public int getInventorySizeForItensOld(int itens) {
			if (itens > 45) {
				return 54;
			} else if (itens > 36) {
				return 45;
			} else if (itens > 27) {
				return 36;
			} else if (itens > 18) {
				return 27;
			} else if (itens > 9) {
				return 18;
			} else {
				return 9;
			}
		}

		public String createCenterTitle(String str) {
			if (str.length() > 30) {
				str.substring(0, 30);
			}
			str = "§n" + str;
			while (str.length() < 32) {
				str = " " + str;
			}
			return str;
		}

	}

	public static class ListenerUtils {

		public void registerListeners(Plugin plugin, Listener... listeners) {
			for (Listener listener : listeners) {
				Bukkit.getPluginManager().registerEvents(listener, plugin);
			}
		}

	}

	public static class LocationUtils {

		public Location getCenter(Location loc, boolean changeY) {
			Location l = loc.clone();
			l.setX(getRelativeCoord(l.getBlockX()));
			l.setZ(getRelativeCoord(l.getBlockZ()));
			if (changeY) {
				l.setY(getRelativeCoord(l.getBlockY()));
			}
			return l;
		}

		private double getRelativeCoord(int i) {
			double d = i;
			d = d < 0 ? d - 0.5 : d + 0.5;
			return d;
		}

		public Location lookAt(Location loc, Location lookat) {
			loc = loc.clone();
			double dx = lookat.getX() - loc.getX();
			double dy = lookat.getY() - loc.getY();
			double dz = lookat.getZ() - loc.getZ();
			if (dx != 0) {
				if (dx < 0) {
					loc.setYaw((float) (1.5 * Math.PI));
				} else {
					loc.setYaw((float) (0.5 * Math.PI));
				}
				loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
			} else if (dz < 0) {
				loc.setYaw((float) Math.PI);
			}
			double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));
			loc.setPitch((float) -Math.atan(dy / dxz));
			loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
			loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);
			return loc;
		}

	}

	public static class TimeUtils {

		public String milisecondsToTime(int ms) {
			String hours = ms / 3600000 + "";
			String minutes = ((ms - (Integer.valueOf(hours) * 3600000)) / 60000) + "";
			String seconds = ((ms - (Integer.valueOf(minutes) * 60000)) / 1000) + "";
			String miliseconds = (ms - (Integer.valueOf(seconds) * 1000)) + "";
			if (minutes.length() == 1) {
				minutes = "0" + minutes;
			}
			if (seconds.length() == 1) {
				seconds = "0" + seconds;
			}
			String str = "";
			if (Integer.valueOf(miliseconds) > 0) {
				str = miliseconds;
			}
			if (str.length() > 0) {
				str = "." + str;
			}
			str = seconds + str;
			str = minutes + ":" + str;
			if (Integer.valueOf(hours) > 0) {
				str = hours + ":" + str;
			}
			return str;
		}

		public String formatTime(int i) {
			String str = "";
			if (i >= 60) {
				int m = i / 60;
				int s = i % 60;
				if (m > 0) {
					if (m == 1) {
						str = "1 §%minute%§";
					} else {
						str = m + " §%minute%§s";
					}
				}
				if (s > 0) {
					if (s == 1) {
						if (str.isEmpty()) {
							str = "1 §%second%§";
						} else {
							str = str + " §%and%§ 1 §%second%§";
						}
					} else if (str.isEmpty()) {
						str = s + " §%second%§s";
					} else {
						str = str + " §%and%§ " + s + " §%second%§s";
					}
				}
			} else if (i == 1) {
				str = "1 §%second%§";
			} else {
				str = i + " §%second%§s";
			}
			return str;
		}

	}

	public enum DefaultFontInfo {

		A('A', 5), a('a', 5), B('B', 5), b('b', 5), C('C', 5), c('c', 5), D('D', 5), d('d', 5), E('E', 5), e('e', 5), F(
				'F', 5), f('f', 4), G('G', 5), g('g', 5), H('H', 5), h('h', 5), I('I', 3), i('i', 1), J('J', 5), j('j',
						5), K('K', 5), k('k', 4), L('L', 5), l('l', 1), M('M', 5), m('m', 5), N('N', 5), n('n', 5), O(
								'O',
								5), o('o', 5), P('P', 5), p('p', 5), Q('Q', 5), q('q', 5), R('R', 5), r('r', 5), S('S',
										5), s('s', 5), T('T', 5), t('t', 4), U('U', 5), u('u', 5), V('V', 5), v('v',
												5), W('W', 5), w('w', 5), X('X', 5), x('x', 5), Y('Y', 5), y('y', 5), Z(
														'Z', 5), z('z', 5), NUM_1('1', 5), NUM_2('2', 5), NUM_3('3',
																5), NUM_4('4', 5), NUM_5('5', 5), NUM_6('6', 5), NUM_7(
																		'7', 5), NUM_8('8', 5), NUM_9('9', 5), NUM_0(
																				'0', 5), EXCLAMATION_POINT('!',
																						1), AT_SYMBOL('@', 6), NUM_SIGN(
																								'#',
																								5), DOLLAR_SIGN('$',
																										5), PERCENT('%',
																												5), UP_ARROW(
																														'^',
																														5), AMPERSAND(
																																'&',
																																5), ASTERISK(
																																		'*',
																																		5), LEFT_PARENTHESIS(
																																				'(',
																																				4), RIGHT_PERENTHESIS(
																																						')',
																																						4), MINUS(
																																								'-',
																																								5), UNDERSCORE(
																																										'_',
																																										5), PLUS_SIGN(
																																												'+',
																																												5), EQUALS_SIGN(
																																														'=',
																																														5), LEFT_CURL_BRACE(
																																																'{',
																																																4), RIGHT_CURL_BRACE(
																																																		'}',
																																																		4), LEFT_BRACKET(
																																																				'[',
																																																				3), RIGHT_BRACKET(
																																																						']',
																																																						3), COLON(
																																																								':',
																																																								1), SEMI_COLON(
																																																										';',
																																																										1), DOUBLE_QUOTE(
																																																												'"',
																																																												3), SINGLE_QUOTE(
																																																														'\'',
																																																														1), LEFT_ARROW(
																																																																'<',
																																																																4), RIGHT_ARROW(
																																																																		'>',
																																																																		4), QUESTION_MARK(
																																																																				'?',
																																																																				5), SLASH(
																																																																						'/',
																																																																						5), BACK_SLASH(
																																																																								'\\',
																																																																								5), LINE(
																																																																										'|',
																																																																										1), TILDE(
																																																																												'~',
																																																																												5), TICK(
																																																																														'`',
																																																																														2), PERIOD(
																																																																																'.',
																																																																																1), COMMA(
																																																																																		',',
																																																																																		1), SPACE(
																																																																																				' ',
																																																																																				3), DEFAULT(
																																																																																						'a',
																																																																																						4);

		private char character;
		private int length;

		DefaultFontInfo(char character, int length) {
			this.character = character;
			this.length = length;
		}

		public char getCharacter() {
			return this.character;
		}

		public int getLength() {
			return this.length;
		}

		public int getBoldLength() {
			if (this == DefaultFontInfo.SPACE)
				return this.getLength();
			return this.length + 1;
		}

		public static DefaultFontInfo getDefaultFontInfo(char c) {
			for (DefaultFontInfo dFI : DefaultFontInfo.values()) {
				if (dFI.getCharacter() == c)
					return dFI;
			}
			return DefaultFontInfo.DEFAULT;
		}
	}

	public static int getItemAmount(int i) {
		if (i < 1) {
			return 1;
		} else if (i > 64) {
			return 64;
		} else {
			return i;
		}
	}

	private static class zUtilsListener implements Listener {

		@EventHandler
		public void onPlayerQuitListener(PlayerQuitEvent e) {
			cooldownUtils.removeAllCooldowns(e.getPlayer().getUniqueId());
		}
	}

}