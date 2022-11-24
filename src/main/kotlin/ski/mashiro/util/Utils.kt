package ski.mashiro.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import ski.mashiro.file.Config
import ski.mashiro.pojo.InsertResult
import ski.mashiro.pojo.SelectResult
import ski.mashiro.pojo.Deal
import java.sql.Date
import java.sql.DriverManager
import java.text.NumberFormat
import java.util.*

class Utils {
    companion object {
        val userData: MutableMap<Long, Vector<Deal>> = HashMap(5)
        val yamlMapper: ObjectMapper = YAMLMapper().registerModule(kotlinModule())
        val numFormat = NumberFormat.getCurrencyInstance()


        fun insert(qq: Long) : InsertResult? {
            val dealData = userData[qq] ?: return null
            Class.forName("com.mysql.cj.jdbc.Driver")
            val sql = "insert into tb_$qq (money, reason, date) values (?, ?, ?);"
            val createSql = "CREATE TABLE IF NOT EXISTS `tb_$qq` (\n" +
                    "  `money` double NOT NULL,\n" +
                    "  `reason` varchar(50) NOT NULL,\n" +
                    "  `date` date NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;"
            var successNum = 0
            var failedNum = 0
            try {
                val connection = DriverManager.getConnection(
                    Config.config.mysql.url,
                    Config.config.mysql.username,
                    Config.config.mysql.password
                )
                connection.use {
                    val createStatement = connection.prepareStatement(createSql)
                    createStatement.use { createStatement.executeUpdate() }
                    val prepareStatement = connection.prepareStatement(sql)
                    prepareStatement.use {
                        for (deal in dealData) {
                            prepareStatement.setDouble(1, deal.money)
                            prepareStatement.setString(2, deal.reason)
                            prepareStatement.setDate(3, Date(deal.date.time))
                            if (prepareStatement.executeUpdate() != 1) {
                                failedNum++
                            } else {
                                successNum++
                            }
                        }
                    }
                    userData.clear()
                }
                return InsertResult(successNum, failedNum)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun select(days: Int, qq: Long): SelectResult? {
            Class.forName("com.mysql.cj.jdbc.Driver")
            val sql = "select money from tb_$qq where date = ?;"
            val createSql = "CREATE TABLE IF NOT EXISTS `tb_$qq` (\n" +
                    "  `money` double NOT NULL,\n" +
                    "  `reason` varchar(50) NOT NULL,\n" +
                    "  `date` date NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;"
            val initTime = Calendar.getInstance()
            initTime.set(Calendar.HOUR_OF_DAY, 0)
            initTime.set(Calendar.MINUTE, 0)
            initTime.set(Calendar.SECOND, 0)
            initTime.set(Calendar.MILLISECOND, 0)
            var inMoney = 0.0
            var outMoney = 0.0
            try {
                val connection = DriverManager.getConnection(
                    Config.config.mysql.url,
                    Config.config.mysql.username,
                    Config.config.mysql.password
                )
                connection.use {
                    val createStatement = connection.prepareStatement(createSql)
                    createStatement.use { createStatement.executeUpdate() }
                    val prepareStatement = connection.prepareStatement(sql)
                    prepareStatement.use {
                        for (i in 1..days) {
                            prepareStatement.setDate(1, Date(initTime.time.time))
                            val result = prepareStatement.executeQuery()
                            result.use {
                                while (result.next()) {
                                    val money = result.getDouble("money")
                                    if (money >= 0) {
                                        inMoney += money
                                        continue
                                    }
                                    outMoney += money
                                }
                            }
                            initTime.set(Calendar.DAY_OF_MONTH, initTime.get(Calendar.DAY_OF_MONTH) - 1)
                        }
                    }
                }
                return SelectResult(days, inMoney, outMoney, outMoney + inMoney)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun isUserInWhitelist(qq: Long): Boolean = !Config.config.whiteList.contains(qq)

    }
}