package net.atog.kap.sqlite;

import java.util.logging.Level;

import net.atog.kap.plugin.main.KhalyAuth;

public class Error {
    public static void execute(KhalyAuth plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(KhalyAuth plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}