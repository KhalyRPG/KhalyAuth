package net.atog.kap.plugin.cmd;

import net.atog.kap.plugin.auth.AuthAPI;
import net.atog.kap.plugin.main.KhalyAuth;
import net.atog.kap.plugin.util.API;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePassword implements CommandExecutor {
	
	private KhalyAuth pl;
	public ChangePassword(KhalyAuth pl) {
		this.pl = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(!(s instanceof Player)) {
			s.sendMessage(API.color("Solo lo puede ejecutar el jugador."));
			return true;
		}else{
			Player p = (Player)s;
			AuthAPI auth = new AuthAPI(pl);
			String pass = pl.getRDatabase().getPassword(p.getName());
			if(!auth.isLogged(p) || !auth.isRegistrered(p))return true;
			if(args.length < 2) {
				p.sendMessage(API.color("&b&lAuth &7➟ &aEspecifica los argumentos necesarios. &e/cp <contraseña> <nueva_contraseña>"));
				return true;
			}
			if(args[0] == null || args[1] == null)return true;
			if(!args[0].equals(pass)) {
				p.sendMessage(API.color("&b&lAuth &7➟ &cContraseña incorrecta."));
			}
			
			
			
			
		}
		return false;
	}

}
