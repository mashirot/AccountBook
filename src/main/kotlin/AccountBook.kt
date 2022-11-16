package ski.mashiro

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object AccountBook : KotlinPlugin(
    JvmPluginDescription(
        id = "ski.mashiro.AccountBook",
        name = "AccountBook",
        version = "0.1.0",
    ) {
        author("FeczIne")
        dependsOn("net.mamoe.chat-command:[0.1.0,0.5.1]?")
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
    }
}