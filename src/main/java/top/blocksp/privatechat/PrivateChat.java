package top.blocksp.privatechat;

import org.bukkit.plugin.java.JavaPlugin;
import top.blocksp.privatechat.Command.CommandManager;
import top.blocksp.privatechat.Config.ConfigLoader;

public final class PrivateChat extends JavaPlugin {
    private static PrivateChat pc;

    public static PrivateChat getInstance() {
        return pc;
    }

    public ConfigLoader loader;
    public CommandManager commandManager;

    @Override
    public void onLoad(){
        pc = this;
    }
    @Override
    public void onEnable() {
        getLogger().info("私聊插件正在加载中...");
        loader = new ConfigLoader();
        commandManager = new CommandManager();
        loader.createConfig();
        commandManager.registerCommands();
        getLogger().info("私聊插件加载成功");
    }

    @Override
    public void onDisable() {
        getLogger().info("私聊插件正在卸载中...");
        getLogger().info("私聊插件卸载成功");
    }
}
