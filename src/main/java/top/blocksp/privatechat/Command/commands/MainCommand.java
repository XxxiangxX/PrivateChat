package top.blocksp.privatechat.Command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import top.blocksp.privatechat.PrivateChat;
import top.blocksp.privatechat.Util.AsyncUtil;

import java.io.IOException;
import java.util.ArrayList;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {//判断是否有子命令
            sender.sendMessage("§cUsage: /pc <player> <message>");
            return true;
        }
        Player player = null;//定义空发送者玩家
        if(sender instanceof Player){
            player = (Player)sender;//如果发送者是玩家，则赋值 后续可使用检测null的方法检测是否是控制台操作
        }
        Player target;//定义空目标玩家
        if(args.length == 1){
            if(args[0].equals("block") | args[0].equals("relieve") | args[0].equals("help") | args[0].equals("debug") && sender instanceof Player) {//如果第一条子命令为以上特殊命令，则执行特殊方法
                switch (args[0]) {
                    case "block":
                        sender.sendMessage("§cUsage: /pc block <player>");
                        break;
                    case "relieve":
                        sender.sendMessage("§cUsage: /pc relieve <player>");
                        break;
                    case "help":
                        sender.sendMessage("§9/pc help");
                        sender.sendMessage("§9/pc block <player> 拉黑某人");
                        sender.sendMessage("§9/pc relieve <player> 释放某人");
                        sender.sendMessage("§9/pc debug <true/false> 关闭或开启debug");
                        break;
                    case "debug":
                        sender.sendMessage("§cUsage: /pc debug <on/off>");
                        break;
                }
                return true;
            }
            sender.sendMessage("§cUsage: /pc <player> <message>");
            return true;
        }
        if(args.length >= 2){
            if(args[0].equals("block") | args[0].equals("relieve") | args[0].equals("debug") && player != null) {
                target = Bukkit.getPlayerExact(args[1]);//给空目标玩家赋予拉进黑名单或移出黑名单的玩家
                Player finalPlayer = player;//指定发送者玩家，由于上面的if语句已经对发送者做了判断，所以不需要在接下来的语句中进行判断
                switch (args[0]) {
                    case "block":
                        AsyncUtil.runAsyncTask(new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(target!=null){
                                    //执行添加黑名单操作
                                    try {
                                        BlockCommand.blockPlayer(finalPlayer,target);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    finalPlayer.sendMessage("§c此玩家不在线.");
                                }
                            }
                        });
                        break;
                    case "relieve":
                        AsyncUtil.runAsyncTask(new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(target!=null){
                                    //执行移出黑名单操作
                                    try {
                                        RelieveCommand.relievePlayer(finalPlayer,target);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    finalPlayer.sendMessage("§c此玩家不在线.");
                                }
                            }
                        });
                        break;
                    case "debug":
                        AsyncUtil.runAsyncTask(new BukkitRunnable() {
                            @Override
                            public void run() {
                                String debugString = args[1];
                                switch (debugString){
                                    case "on":
                                        try {
                                            DebugCommand.setDebug(finalPlayer,true);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case "off":
                                        try {
                                            DebugCommand.setDebug(finalPlayer,false);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    default:
                                        finalPlayer.sendMessage("§cUsage: /pc debug <on/off>");
                                        break;
                                }
                            }
                        });
                        break;
                }
                return true;
            }
            target = Bukkit.getPlayerExact(args[0]);
            Player finalPlayer = player;
            AsyncUtil.runAsyncTask(new BukkitRunnable() {
                @Override
                public void run() {
                    if(target != null) {
                        if(sender instanceof Player){
                            //查询目标是否自己在发送者的黑名单内
                            //如果在则显示你已被拉黑
                            if(!PrivateChat.getInstance().loader.getData().contains(finalPlayer.getName())){
                                try {
                                    PrivateChat.getInstance().loader.setValue(finalPlayer.getName(),new ArrayList<String>());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else if(!PrivateChat.getInstance().loader.getData().contains(target.getName())){
                                try {
                                    PrivateChat.getInstance().loader.setValue(target.getName(),new ArrayList<String>());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            boolean can_say = true;
                            for(String name : PrivateChat.getInstance().loader.getData().getStringList(finalPlayer.getName())){
                                if(name.equals(target.getName())){
                                    finalPlayer.sendMessage("§c已拉黑该玩家");
                                    AsyncUtil.cancelAsyncTask(this);
                                    can_say = false;
                                    break;
                                }
                            }
                            for(String name : PrivateChat.getInstance().loader.getData().getStringList(target.getName())){
                                if(name.equals(finalPlayer.getName())){
                                    finalPlayer.sendMessage("§c该玩家已拉黑你");
                                    AsyncUtil.cancelAsyncTask(this);
                                    can_say = false;
                                    break;
                                }
                            }
                            //否则正常执行
                            if(can_say){
                                target.sendMessage("§9".concat(finalPlayer.getName()).concat(" §8-> §f".concat(getMessage(args))));
                                if(!PrivateChat.getInstance().loader.getData().contains(finalPlayer.getName().concat("Debug"))){
                                    try {
                                        PrivateChat.getInstance().loader.setValue(finalPlayer.getName().concat("Debug"),true);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(PrivateChat.getInstance().loader.getData().getBoolean(finalPlayer.getName().concat("Debug"))) {
                                    finalPlayer.sendMessage("§9成功发送私聊信息，如果想关闭这个提醒，请使用/pc debug off");
                                }
                            }
                        }else{
                            target.sendMessage("§9Console §8-> §f".concat(getMessage(args)));
                        }
                    }else {
                        sender.sendMessage("§c此玩家不在线.");
                    }
                }
            });
            return true;
        }
        return false;
    }
    public String getMessage(String[] args){
        final StringBuilder sb = new StringBuilder();
        int length = args.length - 1;
        for(int i = 1;i <= length;i++){
            sb.append(args[i]).append(" ");//获取子命令后玩家所聊天的信息，以免出现英文句子而出现报错
        }
        return sb.toString();
    }
}
