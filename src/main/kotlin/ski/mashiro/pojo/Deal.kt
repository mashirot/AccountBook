package ski.mashiro.pojo

import java.util.Date

data class Deal(
    val money : Double,
    val reason : String = "No reason",
    val date: Date = Date()
) {
}