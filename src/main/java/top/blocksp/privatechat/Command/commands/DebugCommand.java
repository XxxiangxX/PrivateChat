package top.blocksp.privatechat.Command.commands;

import org.bukkit.entity.Player;
import top.blocksp.privatechat.PrivateChat;

import java.io.IOException;

public final class DebugCommand {
    public static void setDebug(Player player,Boolean debug) throws IOException {
        if(!PrivateChat.getInstance().loader.getData().contains(player.getName().concat("Debug"))){
            PrivateChat.getInstance().loader.setValue(player.getName().concat("Debug"),true);
        }
        PrivateChat.getInstance().loader.setValue(player.getName().concat("Debug"),debug);
        if(debug){
            player.sendMessage("§9已开启debug模式");
        }else{
            player.sendMessage("§9已关闭debug模式");
        }
    }
}
