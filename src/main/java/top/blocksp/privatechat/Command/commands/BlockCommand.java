package top.blocksp.privatechat.Command.commands;

import org.bukkit.entity.Player;
import top.blocksp.privatechat.PrivateChat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BlockCommand {
    public static void blockPlayer(Player player,Player target) throws IOException {
        if(!PrivateChat.getInstance().loader.getData().contains(player.getName())){//检查玩家是否有在data里面
            PrivateChat.getInstance().loader.setValue(player.getName() + "." + target.getName(),new ArrayList<String>());
        }
        if(PrivateChat.getInstance().loader.getData().getStringList(player.getName()).contains(target.getName())){//如果检测的StringList内含有目标的名称,则说明目标已经被添加到黑名单了
            player.sendMessage("§c对方已加入黑名单，无法重复加入！");
            return;
        }
        List<String> list = PrivateChat.getInstance().loader.getData().getStringList(player.getName());//获取实时data   由于这是单个玩家操作的，所以无需考虑多人同时操作的问题
        list.add(target.getName());//加入黑名单
        PrivateChat.getInstance().loader.setValue(player.getName(),list);//设置data
        player.sendMessage("§9已将§f"+target.getName()+"§9加入黑名单！你们之间的私聊无法被看到");
    }
}
