package br.com.battlebits.ylobby.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.yaddons.yAddonsPlugin;
import br.com.battlebits.ylobby.yLobbyPlugin;

public class MySQLConnection {

	private Connection connection;
	private String user;
	private String port;
	private String pass;
	private String ip;
	private String database;

	public MySQLConnection() {
		user = "vitor";
		port = "3306";
		pass = "2U9CdhdHM5mxpauG";
		ip = "localhost";
		database = "lobby";
	}

	public void tryToConnect() {
		try {
			yLobbyPlugin.getyLobby().getLogger().info("[MySQLConnection] Conectando ao Banco de Dados...");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database + "?autoReconnect=true", user, pass);
			yLobbyPlugin.getyLobby().getLogger().info("[MySQLConnection] Conectado ao Banco de Dados!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createTables() {
		sendUpdate(
				"CREATE TABLE IF NOT EXISTS `parkour_records` (" + " `parkour_name` varchar(32) NOT NULL," + "  `player_uuid` varchar(36) NOT NULL, "
						+ " `parkour_time` int(11) NOT NULL, " + "  `parkour_rank` varchar(8) NOT NULL " + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
	}

	public void tryToReconnect() {
		stop();
		tryToConnect();
	}

	public void stop() {
		if (connection != null) {
			try {
				yLobbyPlugin.getyLobby().getLogger().info("[MySQLConnection] Fechando conexao com o Banco de Dados!");
				if (!connection.isClosed()) {
					connection.close();
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
					Statement stmt = connection.createStatement();
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
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			yLobbyPlugin.getyLobby().getLogger().warning("[MySQLConnection] Erro com a query " + sql + "!");
			return null;
		}
	}
}
