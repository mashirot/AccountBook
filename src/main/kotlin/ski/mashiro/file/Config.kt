package ski.mashiro.ski.mashiro.file

import org.apache.commons.io.FileUtils
import ski.mashiro.AccountBook
import ski.mashiro.ski.mashiro.pojo.Config
import ski.mashiro.ski.mashiro.util.Utils
import java.io.File

class Config {
    companion object {
        lateinit var config : Config
        private val configFile : File = File(AccountBook.configFolder, "Config.yml")
        fun loadConfig() {
            if (!configFile.exists()) {
                configFile.createNewFile()
                return
            }
            config = Utils.yamlMapper.readValue(FileUtils.readFileToString(configFile, "utf-8"), Config::class.java)
        }
        fun saveConfig() {
            if (!configFile.exists()) {
                configFile.createNewFile()
                return
            }
            FileUtils.writeStringToFile(configFile, Utils.yamlMapper.writeValueAsString(config), "utf-8")
        }
    }
}