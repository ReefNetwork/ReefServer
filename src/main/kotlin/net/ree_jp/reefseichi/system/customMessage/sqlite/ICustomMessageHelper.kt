package net.ree_jp.reefseichi.system.customMessage.sqlite

interface ICustomMessageHelper {

    fun isExists(xuid: String): Boolean

    fun setTable(xuid: String)

    fun isExistsKey(xuid: String, key: String): Boolean

    fun getMessage(xuid: String, key: String): String

    fun setMessage(xuid: String, key: String, value: String)
}