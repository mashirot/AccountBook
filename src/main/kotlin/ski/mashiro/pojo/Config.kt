package ski.mashiro.ski.mashiro.pojo

import net.mamoe.mirai.console.plugin.version
import ski.mashiro.AccountBook

data class Config(
    val version: String = AccountBook.version.toString(),
    val enableUpdateCheck: Boolean = true,
    val whiteList: List<Long> = listOf(12345L)
)