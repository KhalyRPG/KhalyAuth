package net.atog.kap.plugin.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.atog.kap.plugin.cmd.Login;
import net.atog.kap.plugin.cmd.Register;
import net.atog.kap.plugin.events.AuthEvent;
import net.atog.kap.plugin.task.LoginTask;
import net.atog.kap.sqlite.Database;
import net.atog.kap.sqlite.SQLite;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KhalyAuth extends JavaPlugin {
	public String rutaConfig;
	private Database db;
	ConsoleCommandSender console = Bukkit.getConsoleSender();
	
	public List<Player> lista = new ArrayList<Player>();
	
	public void onEnable() { try {
		setup();
	} catch (SQLException e) {
		e.printStackTrace();
	} }
	
	public void onDisable() { lista.clear(); }
	
	private void setup() throws SQLException {
		// Variables
		PluginManager pm = Bukkit.getPluginManager();
		LoginTask lt = new LoginTask(this);
		// SQLite
		this.db = new SQLite(this);
		this.db.load();
		
		// Comandos
		this.getCommand("login").setExecutor(new Login(this));
		this.getCommand("register").setExecutor(new Register(this));
		
		// Tareas
		lt.startTask();
		lt.effects();
		
		// Eventos
		pm.registerEvents(new AuthEvent(this), this);
	}
	
	public Database getRDatabase() {
        return this.db;
    }
	
	
}
