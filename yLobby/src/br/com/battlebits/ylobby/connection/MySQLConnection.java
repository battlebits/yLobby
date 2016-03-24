package br.com.battlebits.ylobby.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class MySQLConnection {

	private Connection lobbyConnection;
	private String user;
	private String port;
	private String pass;
	private String ip;
	private String lobbyDatabase;

	public MySQLConnection() {
		user = "vitor";
		port = "3306";
		pass = "2U9CdhdHM5mxpauG";
		ip = "localhost";
		lobbyDatabase = "lobby";
	}

	public void tryToConnect() {
		try {
			yLobbyPlugin.getyLobby().getLogger().info("[MySQLConnection] Conectando ao Banco de Dados...");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			lobbyConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + lobbyDatabase + "?autoReconnect=true", user,
					pass);
			yLobbyPlugin.getyLobby().getLogger().info("[MySQLConnection] Conectando ao Banco de Dados...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createTables() {
		sendUpdate("CREATE TABLE IF NOT EXISTS `uuid_forumid` (" + "  `uuid` varchar(32) NOT NULL," + "  `forumid` varchar(32) NOT NULL, "
				+ "  PRIMARY KEY (`uuid`)" + " ) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
	}

	public void tryToReconnect() {
		stop();
		tryToConnect();
	}

	public Connection getConnection() {
		return lobbyConnection;
	}

	public void stop() {
		if (lobbyConnection != null) {
			try {
				yLobbyPlugin.getyLobby().getLogger().info("[MySQLConnection] Fechando conexao com o Banco de Dados!");
				if (!lobbyConnection.isClosed()) {
					lobbyConnection.close();
					yLobbyPlugin.getyLobby().getLogger().info("[MySQLConnection] Conexao com o Banco de Dados fechada!");
				}
			} catch (Exception e) {
				yLobbyPlugin.getyLobby().getLogger().warning("[MySQLConnection] Erro ao tentar fechar conexao com o Banco de Dados!");
				e.printStackTrace();
			}
		}
	}

	public void sendUpdate(final String sql) {
		new BukkitRunnable() {
			public void run() {
				try {
					Statement stmt = lobbyConnection.createStatement();
					stmt.execute(sql);
					stmt.close();
				} catch (SQLException e) {
					yLobbyPlugin.getyLobby().getLogger().warning("[MySQLConnection] Erro com o update query " + sql + "!");
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(yLobbyPlugin.getyLobby());
	}

	public ResultSet queryResults(String sql) {
		try {
			Statement stmt = lobbyConnection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			yLobbyPlugin.getyLobby().getLogger().warning("[MySQLConnection] Erro com a query " + sql + "!");
			return null;
		}
	}

}
