package ski.mashiro.ski.mashiro.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import ski.mashiro.AccountBook
import ski.mashiro.ski.mashiro.pojo.Deal
import ski.mashiro.ski.mashiro.util.Utils

class Income : SimpleCommand(AccountBook, "+") {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional: Boolean
        get() = true

    @Handler
    suspend fun income(sender: CommandSender, money: Double) {
        Utils.dealData.add(Deal(money))
        Utils.undoFlag = false
        sender.sendMessage("Insert Success")
    }

    @Handler
    suspend fun income(sender: CommandSender, money: Double, reason : String) {
        Utils.dealData.add(Deal(money))
        Utils.undoFlag = false
        sender.sendMessage("Insert Failed")
    }
}