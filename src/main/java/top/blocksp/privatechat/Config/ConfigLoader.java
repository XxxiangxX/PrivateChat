package top.blocksp.privatechat.Config;

import org.bukkit.configuration.file.YamlConfiguration;
import top.blocksp.privatechat.PrivateChat;

import java.io.File;
import java.io.IOException;

public final class ConfigLoader {
    private YamlConfiguration data;
    private final File data_file = new File(PrivateChat.getInstance().getDataFolder(),"data.yml");

    public YamlConfiguration getData() {
        return data;
    }
    public void setValue(String path,Object value) throws IOException {
        data.set(path,value);
        data.save(data_file);
    }

    public void createConfig(){
        if(!data_file.getParentFile().exists()){
            data_file.getParentFile().mkdirs();
        }
        if(!data_file.exists()){
            PrivateChat.getInstance().saveResource("data.yml",false);
        }
        data = YamlConfiguration.loadConfiguration(data_file);
    }
}
