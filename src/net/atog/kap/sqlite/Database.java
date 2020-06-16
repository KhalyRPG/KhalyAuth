package net.atog.kap.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import net.atog.kap.plugin.encrypt.KhalyMD5;
import net.atog.kap.plugin.main.KhalyAuth;

import org.bukkit.entity.Player;

public abstract class Database {
    KhalyAuth plugin;
    Connection connection;
    public String table = "khalyauth";
    public int tokens = 0;
    public Database(KhalyAuth instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load() throws SQLException;

    public void initialize() {
        connection = getSQLConnection();
        
        try {
        	PreparedStatement ps = connection.prepareStatement("SELECT * FROM khalyauth WHERE player = ? OR (player IS NULL AND ? IS NULL)");
        	ResultSet rs = ps.executeQuery();
        	close(ps,rs);
        }catch(SQLException e) {
        	
        }
    }

    public String getPassword(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '"+string.toLowerCase()+"';");
   
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("player").equalsIgnoreCase(string.toLowerCase())){ 
                    return rs.getString("password");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return "$none_:xdddds";
    }

    public void setPassword(Player player, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (player,password) VALUES(?,?)"); 
            ps.setString(1, player.getName().toLowerCase());                                            
            String encrypted = KhalyMD5.encrypt(password);
            ps.setString(2, encrypted);
            
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;      
    }


    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
}
 