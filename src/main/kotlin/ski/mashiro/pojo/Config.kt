package ski.mashiro.pojo

data class Config(
    val version: String,
    val enableUpdateCheck: Boolean,
    val bot: Long = 12345L,
    val owner: Long = 12345L,
    val checkoutDay: Int = 25,
    val whiteList: MutableList<Long> = mutableListOf(12345L),
    val mysql : MySQLConfig = MySQLConfig()
)