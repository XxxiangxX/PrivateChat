package top.blocksp.privatechat.Util;

import org.bukkit.Bukkit;
import top.blocksp.privatechat.PrivateChat;

import java.util.HashMap;

public final class AsyncUtil {
    private AsyncUtil(){}
    private static final HashMap<Runnable,Integer> taskIdList = new HashMap<>();
    public static HashMap<Runnable,Integer> getTaskIdList(){return taskIdList;}
    public static void runAsyncTask(Runnable runnable){
        taskIdList.put(runnable,Bukkit.getScheduler().runTaskAsynchronously(PrivateChat.getInstance(),runnable).getTaskId());
    }
    public static void cancelAsyncTask(Runnable runnable){
        int taskId = taskIdList.get(runnable);
        Bukkit.getScheduler().cancelTask(taskId);
        taskIdList.remove(runnable);
    }
}
