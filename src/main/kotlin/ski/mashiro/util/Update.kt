package ski.mashiro.util

import ski.mashiro.AccountBook
import ski.mashiro.file.Config
import java.net.URL

class Update {
    companion object {
        private val url = URL("https://update.check.mashiro.ski/AccountBook.txt")
        fun checkUpdate() {
            if (!Config.config.enableUpdateCheck) {
                return
            }
            url.openConnection().run {
                getInputStream().run {
                    if (Config.config.version != bufferedReader().readLine()) {
                        AccountBook.logger.info("[AccountBook] 有更新可用，请前往Github下载最新Release")
                        return
                    }
                    AccountBook.logger.info("[AccountBook] 已是最新版本")
                    return
                }
            }
        }
    }
}