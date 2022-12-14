package ski.mashiro.util

import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import ski.mashiro.AccountBook
import ski.mashiro.file.Config
import ski.mashiro.jdbc.Jdbc
import java.util.*
import kotlin.math.absoluteValue

class ThreadManager {
    companion object {
        private val initTime = Calendar.getInstance()
        private val nextCheckoutDay = Calendar.getInstance()
        fun startCoroutines() {
            logResult()
            checkout()
        }
        private fun logResult() {
            CoroutineScope(Dispatchers.Default).launch {
                initTime.set(Calendar.DAY_OF_MONTH, initTime.get(Calendar.DAY_OF_MONTH) + 1)
                initTime.set(Calendar.HOUR_OF_DAY, 0)
                initTime.set(Calendar.MINUTE, 0)
                initTime.set(Calendar.SECOND, 0)
                initTime.set(Calendar.MILLISECOND, 0)
                delay(initTime.timeInMillis - System.currentTimeMillis())
                while (true) {
                    if (Utils.userDealData.isEmpty()) {
                        delay(24 * 60 * 60 * 1000L)
                        continue
                    }
                    for (qq in Utils.userDealData.keys) {
                        val rs = Jdbc.insert(qq)
                        if (rs == null) {
                            AccountBook.logger.info("数据库连接失败")
                            continue
                        }
                        AccountBook.logger.info("用户 $qq, 已成功向数据库写入 ${rs.successNum} 个数据, ${rs.failedNum} 个写入失败")
                    }
                    delay(24 * 60 * 60 * 1000L)
                }
            }
        }
        private fun checkout() {
            CoroutineScope(Dispatchers.Default).launch {
                nextCheckoutDay.set(Calendar.HOUR_OF_DAY, 0)
                nextCheckoutDay.set(Calendar.MINUTE, 0)
                nextCheckoutDay.set(Calendar.SECOND, 0)
                nextCheckoutDay.set(Calendar.MILLISECOND, 0)
                if (nextCheckoutDay.get(Calendar.DAY_OF_MONTH) > Config.config.checkoutDay) {
                    nextCheckoutDay.set(Calendar.MONTH, nextCheckoutDay.get(Calendar.MONTH) + 1)
                    nextCheckoutDay.set(Calendar.DAY_OF_MONTH, Config.config.checkoutDay)
                } else {
                    nextCheckoutDay.set(Calendar.DAY_OF_MONTH, Config.config.checkoutDay)
                }
                delay(nextCheckoutDay.timeInMillis - System.currentTimeMillis())
                while (true) {
                    for (qq in Config.config.whiteList) {
                        val rs = Jdbc.select(30, qq)
                        if (rs == null) {
                            AccountBook.logger.info("用户 $qq, 数据库连接失败或对应表未找到")
                            continue
                        }
                        val bot = Bot.getInstance(Config.config.bot)
                        for (friend in bot.friends) {
                            if (!Config.config.whiteList.contains(friend.id)) {
                                continue
                            }
                            friend.sendMessage("过去 ${rs.days} 天, 支出 ${Utils.currency.format(rs.outMoney.absoluteValue)} 元, 收入 ${Utils.currency.format(rs.inMoney)} 元, 结余 ${Utils.currency.format(rs.totalMoney)} 元")
                        }
                    }
                    delay(30 * 24 * 60 * 60 * 1000L)
                }
            }
        }

    }
}