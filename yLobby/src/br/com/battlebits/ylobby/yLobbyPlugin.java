package br.com.battlebits.ylobby;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.battlebits.commons.bukkit.command.BukkitCommandFramework;
import br.com.battlebits.commons.core.command.CommandLoader;
import br.com.battlebits.ylobby.bungee.BungeeMessageReceiver;
import br.com.battlebits.ylobby.bungee.BungeeMessageSender;
import br.com.battlebits.ylobby.detector.PlayerOutOfLobbyDetector;
import br.com.battlebits.ylobby.listener.BountifulListener;
import br.com.battlebits.ylobby.listener.GameModsListener;
import br.com.battlebits.ylobby.listener.MainListener;
import br.com.battlebits.ylobby.listener.PlayerHideListener;
import br.com.battlebits.ylobby.listener.VipSlotsListener;
import br.com.battlebits.ylobby.manager.BungeeManager;
import br.com.battlebits.ylobby.manager.ChatManager;
import br.com.battlebits.ylobby.manager.GameModsManager;
import br.com.battlebits.ylobby.manager.GameServerInfoManager;
import br.com.battlebits.ylobby.manager.LobbyItensManager;
import br.com.battlebits.ylobby.manager.LocationManager;
import br.com.battlebits.ylobby.manager.MatchSelectorManager;
import br.com.battlebits.ylobby.manager.MultiSelectorManager;
import br.com.battlebits.ylobby.manager.PlayerCountManager;
import br.com.battlebits.ylobby.manager.PlayerHideManager;
import br.com.battlebits.ylobby.manager.ScoreboardManager;
import br.com.battlebits.ylobby.manager.ServerInfoManager;
import br.com.battlebits.ylobby.profile.ProfileConfigurationInventory;
import br.com.battlebits.ylobby.profile.ProfileConfigurationListener;
import br.com.battlebits.ylobby.profile.ProfileRanksInventory;
import br.com.battlebits.ylobby.profile.ProfileRanksListener;
import br.com.battlebits.ylobby.profile.YourProfileInventory;
import br.com.battlebits.ylobby.profile.YourProfileListener;
import br.com.battlebits.ylobby.selector.gamemode.GameModeSelector;
import br.com.battlebits.ylobby.selector.gamemode.GameModeSelectorListener;
import br.com.battlebits.ylobby.selector.lobby.LobbySelector;
import br.com.battlebits.ylobby.selector.lobby.LobbySelectorListener;
import br.com.battlebits.ylobby.selector.match.MatchSelectorListener;
import br.com.battlebits.ylobby.selector.multi.MultiSelectorListener;
import br.com.battlebits.ylobby.updater.TabAndHeaderUpdater;

public class yLobbyPlugin extends JavaPlugin {

	private static yLobbyPlugin yLobby;

	// private MySQLConnection mySQLConnection;

	private BungeeMessageReceiver bungeeMessageReceiver;
	private BungeeMessageSender bungeeMessageSender;

	private MatchSelectorManager matchSelectorManager;
	private MultiSelectorManager multiSelectorManager;
	private BungeeManager bungeeManager;
	private GameServerInfoManager gameServerInfoManager;
	private ServerInfoManager serverInfoManager;
	private PlayerCountManager playerCountManager;
	private GameModsManager gameModsManager;
	private PlayerHideManager playerHideManager;
	private LobbyItensManager lobbyItensManager;
	private LocationManager locationManager;
	private ScoreboardManager scoreboardManager;
	private ChatManager chatManager;

	private LobbySelector lobbySelector;
	private LobbySelectorListener lobbySelectorListener;
	private GameModeSelector gameModeSelector;
	private GameModeSelectorListener gameModeSelectorListener;
	private MatchSelectorListener matchSelectorListener;
	private MultiSelectorListener multiSelectorListener;

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
	private VipSlotsListener vipSlotsListener;

	@Override
	public void onEnable() {

		getLogger().info("Habilitando plugin, por favor aguarde!");

		yLobby = this;

		Bukkit.getWorlds().get(0).setAutoSave(false);
		Bukkit.getWorlds().get(0).setDifficulty(Difficulty.EASY);

		LobbyUtils.registerListener(this);

		// mySQLConnection = new MySQLConnection();
		// mySQLConnection.tryToConnect();
		// mySQLConnection.createTables();

		bungeeMessageReceiver = new BungeeMessageReceiver();
		bungeeMessageSender = new BungeeMessageSender();

		Bukkit.getMessenger().registerIncomingPluginChannel(yLobby, "BungeeCord", bungeeMessageReceiver);
		Bukkit.getMessenger().registerOutgoingPluginChannel(yLobby, "BungeeCord");

		matchSelectorManager = new MatchSelectorManager();
		multiSelectorManager = new MultiSelectorManager();
		bungeeManager = new BungeeManager();
		gameServerInfoManager = new GameServerInfoManager();
		serverInfoManager = new ServerInfoManager();
		playerCountManager = new PlayerCountManager();
		locationManager = new LocationManager();
		gameModsManager = new GameModsManager();
		playerHideManager = new PlayerHideManager();
		locationManager = new LocationManager();
		scoreboardManager = new ScoreboardManager();
		chatManager = new ChatManager();

		lobbySelector = new LobbySelector();
		lobbySelectorListener = new LobbySelectorListener();
		gameModeSelector = new GameModeSelector();
		gameModeSelectorListener = new GameModeSelectorListener();
		matchSelectorListener = new MatchSelectorListener();
		multiSelectorListener = new MultiSelectorListener();
		lobbyItensManager = new LobbyItensManager();

		tabAndHeaderUpdater = new TabAndHeaderUpdater();

		playerOutOfLobbyDetector = new PlayerOutOfLobbyDetector();

		yourProfileInventory = new YourProfileInventory();
		yourProfileListener = new YourProfileListener();
		profileRanksInventory = new ProfileRanksInventory();
		profileRanksListener = new ProfileRanksListener();
		profileConfigurationInventory = new ProfileConfigurationInventory();
		profileConfigurationListener = new ProfileConfigurationListener();

		vipSlotsListener = new VipSlotsListener();
		bountifulListener = new BountifulListener();
		mainListener = new MainListener();
		playerHideListener = new PlayerHideListener();
		gameModsListener = new GameModsListener();

		LobbyUtils.getListenerUtils().registerListeners(this, gameModeSelectorListener, lobbySelectorListener,
				matchSelectorListener, multiSelectorListener, yourProfileListener, profileRanksListener,
				profileConfigurationListener, bountifulListener, mainListener, playerHideListener, gameModsListener,
				vipSlotsListener);

		chatManager.start();

		tabAndHeaderUpdater.start();

		playerOutOfLobbyDetector.start();

		new CommandLoader(new BukkitCommandFramework(this)).loadCommandsFromPackage("br.com.battlebits.ylobby.command");

		getLogger().info("Plugin habilitado com sucesso!");
	}

	@Override
	public void onDisable() {

		getLogger().info("Finalizando plugin...");

		if (Bukkit.getOnlinePlayers().size() > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.kickPlayer("§cO servidor está reiniciando!");
			}
		}

		// mySQLConnection.stop();

		bungeeMessageReceiver.stop();

		playerOutOfLobbyDetector.stop();

		tabAndHeaderUpdater.stop();

		chatManager.stop();
		matchSelectorManager.stop();
		multiSelectorManager.stop();
		gameServerInfoManager.stop();
		serverInfoManager.stop();

		gameModsManager.stop();

		gameModeSelector.stop();
		lobbySelector.stop();

		HandlerList.unregisterAll(yLobby);

		getLogger().info("Plugin finalizado!");
		getLogger().warning("Reiniciando servidor!");

		Bukkit.shutdown();

	}

	public static yLobbyPlugin getyLobby() {
		return yLobby;
	}

	public BungeeMessageSender getBungeeMessageSender() {
		return bungeeMessageSender;
	}

	// public MySQLConnection getMySQLConnection() {
	// return mySQLConnection;
	// }

	public BungeeManager getBungeeManager() {
		return bungeeManager;
	}

	public PlayerCountManager getPlayerCountManager() {
		return playerCountManager;
	}

	public GameServerInfoManager getGameServerInfoManager() {
		return gameServerInfoManager;
	}

	public ServerInfoManager getServerInfoManager() {
		return serverInfoManager;
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

	public ChatManager getChatManager() {
		return chatManager;
	}

	public ProfileConfigurationInventory getProfileConfigurationInventory() {
		return profileConfigurationInventory;
	}

}
