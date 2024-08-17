package top.blocksp.privatechat.Command.commands;

import org.bukkit.entity.Player;
import top.blocksp.privatechat.PrivateChat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class RelieveCommand {
    public static void relievePlayer(Player player, Player target) throws IOException {
        if(!PrivateChat.getInstance().loader.getData().contains(player.getName())){//检查玩家是否有在data里面
            PrivateChat.getInstance().loader.setValue(player.getName() + "." + target.getName(),new ArrayList<String>());
        }
        if(!PrivateChat.getInstance().loader.getData().getStringList(player.getName()).contains(target.getName())){//如果检测的StringList内不含有目标的名称,则说明目标已经被移除黑名单了
            player.sendMessage("§c对方已移除黑名单，无法重复移除！");
            return;
        }
        List<String> list = PrivateChat.getInstance().loader.getData().getStringList(player.getName());//获取实时data   由于这是单个玩家操作的，所以无需考虑多人同时操作的问题
        list.remove(target.getName());//移除黑名单
        PrivateChat.getInstance().loader.setValue(player.getName(),list);//设置data
        player.sendMessage("§9已将§f"+target.getName()+"§9移除黑名单！");
    }
}
