package br.com.battlebits.ylobby;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.spigotmc.ProtocolInjector.PacketTabHeader;
import org.spigotmc.ProtocolInjector.PacketTitle;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import me.flame.utils.permissions.enums.Group;
import me.flame.utils.tagmanager.enums.Tag;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityTypes;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParser;
import net.minecraft.util.com.google.gson.stream.JsonReader;

public class zUtils {

	private Plugin plugin;

	private BlockUtils blockUtils;
	private BountifullUtils bountifullUtils;
	private CommandUtils commandUtils;
	private CooldownUtils cooldownUtils;
	private InventoryUtils inventoryUtils;
	private ItemUtils itemUtils;
	private JSONUtils jsonUtils;
	private ListenerUtils listenerUtils;
	private LocationUtils locationUtils;
	private MessageUtils messageUtils;
	private NMSUtils nmsUtils;
	private PlayerUtils playerUtils;
	private TagUtils tagUtils;
	private TimeUtils timeUtils;
	private UUIDUtils uuidUtils;

	private zUtilsListener zListener;

	public zUtils(Plugin pl) {
		plugin = pl;

		blockUtils = new BlockUtils();
		bountifullUtils = new BountifullUtils();
		commandUtils = new CommandUtils();
		cooldownUtils = new CooldownUtils();
		inventoryUtils = new InventoryUtils();
		itemUtils = new ItemUtils();
		jsonUtils = new JSONUtils();
		listenerUtils = new ListenerUtils();
		locationUtils = new LocationUtils();
		messageUtils = new MessageUtils();
		nmsUtils = new NMSUtils();
		playerUtils = new PlayerUtils();
		tagUtils = new TagUtils();
		timeUtils = new TimeUtils();
		uuidUtils = new UUIDUtils();

		zListener = new zUtilsListener();
		listenerUtils.registerListeners(zListener);
	}

	public BlockUtils getBlockUtils() {
		return blockUtils;
	}

	public BountifullUtils getBountifullUtils() {
		return bountifullUtils;
	}

	public CommandUtils getCommandUtils() {
		return commandUtils;
	}

	public CooldownUtils getCooldownUtils() {
		return cooldownUtils;
	}

	public InventoryUtils getInventoryUtils() {
		return inventoryUtils;
	}

	public ItemUtils getItemUtils() {
		return itemUtils;
	}

	public JSONUtils getJSONUtils() {
		return jsonUtils;
	}

	public LocationUtils getLocationUtils() {
		return locationUtils;
	}

	public ListenerUtils getListenerUtils() {
		return listenerUtils;
	}

	public MessageUtils getMessageUtils() {
		return messageUtils;
	}

	public NMSUtils getNMSUtils() {
		return nmsUtils;
	}

	public PlayerUtils getPlayerUtils() {
		return playerUtils;
	}

	public TagUtils getTagUtils() {
		return tagUtils;
	}

	public TimeUtils getTimeUtils() {
		return timeUtils;
	}

	public UUIDUtils getUUIDUtils() {
		return uuidUtils;
	}

	public class BlockUtils {

		public HashMap<Block, Double> getInRadius(Location loc, double distance) {
			HashMap<Block, Double> blockList = new HashMap<Block, Double>();
			int iR = (int) distance + 1;
			for (int x = -iR; x <= iR; x++)
				for (int z = -iR; z <= iR; z++)
					for (int y = -iR; y <= iR; y++) {
						Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y), (int) (loc.getZ() + z));
						double offset = loc.clone().toVector().subtract(curBlock.getLocation().clone().add(0.5, 0.5, 0.5).toVector()).length();
						if (offset <= distance)
							blockList.put(curBlock, 1 - (offset / distance));
					}
			return blockList;
		}

	}

	public class BountifullUtils {

		public PacketTitle createTitlePacket(String text) {
			return new PacketTitle(org.spigotmc.ProtocolInjector.PacketTitle.Action.TITLE,
					ChatSerializer.a("{\"text\": \"" + text.replace("&", "§") + "\"}"));
		}

		public void createAndSendTitlePacket(Player p, String text) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketTitle(org.spigotmc.ProtocolInjector.PacketTitle.Action.TITLE,
					ChatSerializer.a("{\"text\": \"" + text.replace("&", "§") + "\"}")));
		}

		public PacketTitle createSubtitlePacket(String text) {
			return new PacketTitle(org.spigotmc.ProtocolInjector.PacketTitle.Action.SUBTITLE,
					ChatSerializer.a("{\"text\": \"" + text.replace("&", "§") + "\"}"));
		}

		public void createAndSendSubtitlePacket(Player p, String text) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketTitle(org.spigotmc.ProtocolInjector.PacketTitle.Action.SUBTITLE,
					ChatSerializer.a("{\"text\": \"" + text.replace("&", "§") + "\"}")));
		}

		public PacketPlayOutChat createActionPacket(String text) {
			return new PacketPlayOutChat(ChatSerializer.a("{\"text\": \"" + text.replace("&", "§") + "\"}"), 2);
		}

		public void createAndSendActionPacket(Player p, String text) {
			((CraftPlayer) p).getHandle().playerConnection
					.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{\"text\": \"" + text.replace("&", "§") + "\"}"), 2));
		}

		public PacketTabHeader createTabHeaderAndFooterPacket(String header, String footer) {
			return new PacketTabHeader(ChatSerializer.a("{\"text\": \"" + header.replace("&", "§") + "\"}"),
					ChatSerializer.a("{\"text\": \"" + footer.replace("&", "§") + "\"}"));
		}

		public void createAndSendTabHeaderAndFooterPacket(Player p, String header, String footer) {
			((CraftPlayer) p).getHandle().playerConnection
					.sendPacket(new PacketTabHeader(ChatSerializer.a("{\"text\": \"" + header.replace("&", "§") + "\"}"),
							ChatSerializer.a("{\"text\": \"" + footer.replace("&", "§") + "\"}")));
		}

	}

	public class CommandUtils {

		public void registerCommand(final CommandExecutor executor, String name, String description, String... aliases) {
			try {
				Field commandmapfield = Bukkit.getServer().getClass().getDeclaredField("commandMap");
				commandmapfield.setAccessible(true);
				SimpleCommandMap commandmap = (SimpleCommandMap) commandmapfield.get(Bukkit.getServer());
				Command command = new Command(name, description, "/" + name, Arrays.asList(aliases)) {
					@Override
					public boolean execute(CommandSender sender, String label, String[] args) {
						executor.onCommand(sender, this, label, args);
						return true;
					}
				};
				commandmap.register(plugin.getDescription().getName(), command);
			} catch (Exception e) {
				Bukkit.getLogger().warning("[zUtils] Erro ao registrar comando " + name + "!");
				e.printStackTrace();
			}
		}

	}

	public class CooldownUtils {

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
			return "1 segundo";
		}

		private Long getLongTime(UUID id, String key) {
			if (hasCooldown(id, key)) {
				return cooldowns.row(id).get(key);
			} else {
				return System.currentTimeMillis();
			}
		}

	}

	public class InventoryUtils {

		public int getInventorySizeForItens(int itens) {
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

	public class ItemUtils {

		private NBTTagCompound basic;
		private NBTTagList ench;

		public ItemUtils() {
			basic = new NBTTagCompound();
			ench = new NBTTagList();
			basic.set("ench", ench);
		}

		public ItemStack addGlow(ItemStack item) {
			net.minecraft.server.v1_7_R4.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
			if (nmsItem.hasTag()) {
				nmsItem.getTag().set("ench", ench);
			} else {
				nmsItem.setTag(basic);
			}
			return CraftItemStack.asCraftMirror(nmsItem);
		}

		public int getItemAmount(int i) {
			if (i < 1) {
				return 1;
			} else if (i > 64) {
				return 64;
			} else {
				return i;
			}
		}

		public ArrayList<String> formatForLore(String string) {
			String[] split = string.split(" ");
			string = "";
			ArrayList<String> newString = new ArrayList<String>();
			for (int i = 0; i < split.length; i++) {
				if (ChatColor.stripColor(string).length() > 25 || ChatColor.stripColor(string).endsWith(".")
						|| ChatColor.stripColor(string).endsWith("!")) {
					newString.add("§7" + string);
					if (string.endsWith(".") || string.endsWith("!"))
						newString.add("");
					string = "";
				}
				String toAdd = split[i];
				if (toAdd.contains("\\n")) {
					toAdd = toAdd.substring(0, toAdd.indexOf("\\n"));
					split[i] = split[i].substring(toAdd.length() + 2);
					newString.add("§7" + string + (string.length() == 0 ? "" : " ") + toAdd);
					string = "";
					i--;
				} else {
					string += (string.length() == 0 ? "" : " ") + toAdd;
				}
			}
			newString.add("§7" + string);
			return newString;
		}

	}

	public class JSONUtils {

		private JsonParser jsonParser;

		public JSONUtils() {
			jsonParser = new JsonParser();
		}

		public JsonObject getJSONObjectFromUrl(URL url) {
			JsonObject object = null;
			try {
				InputStream is = url.openStream();
				InputStreamReader streamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
				BufferedReader bufferedReader = new BufferedReader(streamReader);
				JsonReader reader = new JsonReader(bufferedReader);
				JsonElement element = jsonParser.parse(reader);
				object = element.getAsJsonObject();
				bufferedReader.close();
				streamReader.close();
				is.close();
				return object;
			} catch (Exception e) {
				return null;
			}
		}

		public String getValue(String key, JsonObject json) {
			return json.get(key).getAsString();
		}

	}

	public class ListenerUtils {

		public void registerListeners(Listener... listeners) {
			for (Listener listener : listeners) {
				Bukkit.getPluginManager().registerEvents(listener, plugin);
			}
		}

	}

	public class LocationUtils {

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

	public class MessageUtils {

		public String centerChatMessage(String message) {
			if (message == null || message.equals("")) {
				return "§0";
			} else {
				message = ChatColor.translateAlternateColorCodes('&', message);
				int messagePxSize = 0;
				boolean previousCode = false;
				boolean isBold = false;
				for (char c : message.toCharArray()) {
					if (c == '§') {
						previousCode = true;
						continue;
					} else if (previousCode == true) {
						previousCode = false;
						if (c == 'l' || c == 'L') {
							isBold = true;
							continue;
						} else
							isBold = false;
					} else {
						DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
						messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
						messagePxSize++;
					}
				}
				int halvedMessageSize = messagePxSize / 2;
				int toCompensate = 160 - halvedMessageSize;
				int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
				int compensated = 0;
				StringBuilder sb = new StringBuilder();
				while (compensated < toCompensate) {
					sb.append(" ");
					compensated += spaceLength;
				}
				return (sb.toString() + message);
			}
		}

	}

	public class NMSUtils {

		@SuppressWarnings("rawtypes")
		public void registerCustomEntity(Class entityClass, String name, int id) throws Exception {
			putInPrivateStaticMap(EntityTypes.class, "d", entityClass, name);
			putInPrivateStaticMap(EntityTypes.class, "f", entityClass, Integer.valueOf(id));
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private void putInPrivateStaticMap(Class<?> clazz, String fieldName, Object key, Object value) throws Exception {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			Map map = (Map) field.get(null);
			map.put(key, value);
		}

		public MinecraftServer getMinecraftServer() {
			return ((CraftServer) Bukkit.getServer()).getServer();
		}

		public WorldServer getWorldServer(World w) {
			return ((CraftWorld) w).getHandle();
		}

		public void setHeadYaw(Entity en, float yaw) {
			if (!(en instanceof EntityLiving))
				return;
			EntityLiving handle = (EntityLiving) en;
			while (yaw < -180.0F) {
				yaw += 360.0F;
			}

			while (yaw >= 180.0F) {
				yaw -= 360.0F;
			}
			handle.aO = yaw;
			if (!(handle instanceof EntityHuman))
				handle.aM = yaw;
			handle.aP = yaw;
		}

	}

	public class PlayerUtils {

		public boolean isPlayerOn18(Player p) {
			return !(((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < 47);
		}

		public void sendPacket(Player p, Packet pckt) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(pckt);
		}

		public int getPlayerPing(Player p) {
			return ((CraftPlayer) p).getHandle().ping;
		}

	}

	public class TagUtils {

		public Tag getDefaultTag(Group g) {
			return Tag.valueOf(g.toString());
		}

		public short getTagColor(Tag t) {
			switch (t) {
			case LIGHT:
				return (short) 10;
			case PREMIUM:
				return (short) 14;
			case ULTIMATE:
				return (short) 13;
			case YOUTUBER:
				return (short) 12;
			default:
				return (short) 7;
			}
		}

	}

	public class TimeUtils {

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
						str = "1 minuto";
					} else {
						str = m + " minutos";
					}
				}
				if (s > 0) {
					if (s == 1) {
						if (str.isEmpty()) {
							str = "1 segundo";
						} else {
							str = str + " e 1 segundo";
						}
					} else if (str.isEmpty()) {
						str = s + " segundos";
					} else {
						str = str + " e " + s + " segundos";
					}
				}
			} else if (i == 1) {
				str = "1 segundo";
			} else {
				str = i + " segundos";
			}
			return str;
		}

	}

	public class UUIDUtils {

		private Cache<String, String> uuidName;

		public UUIDUtils() {
			uuidName = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
				@Override
				public String load(String uuid) throws Exception {
					return getNameFromMojang(uuid);
				}
			});
		}

		public String getNameByUUID(String uuid) {
			try {
				return uuidName.get(uuid.replace("-", ""));
			} catch (Exception e) {
				return getNameFromMojang(uuid);
			}
		}

		private String getNameFromMojang(String uuid) {
			try {
				return jsonUtils.getValue("name", jsonUtils
						.getJSONObjectFromUrl(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.replace("-", ""))));
			} catch (Exception e) {
				return Bukkit.getPlayer(UUID.fromString(uuid)).getName();
			}
		}

	}

	public enum DefaultFontInfo {

		A('A', 5), a('a', 5), B('B', 5), b('b', 5), C('C', 5), c('c', 5), D('D', 5), d('d', 5), E('E', 5), e('e', 5), F('F', 5), f('f', 4), G('G',
				5), g('g', 5), H('H', 5), h('h', 5), I('I', 3), i('i', 1), J('J', 5), j('j', 5), K('K', 5), k('k', 4), L('L', 5), l('l', 1), M('M',
						5), m('m', 5), N('N', 5), n('n', 5), O('O', 5), o('o', 5), P('P', 5), p('p', 5), Q('Q', 5), q('q', 5), R('R', 5), r('r',
								5), S('S', 5), s('s', 5), T('T', 5), t('t', 4), U('U', 5), u('u', 5), V('V', 5), v('v', 5), W('W', 5), w('w',
										5), X('X', 5), x('x', 5), Y('Y', 5), y('y', 5), Z('Z', 5), z('z', 5), NUM_1('1', 5), NUM_2('2', 5), NUM_3('3',
												5), NUM_4('4', 5), NUM_5('5', 5), NUM_6('6', 5), NUM_7('7', 5), NUM_8('8', 5), NUM_9('9', 5), NUM_0(
														'0', 5), EXCLAMATION_POINT('!', 1), AT_SYMBOL('@', 6), NUM_SIGN('#', 5), DOLLAR_SIGN('$',
																5), PERCENT('%', 5), UP_ARROW('^', 5), AMPERSAND('&', 5), ASTERISK('*',
																		5), LEFT_PARENTHESIS('(', 4), RIGHT_PERENTHESIS(')', 4), MINUS('-',
																				5), UNDERSCORE('_', 5), PLUS_SIGN('+', 5), EQUALS_SIGN('=',
																						5), LEFT_CURL_BRACE('{', 4), RIGHT_CURL_BRACE('}',
																								4), LEFT_BRACKET('[', 3), RIGHT_BRACKET(']',
																										3), COLON(':', 1), SEMI_COLON(';',
																												1), DOUBLE_QUOTE('"',
																														3), SINGLE_QUOTE('\'',
																																1), LEFT_ARROW('<',
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

	private class zUtilsListener implements Listener {

		@EventHandler
		public void onPlayerQuitListener(PlayerQuitEvent e) {
			cooldownUtils.removeAllCooldowns(e.getPlayer().getUniqueId());
		}
	}

}