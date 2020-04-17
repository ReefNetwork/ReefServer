package net.ree_jp.reefseichi.api

import net.ree_jp.reefseichi.data.User
import net.ree_jp.reefseichi.sqlite.ReefHelper

class ReefAPI(private val helper: ReefHelper): IReefAPI {

    companion object {

        private lateinit var instance: ReefAPI

        fun getInstance(): ReefAPI {
            if (!::instance.isInitialized) {
                instance = ReefAPI(ReefHelper.getInstance())
            }
            return instance
        }
    }

    override fun isExists(xuid: String): Boolean {
        return helper.isExistsUser(xuid)
    }

    override fun register(xuid: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getXuid(name: String): String {
        TODO("Not yet implemented")
    }

    override fun getName(xuid: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setName(xuid: String) {
        TODO("Not yet implemented")
    }

    override fun addAddress(xuid: String, address: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDevice(xuid: String, deviceId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(xuid: String): User {
        if (!isExists(xuid)) return User.createResult(xuid)

        return helper.getUser(xuid)
    }
}