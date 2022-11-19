package ski.mashiro

import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import ski.mashiro.command.*
import ski.mashiro.file.Config
import ski.mashiro.util.ThreadManager
import ski.mashiro.util.Update

object AccountBook : KotlinPlugin(
    JvmPluginDescription(
        id = "ski.mashiro.AccountBook",
        name = "AccountBook",
        version = "1.0.0",
    ) {
        author("FeczIne")
    }
) {

    override fun onEnable() {
        Config.loadConfig()
        ThreadManager.startCoroutines()
        CommandManager.registerCommand(Disburse())
        CommandManager.registerCommand(Income())
        CommandManager.registerCommand(Inquire())
        CommandManager.registerCommand(Undo())
        CommandManager.registerCommand(Admin())
        AbstractPermitteeId.AnyFriend.permit(parentPermission)
        Update.checkUpdate()
        logger.info("[AccountBook] 加载成功")
    }

    override fun onDisable() {
        Config.saveConfig()
        logger.info("[AccountBook] 已卸载")
    }
}