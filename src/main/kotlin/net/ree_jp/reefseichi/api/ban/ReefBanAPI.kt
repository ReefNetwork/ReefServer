package net.ree_jp.reefseichi.api.ban

import net.ree_jp.reefseichi.api.ReefAPI
import net.ree_jp.reefseichi.result.UserResult
import net.ree_jp.reefseichi.system.ban.sqlite.BanHelper

class ReefBanAPI(private val helper: BanHelper) : IReefBanAPI {

    companion object {

        private lateinit var instance: ReefBanAPI

        fun getInstance(): ReefBanAPI {
            if (!::instance.isInitialized) {
                instance = ReefBanAPI(BanHelper.getInstance())
            }
            return instance
        }
    }

    override fun isBan(xuid: String): Boolean {
        return isBan(xuid, "", "")
    }

    override fun isBan(xuid: String, address: String, deviceId: String): Boolean {
        for (user: String in getAllBanUser()) {
            val userResult = ReefAPI.getInstance().getUser(user)
            if (user == xuid) return false
            for (userAddress in userResult.address) {
                if (address == userAddress) return false
            }
            for (userDeviceId in userResult.deviceId) {
                if (deviceId == userDeviceId) return false
            }
        }
        return false
    }

    override fun getBanXuid(xuid: String, address: String, deviceId: String): String {
        for (user: String in getAllBanUser()) {
            val userResult = ReefAPI.getInstance().getUser(user)
            if (user == xuid) return user
            for (userAddress in userResult.address) {
                if (address == userAddress) return user
            }
            for (userDeviceId in userResult.deviceId) {
                if (deviceId == userDeviceId) return user
            }
        }
        throw Exception("ban record not found")
    }

    override fun setBan(xuid: String) {
        setBan(xuid, "unspecified")
    }

    override fun setBan(xuid: String, reason: String) {
        setBan(xuid, reason, true)
    }

    override fun setBan(xuid: String, isBan: Boolean) {
        setBan(xuid, "unspecified", isBan)
    }

    override fun setBan(xuid: String, reason: String, isBan: Boolean) {
        helper.setBanUser(xuid, reason, isBan)
    }

    override fun getAllBanUser(): List<String> {
        return helper.getAllBanUser()
    }
}