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
        if (Utils.userData.isEmpty() || !Utils.userData.containsKey(sender.subject!!.id)) {
            sender.sendMessage("Undo Failed, cause by qq not exist in map")
            return
        }
        if (Utils.userData[sender.subject!!.id]!!.isEmpty()) {
            sender.sendMessage("Undo Failed, cause by list is empty")
            return
        }
        Utils.userData[sender.subject!!.id]!!.removeAt(Utils.userData.size - 1)
        sender.sendMessage("Undo Success")
    }

}