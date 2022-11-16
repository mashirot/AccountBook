package ski.mashiro.ski.mashiro.pojo

data class Config(
    val version: String,
    val enableUpdateCheck: Boolean,
    val bot: Long = 12345L,
    val checkoutDay: Int = 25,
    val whiteList: List<Long> = listOf(12345L),
    val mysql : MySQLConfig = MySQLConfig()
)