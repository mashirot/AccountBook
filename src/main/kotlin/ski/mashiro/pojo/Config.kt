package ski.mashiro.pojo

data class Config(
    val version: String,
    val enableUpdateCheck: Boolean,
    var bot: Long = 12345L,
    var owner: Long = 12345L,
    var checkoutDay: Int = 25,
    val whiteList: MutableList<Long> = mutableListOf(12345L),
    val mysql : MySQLConfig = MySQLConfig()
)