package ski.mashiro.ski.mashiro.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import ski.mashiro.ski.mashiro.pojo.Deal

class Utils {
    companion object {
        val dealData : MutableList<Deal> = ArrayList(25)
        val yamlMapper: ObjectMapper = YAMLMapper().registerModule(kotlinModule())
//        val sqlSessionFactory = SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"))
        var undoFlag: Boolean = false
    }
}