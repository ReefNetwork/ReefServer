package net.ree_jp.reefseichi.api

import net.ree_jp.reefseichi.data.User

interface IReefAPI {

    fun isExists(xuid: String): Boolean

    fun register(xuid: String)

    fun getXuid(name: String): String

    fun getName(xuid: String): String

    fun setName(xuid: String)

    fun addAddress(xuid: String, address: String)

    fun addDevice(xuid: String, deviceId: String)

    fun getUser(xuid: String): User
}