package net.ree_jp.reefseichi.api.ban

interface IReefBanAPI {

    fun isBan(xuid: String): Boolean

    fun isBan(xuid: String, address: String, deviceId: String): Boolean

    fun getBanXuid(xuid: String, address: String, deviceId: String): String

    fun setBan(xuid: String)

    fun setBan(xuid: String, reason: String)

    fun setBan(xuid: String, isBan: Boolean)

    fun setBan(xuid: String, reason: String, isBan: Boolean)

    fun getAllBanUser(): List<String>
}