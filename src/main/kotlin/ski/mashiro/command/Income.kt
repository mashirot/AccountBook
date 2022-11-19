package ski.mashiro.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import ski.mashiro.AccountBook
import ski.mashiro.pojo.Deal
import ski.mashiro.util.Utils
import java.util.*

class Income: SimpleCommand(AccountBook, "+") {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional: Boolean
        get() = true

    @Handler
    suspend fun income(sender: CommandSender, money: Double) {
        if (Utils.isUserInWhitelist(sender.subject!!.id)) {
            return
        }
        if (!Utils.userData.containsKey(sender.subject!!.id)) {
            val dealData = Vector<Deal>(25)
            dealData.add(Deal(money))
            Utils.userData[sender.subject!!.id] = dealData
            sender.sendMessage("Insert Success")
            return
        }
        Utils.userData[sender.subject!!.id]!!.add(Deal(money))
        sender.sendMessage("Insert Success")
    }

    @Handler
    suspend fun income(sender: CommandSender, money: Double, reason : String) {
        if (Utils.isUserInWhitelist(sender.subject!!.id)) {
            return
        }
        if (!Utils.userData.containsKey(sender.subject!!.id)) {
            val dealData = Vector<Deal>(25)
            dealData.add(Deal(money, reason))
            Utils.userData[sender.subject!!.id] = dealData
            sender.sendMessage("Insert Success")
            return
        }
        Utils.userData[sender.subject!!.id]!!.add(Deal(money, reason))
        sender.sendMessage("Insert Success")
    }
}