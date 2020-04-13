package net.ree_jp.reefseichi.system.ban.sqlite

interface IBanHelper {

    fun isBanUser(xuid: String): Boolean

    fun getBanReason(xuid: String): String

    fun setBanUser(xuid: String, reason: String, ban: Boolean)

    fun getAllBanUser(): List<String>
}