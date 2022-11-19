package ski.mashiro.file

import net.mamoe.mirai.console.plugin.version
import org.apache.commons.io.FileUtils
import ski.mashiro.AccountBook
import ski.mashiro.util.Utils
import ski.mashiro.pojo.Config
import java.io.File

class Config {
    companion object {
        lateinit var config : Config
        private val configFile : File = File(AccountBook.configFolder, "Config.yml")
        fun loadConfig() {
            if (!configFile.exists()) {
                if (configFile.createNewFile()) {
                    FileUtils.writeStringToFile(configFile, Utils.yamlMapper.writeValueAsString(Config(AccountBook.version.toString(), true)), "utf-8")
                    config = Utils.yamlMapper.readValue(FileUtils.readFileToString(configFile, "utf-8"), Config::class.java)
                }
                return
            }
            config = Utils.yamlMapper.readValue(FileUtils.readFileToString(configFile, "utf-8"), Config::class.java)
        }
        fun saveConfig() {
            if (!configFile.exists()) {
                configFile.createNewFile()
                FileUtils.writeStringToFile(configFile, Utils.yamlMapper.writeValueAsString(Config(AccountBook.version.toString(), true)), "utf-8")
                return
            }
            FileUtils.writeStringToFile(configFile, Utils.yamlMapper.writeValueAsString(config), "utf-8")
        }
    }
}