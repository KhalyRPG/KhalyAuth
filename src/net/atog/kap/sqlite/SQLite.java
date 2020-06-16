package net.atog.kap.sqlite;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import net.atog.kap.plugin.main.KhalyAuth;

public class SQLite extends Database {
	String dbname;
	private KhalyAuth plugin;
    public SQLite(KhalyAuth plugin){
        super(plugin);
        this.plugin = plugin;
        dbname = "khalyauth";
    }

    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
            	Bukkit.getConsoleSender().sendMessage("File write error: "+dbname+".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
        	
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.", "");
        }
        return null;
    }

    public void load() throws SQLException {
        connection = getSQLConnection();
        try(Statement s = connection.createStatement()) {
        	s.executeUpdate("CREATE TABLE IF NOT EXISTS khalyauth (player varchar(32) NOT NULL PRIMARY KEY, password varchar(32) NOT NULL);");
            s.close();
            
        }
        initialize();
    }
}
