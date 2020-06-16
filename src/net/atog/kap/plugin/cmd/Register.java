package net.atog.kap.plugin.cmd;

import net.atog.kap.plugin.main.KhalyAuth;
import net.atog.kap.plugin.util.API;
import net.atog.kap.sqlite.Database;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Register implements CommandExecutor {
	
	private KhalyAuth pl;
	public Register(KhalyAuth pl) {
		this.pl = pl;
	}
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(!(s instanceof Player)) {
			s.sendMessage(API.color("&cSolo lo puede usar un jugador."));
		}else{
			Player p = (Player)s;
			if(args.length > 0) {
				//FileConfiguration players = pl.getPlayers();
				Database db = pl.getRDatabase();
				String pass = db.getPassword(p.getName());
				if(pass != "$none_:xdddds") {
					p.sendMessage(API.color("&b&lAuth &7➟ &cYa estás registrado."));
					return true;
				}
				if(args.length < 2) {
					//p.sendMessage(API.color("&cDebe responder correctamente los argumentos.\n&e/register <contraseña> <contraseña>"));
					return true;
				}
				if(args[0] == null) {
					//p.sendMessage(API.color("&cDebe responder correctamente los argumentos."));
					return true;
				}
				
				if(args[1] == null) {
					//p.sendMessage(API.color("&cDebe responder correctamente los argumentos."));
					return true;
				}
				String pass1 = args[0];
				String pass2 = args[1];
				if(!pass1.equals(pass2)) {
					p.sendMessage(API.color("&b&lAuth &7➟ &cLas contraseñas no son exactamente iguales."));
					return true;
				}else{
					/*players.set("Players."+p.getUniqueId()+".Data", pass1);
					pl.savePlayers();*/
					db.setPassword(p, pass1);
					p.sendMessage(API.color("&b&lAuth &7➟ &aRegistrado correctamente&e!"));
				}
			}else{
				p.sendMessage(API.color("&cDebe responder correctamente los argumentos.\n&e/register <contraseña> <contraseña>"));
			}
		}
		return false;
	}

}
