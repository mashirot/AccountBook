package ski.mashiro.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.command.isConsole
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import ski.mashiro.AccountBook
import ski.mashiro.file.Config
import ski.mashiro.util.Utils

class Admin: CompositeCommand(
    AccountBook,
    "accountbook",
    secondaryNames = arrayOf("ab"),
) {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional: Boolean
        get() = true

    @SubCommand("add")
    suspend fun addWhitelist(sender: CommandSender, id: Long) {
        if (sender.subject!!.id != Config.config.owner) {
            sender.sendMessage("无权限")
            return
        }
        if (Config.config.whiteList.contains(id)) {
            sender.sendMessage("重复添加")
            return
        }
        Config.config.whiteList.add(id)
        sender.sendMessage("添加成功")
    }
    @SubCommand("del")
    suspend fun delWhitelist(sender: CommandSender, id: Long) {
        if (sender.subject!!.id != Config.config.owner) {
            sender.sendMessage("无权限")
            return
        }
        if (!Config.config.whiteList.contains(id)) {
            sender.sendMessage("不存在此用户")
            return
        }
        Config.config.whiteList.remove(id)
        sender.sendMessage("移除成功")
    }
    @SubCommand("list")
    suspend fun listWhitelist(sender: CommandSender) {
        if (sender.subject!!.id != Config.config.owner) {
            sender.sendMessage("无权限")
            return
        }
        if (Config.config.whiteList.isEmpty()) {
            sender.sendMessage("白名单为空")
            return
        }
        var whitelist = "白名单：\n"
        Config.config.whiteList.stream().forEach { qq -> whitelist += if (qq != Config.config.whiteList.last()) "$qq\n" else "$qq" }
        sender.sendMessage(whitelist)
    }
    @SubCommand("submit")
    suspend fun submit(sender: CommandSender) {
        val qq = sender.subject!!.id
        if (!Config.config.whiteList.contains(qq)) {
            sender.sendMessage("无权限")
            return
        }
        if (!Utils.userData.contains(qq)) {
            sender.sendMessage("暂无数据需要写入")
            return
        }
        val rs = Utils.insert(qq)
        if (rs == null) {
            AccountBook.logger.info("数据库连接失败")
            return
        }
        sender.sendMessage("已成功向数据库写入 ${rs.successNum} 个数据, ${rs.failedNum} 个写入失败")
    }
    @SubCommand("reload")
    suspend fun reload(sender: CommandSender) {
        if (sender.isConsole()) {
            Config.loadConfig()
            sender.sendMessage("重载成功")
            return
        }
        if (sender.subject!!.id != Config.config.owner) {
            sender.sendMessage("无权限")
            return
        }
        Config.loadConfig()
        sender.sendMessage("重载成功")
    }
}