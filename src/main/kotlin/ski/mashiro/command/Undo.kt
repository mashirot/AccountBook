package ski.mashiro.ski.mashiro.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import ski.mashiro.AccountBook
import ski.mashiro.ski.mashiro.util.Utils

class Undo : SimpleCommand(AccountBook, "undo") {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional: Boolean
        get() = true

    @Handler
    suspend fun undo(sender: CommandSender) {
        if (Utils.dealData.isEmpty()) {
            sender.sendMessage("Undo Failed")
            return
        }
        if (!Utils.undoFlag) {
            Utils.dealData.removeAt(Utils.dealData.size - 1)
            sender.sendMessage("Undo Success")
            Utils.undoFlag = true
        }
    }

}