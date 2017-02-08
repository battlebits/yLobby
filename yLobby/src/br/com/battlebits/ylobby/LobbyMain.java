package br.com.battlebits.ylobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.command.BukkitCommandFramework;
import br.com.battlebits.commons.core.command.CommandLoader;
import br.com.battlebits.commons.core.data.DataServer;
import br.com.battlebits.commons.core.server.ServerManager;
import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.commons.core.server.loadbalancer.type.RoundRobin;
import br.com.battlebits.commons.core.translate.Language;
import br.com.battlebits.commons.core.translate.Translate;
import br.com.battlebits.commons.util.updater.AutoUpdater;
import br.com.battlebits.ylobby.detector.PlayerOutOfLobbyDetector;
import br.com.battlebits.ylobby.listener.BountifulListener;
import br.com.battlebits.ylobby.listener.GameModsListener;
import br.com.battlebits.ylobby.listener.MainListener;
import br.com.battlebits.ylobby.listener.PlayerHideListener;
import br.com.battlebits.ylobby.manager.GameModsManager;
import br.com.battlebits.ylobby.manager.LobbyItensManager;
import br.com.battlebits.ylobby.manager.LocationManager;
import br.com.battlebits.ylobby.manager.MatchSelectorManager;
import br.com.battlebits.ylobby.manager.MultiSelectorManager;
import br.com.battlebits.ylobby.manager.PlayerHideManager;
import br.com.battlebits.ylobby.manager.ScoreboardManager;
import br.com.battlebits.ylobby.profile.ProfileConfigurationInventory;
import br.com.battlebits.ylobby.profile.ProfileConfigurationListener;
import br.com.battlebits.ylobby.profile.ProfileRanksInventory;
import br.com.battlebits.ylobby.profile.ProfileRanksListener;
import br.com.battlebits.ylobby.profile.YourProfileInventory;
import br.com.battlebits.ylobby.profile.YourProfileListener;
import br.com.battlebits.ylobby.selector.gamemode.GameModeSelector;
import br.com.battlebits.ylobby.selector.lobby.LobbySelector;
import br.com.battlebits.ylobby.updater.TabAndHeaderUpdater;
import lombok.Getter;
import lombok.Setter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;

public class LobbyMain extends JavaPlugin {

	@Getter
	private static LobbyMain instance;

	@Getter
	private ServerManager serverManager;

	private MatchSelectorManager matchSelectorManager;
	private MultiSelectorManager multiSelectorManager;
	private GameModsManager gameModsManager;
	private PlayerHideManager playerHideManager;
	private LobbyItensManager lobbyItensManager;
	private LocationManager locationManager;
	private ScoreboardManager scoreboardManager;

	private LobbySelector lobbySelector;
	private GameModeSelector gameModeSelector;

	private TabAndHeaderUpdater tabAndHeaderUpdater;

	private PlayerOutOfLobbyDetector playerOutOfLobbyDetector;

	private YourProfileInventory yourProfileInventory;
	private YourProfileListener yourProfileListener;
	private ProfileRanksInventory profileRanksInventory;
	private ProfileRanksListener profileRanksListener;
	private ProfileConfigurationInventory profileConfigurationInventory;
	private ProfileConfigurationListener profileConfigurationListener;

	private BountifulListener bountifulListener;
	private GameModsListener gameModsListener;
	private PlayerHideListener playerHideListener;
	private MainListener mainListener;

	@Override
	public void onLoad() {
		new AutoUpdater(this, "7}.g=w6n+:_YG:pJ").run();
		Plugin plugin = getServer().getPluginManager().getPlugin("HolographicDisplays");
		if (plugin != null)
			new AutoUpdater(plugin, "Xr('EY$hY)68e6_G").run();
		plugin = getServer().getPluginManager().getPlugin("Citizens");
		if (plugin != null)
			new AutoUpdater(plugin, ".^rr7s6'.D<[uJ.=").run();
	}
	
	@Override
	public void onEnable() {

		getLogger().info("Habilitando plugin, por favor aguarde!");

		for (Language lang : Language.values()) {
			Translate.loadTranslations("yLobby", lang, loadTranslation(lang));
		}

		instance = this;

		CitizensAPI.createNamedNPCRegistry("lobby", new NPCDataStore() {

			@Override
			public void storeAll(NPCRegistry arg0) {

			}

			@Override
			public void store(NPC arg0) {

			}

			@Override
			public void saveToDiskImmediate() {

			}

			@Override
			public void saveToDisk() {

			}

			@Override
			public void loadInto(NPCRegistry arg0) {

			}

			@Override
			public int createUniqueNPCId(NPCRegistry arg0) {
				return 0;
			}

			@Override
			public void clearData(NPC arg0) {

			}
		});
		Bukkit.getWorlds().get(0).setAutoSave(false);
		Bukkit.getWorlds().get(0).setDifficulty(Difficulty.EASY);

		LobbyUtils.registerListener(this);

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		serverManager = new ServerManager();
		serverManager.putBalancer(ServerType.NETWORK, new RoundRobin<>());
		for (Entry<String, Map<String, String>> entry : DataServer.getAllServers().entrySet()) {
			try {
				if (!entry.getValue().containsKey("type"))
					continue;
				if (!entry.getValue().containsKey("address"))
					continue;
				if (!entry.getValue().containsKey("maxplayers"))
					continue;
				if (!entry.getValue().containsKey("onlineplayers"))
					continue;
				getServerManager().addActiveServer(entry.getValue().get("address"), entry.getKey(),
						Integer.valueOf(entry.getValue().get("maxplayers")));
				getServerManager().getServer(entry.getKey())
						.setOnlinePlayers(Integer.valueOf(entry.getValue().get("onlineplayers")));
			} catch (Exception e) {
			}
		}
		matchSelectorManager = new MatchSelectorManager();
		multiSelectorManager = new MultiSelectorManager();
		locationManager = new LocationManager();
		gameModsManager = new GameModsManager();
		playerHideManager = new PlayerHideManager();
		locationManager = new LocationManager();
		scoreboardManager = new ScoreboardManager();

		lobbySelector = new LobbySelector();
		gameModeSelector = new GameModeSelector();
		lobbyItensManager = new LobbyItensManager();

		tabAndHeaderUpdater = new TabAndHeaderUpdater();

		playerOutOfLobbyDetector = new PlayerOutOfLobbyDetector();

		yourProfileInventory = new YourProfileInventory();
		yourProfileListener = new YourProfileListener();
		profileRanksInventory = new ProfileRanksInventory();
		profileRanksListener = new ProfileRanksListener();
		profileConfigurationInventory = new ProfileConfigurationInventory();
		profileConfigurationListener = new ProfileConfigurationListener();
		gameModsListener = new GameModsListener();

		bountifulListener = new BountifulListener();
		mainListener = new MainListener();
		playerHideListener = new PlayerHideListener();
		LobbyUtils.getListenerUtils().registerListeners(this, yourProfileListener, profileRanksListener,
				profileConfigurationListener, bountifulListener, mainListener, playerHideListener, gameModsListener);

		tabAndHeaderUpdater.start();
		lobbySelector.start();
		gameModeSelector.start();
		playerOutOfLobbyDetector.start();
		new CommandLoader(new BukkitCommandFramework(this)).loadCommandsFromPackage("br.com.battlebits.ylobby.command");
		new BukkitRunnable() {
			@Override
			public void run() {
				World world = getServer().getWorlds().get(0);
				world.setWeatherDuration(10000000);
				world.setThundering(false);
				world.setStorm(false);
				world.setGameRuleValue("doMobSpawning", "false");
				world.setGameRuleValue("mobGriefing", "false");
				world.setGameRuleValue("doDaylightCycle", "false");
				world.setTime(0);
			}
		}.runTaskLater(this, 1);
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					BukkitMain.getInstance().getPubSubListener().addChannel("server-info");
				} catch (Exception e) {
					run();
				}
			}
		}.runTaskAsynchronously(this);
		BukkitMain.getInstance().setAntiAfkEnabled(false);
		getLogger().info("Plugin habilitado com sucesso!");
	}

	@Override
	public void onDisable() {

		getLogger().info("Finalizando plugin...");

		if (Bukkit.getOnlinePlayers().size() > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.kickPlayer("§cServer Restarting!");
			}
		}
		playerOutOfLobbyDetector.stop();
		tabAndHeaderUpdater.stop();
		matchSelectorManager.stop();
		multiSelectorManager.stop();
		gameModsManager.stop();
		gameModeSelector.stop();
		HandlerList.unregisterAll(this);

		getLogger().info("Plugin finalizado!");
		getLogger().warning("Reiniciando servidor!");

		Bukkit.shutdown();

	}

	public LobbySelector getLobbySelector() {
		return lobbySelector;
	}

	public GameModeSelector getGameModeSelector() {
		return gameModeSelector;
	}

	public GameModsManager getGameModsManager() {
		return gameModsManager;
	}

	public MatchSelectorManager getMatchSelectorManager() {
		return matchSelectorManager;
	}

	public MultiSelectorManager getMultiSelectorManager() {
		return multiSelectorManager;
	}

	public PlayerHideManager getPlayerHideManager() {
		return playerHideManager;
	}

	public LobbyItensManager getLobbyItensManager() {
		return lobbyItensManager;
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public YourProfileInventory getYourProfileInventory() {
		return yourProfileInventory;
	}

	public ProfileRanksInventory getProfileRanksInventory() {
		return profileRanksInventory;
	}

	public ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}

	public ProfileConfigurationInventory getProfileConfigurationInventory() {
		return profileConfigurationInventory;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> loadTranslation(Language language) {
		MongoDatabase database = BattlebitsAPI.getMongo().getClient().getDatabase("lobby");
		MongoCollection<Document> collection = database.getCollection("translation");
		Document found = collection.find(Filters.eq("language", language.toString())).first();
		if (found != null) {
			return (Map<String, String>) found.get("map");
		}
		collection.insertOne(new Document("language", language.toString()).append("map", new HashMap<>()));
		return new HashMap<>();
	}

	@Getter
	@Setter
	private String lobbyID;

}
