package ski.mashiro.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import ski.mashiro.AccountBook
import ski.mashiro.jdbc.Jdbc
import ski.mashiro.util.Utils
import kotlin.math.absoluteValue

class Inquire: SimpleCommand(AccountBook, "=") {
    @ExperimentalCommandDescriptors
    @ConsoleExperimentalApi
    override val prefixOptional: Boolean
        get() = true

    @Handler
    suspend fun inquire(sender: CommandSender) {
        if (Utils.isUserInWhitelist(sender.subject!!.id)) {
            return
        }
        if (Utils.userBalance.isEmpty() || !Utils.userBalance.containsKey(sender.subject!!.id)) {
            sender.sendMessage("Balance init err")
            return
        }
        sender.sendMessage("余额 ${Utils.currency.format(Utils.userBalance[sender.subject!!.id])} 元")
    }

    @Handler
    suspend fun inquire(sender: CommandSender, days: Int) {
        if (Utils.isUserInWhitelist(sender.subject!!.id)) {
            return
        }
        if (days <= 0) {
            sender.sendMessage("输入有误，天数大于0")
            return
        }
        val rs = Jdbc.select(days, sender.subject!!.id)
        if (rs == null) {
            AccountBook.logger.info("用户 ${sender.subject!!.id}, 数据库连接失败或对应表未找到")
            return
        }
        if (Utils.userDealData.containsKey(sender.subject!!.id)) {
            val deals = Utils.userDealData[sender.subject!!.id]
            if (!deals.isNullOrEmpty()) {
                deals.stream().forEach { deal -> if (deal.money >= 0) rs.inMoney += deal.money else rs.outMoney += deal.money }
                rs.totalMoney = rs.inMoney + rs.outMoney
            }
        }
        sender.sendMessage("过去 ${rs.days} 天, 支出 ${Utils.currency.format(rs.outMoney.absoluteValue)} 元, 收入 ${Utils.currency.format(rs.inMoney)} 元, 结余 ${Utils.currency.format(rs.totalMoney)} 元")
    }
}
