package ski.mashiro.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import ski.mashiro.AccountBook
import ski.mashiro.pojo.Deal
import ski.mashiro.util.Utils
import java.util.Vector

class Disburse: SimpleCommand(AccountBook, "-"){
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional: Boolean
        get() = true

    @Handler
    suspend fun disburse(sender: CommandSender, money: Double) {
        if (Utils.isUserInWhitelist(sender.subject!!.id)) {
            return
        }
        if (!Utils.userDealData.containsKey(sender.subject!!.id)) {
            val dealData = Vector<Deal>(25)
            dealData.add(Deal(0 - money))
            Utils.userDealData[sender.subject!!.id] = dealData
            Utils.userBalance[sender.subject!!.id] = Utils.userBalance[sender.subject!!.id]!! - money
            sender.sendMessage("Insert Success")
            return
        }
        Utils.userDealData[sender.subject!!.id]!!.add(Deal(0 - money))
        Utils.userBalance[sender.subject!!.id] = Utils.userBalance[sender.subject!!.id]!! - money
        sender.sendMessage("Insert Success")
    }

    @Handler
    suspend fun disburse(sender: CommandSender, money: Double, reason : String) {
        if (Utils.isUserInWhitelist(sender.subject!!.id)) {
            return
        }
        if (!Utils.userDealData.containsKey(sender.subject!!.id)) {
            val dealData = Vector<Deal>(25)
            dealData.add(Deal(0 - money, reason))
            Utils.userDealData[sender.subject!!.id] = dealData
            Utils.userBalance[sender.subject!!.id] = Utils.userBalance[sender.subject!!.id]!! - money
            sender.sendMessage("Insert Success")
            return
        }
        Utils.userDealData[sender.subject!!.id]!!.add(Deal(0 - money, reason))
        Utils.userBalance[sender.subject!!.id] = Utils.userBalance[sender.subject!!.id]!! - money
        sender.sendMessage("Insert Success")
    }
}