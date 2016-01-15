package br.com.battlebits.ylobby;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.battlebits.ylobby.bungee.BungeeMessageReceiver;
import br.com.battlebits.ylobby.bungee.BungeeMessageSender;
import br.com.battlebits.ylobby.commands.FlyCommand;
import br.com.battlebits.ylobby.commands.SpawnCommand;
import br.com.battlebits.ylobby.detector.PlayerOutOfLobbyDetector;
import br.com.battlebits.ylobby.listener.BountifulListener;
import br.com.battlebits.ylobby.listener.GameModsListener;
import br.com.battlebits.ylobby.listener.MainListener;
import br.com.battlebits.ylobby.listener.PlayerHideListener;
import br.com.battlebits.ylobby.manager.BungeeManager;
import br.com.battlebits.ylobby.manager.GameModsManager;
import br.com.battlebits.ylobby.manager.GameServerInfoManager;
import br.com.battlebits.ylobby.manager.LobbyItensManager;
import br.com.battlebits.ylobby.manager.LocationManager;
import br.com.battlebits.ylobby.manager.MatchSelectorManager;
import br.com.battlebits.ylobby.manager.PlayerCountManager;
import br.com.battlebits.ylobby.manager.PlayerHideManager;
import br.com.battlebits.ylobby.manager.ServerInfoManager;
import br.com.battlebits.ylobby.manager.TabHeaderAndFooterManager;
import br.com.battlebits.ylobby.profile.ProfileRanksInventory;
import br.com.battlebits.ylobby.profile.ProfileRanksListener;
import br.com.battlebits.ylobby.profile.YourProfileInventory;
import br.com.battlebits.ylobby.profile.YourProfileListener;
import br.com.battlebits.ylobby.selector.gamemode.GameModeSelector;
import br.com.battlebits.ylobby.selector.gamemode.GameModeSelectorListener;
import br.com.battlebits.ylobby.selector.lobby.LobbySelector;
import br.com.battlebits.ylobby.selector.lobby.LobbySelectorListener;
import br.com.battlebits.ylobby.selector.match.MatchSelectorListener;

public class yLobbyPlugin extends JavaPlugin {

	private static yLobbyPlugin yLobby;

	private zUtils zUtils;

	// private MySQLConnection mySQLConnection;

	private BungeeMessageReceiver bungeeMessageReceiver;
	private BungeeMessageSender bungeeMessageSender;

	private MatchSelectorManager matchSelectorManager;
	private BungeeManager bungeeManager;
	private GameServerInfoManager gameServerInfoManager;
	private ServerInfoManager serverInfoManager;
	private PlayerCountManager playerCountManager;
	private GameModsManager gameModsManager;
	private TabHeaderAndFooterManager tabHeaderAndFooterManager;
	private PlayerHideManager playerHideManager;
	private LobbyItensManager lobbyItensManager;
	private LocationManager locationManager;

	private LobbySelector lobbySelector;
	private LobbySelectorListener lobbySelectorListener;
	private GameModeSelector gameModeSelector;
	private GameModeSelectorListener gameModeSelectorListener;
	private MatchSelectorListener matchSelectorListener;

	// private PortalPlayerDetector portalPlayerDetector;
	private PlayerOutOfLobbyDetector playerOutOfLobbyDetector;

	private YourProfileInventory yourProfileInventory;
	private YourProfileListener yourProfileListener;
	private ProfileRanksInventory profileRanksInventory;
	private ProfileRanksListener profileRanksListener;

	// private PortalListener portalListener;
	private BountifulListener bountifulListener;
	private GameModsListener gameModsListener;
	private PlayerHideListener playerHideListener;
	private MainListener mainListener;

	@Override
	public void onEnable() {

		yLobby = this;

		Bukkit.getWorlds().get(0).setAutoSave(false);

		zUtils = new zUtils(this);

		// mySQLConnection = new MySQLConnection();
		// mySQLConnection.tryToConnect();
		// mySQLConnection.createTables();

		bungeeMessageReceiver = new BungeeMessageReceiver();
		bungeeMessageSender = new BungeeMessageSender();

		Bukkit.getMessenger().registerIncomingPluginChannel(yLobby, "BungeeCord", bungeeMessageReceiver);
		Bukkit.getMessenger().registerOutgoingPluginChannel(yLobby, "BungeeCord");

		matchSelectorManager = new MatchSelectorManager();
		bungeeManager = new BungeeManager();
		gameServerInfoManager = new GameServerInfoManager();
		serverInfoManager = new ServerInfoManager();
		playerCountManager = new PlayerCountManager();
		locationManager = new LocationManager();
		gameModsManager = new GameModsManager();
		tabHeaderAndFooterManager = new TabHeaderAndFooterManager();
		playerHideManager = new PlayerHideManager();
		locationManager = new LocationManager();

		lobbySelector = new LobbySelector();
		lobbySelectorListener = new LobbySelectorListener();
		gameModeSelector = new GameModeSelector();
		gameModeSelectorListener = new GameModeSelectorListener();
		matchSelectorListener = new MatchSelectorListener();
		lobbyItensManager = new LobbyItensManager();

		// portalPlayerDetector = new PortalPlayerDetector();
		playerOutOfLobbyDetector = new PlayerOutOfLobbyDetector();

		yourProfileInventory = new YourProfileInventory();
		yourProfileListener = new YourProfileListener();
		profileRanksInventory = new ProfileRanksInventory();
		profileRanksListener = new ProfileRanksListener();

		// portalListener = new PortalListener();
		bountifulListener = new BountifulListener();
		mainListener = new MainListener();
		playerHideListener = new PlayerHideListener();
		gameModsListener = new GameModsListener();

		zUtils.getListenerUtils().registerListeners(gameModeSelectorListener, lobbySelectorListener, matchSelectorListener, yourProfileListener,
				profileRanksListener, bountifulListener, mainListener, playerHideListener, gameModsListener);

		// Bukkit.getPluginManager().registerEvents(portalListener, yLobby);

		zUtils.getCommandUtils().registerCommand(new SpawnCommand(), "spawn", "Use esse comando para ir ao Spawn do Lobby", "lobby", "hub");
		zUtils.getCommandUtils().registerCommand(new FlyCommand(), "fly", "Use esse comando para ativar ou desativar seu fly", "voar");

	}

	@Override
	public void onDisable() {

		bungeeMessageReceiver.stop();

		playerOutOfLobbyDetector.stop();
		// portalPlayerDetector.stop();

		matchSelectorManager.stop();
		gameServerInfoManager.stop();
		serverInfoManager.stop();

		gameModsManager.stop();
		tabHeaderAndFooterManager.stop();

		gameModeSelector.stop();
		lobbySelector.stop();

		HandlerList.unregisterAll(yLobby);

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

	public zUtils getzUtils() {
		return zUtils;
	}

}
