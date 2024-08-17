package top.blocksp.privatechat.Command;

import org.bukkit.Bukkit;
import top.blocksp.privatechat.Command.commands.MainCommand;

public final class CommandManager {
    public void registerCommands(){
        Bukkit.getPluginCommand("privatechat").setExecutor(new MainCommand());
    }
}
