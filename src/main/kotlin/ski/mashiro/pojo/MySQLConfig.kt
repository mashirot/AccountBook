package ski.mashiro.ski.mashiro.pojo

data class MySQLConfig(
    val url: String = "jdbc:mysql://127.0.0.1:3306/test_db",
    val username: String = "root",
    val password: String = "root"
)
