package ski.mashiro.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ski.mashiro.file.Config
import ski.mashiro.jdbc.Jdbc
import ski.mashiro.pojo.Deal
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class Utils {
    companion object {
        val userDealData: MutableMap<Long, Vector<Deal>> = HashMap(5)
        val userBalance: MutableMap<Long, Double> = HashMap(5)
        val yamlMapper: ObjectMapper = YAMLMapper().registerModule(kotlinModule())
        val currency: NumberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA)

        fun getEachBalance() {
            CoroutineScope(Dispatchers.Default).launch {
                val whiteList = Config.config.whiteList
                if (whiteList.isEmpty()) {
                    return@launch
                }
                whiteList.stream().forEach { qq ->
                    val selectResult = Jdbc.select(-1, qq)
                    userBalance[qq] = selectResult?.totalMoney ?: 0.0
                }
            }
        }

        fun isUserInWhitelist(qq: Long): Boolean = !Config.config.whiteList.contains(qq)

    }
}