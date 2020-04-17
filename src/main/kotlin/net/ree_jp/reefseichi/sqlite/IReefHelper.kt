package net.ree_jp.reefseichi.sqlite

import net.ree_jp.reefseichi.data.User

interface IReefHelper {

    fun isExistsUser(xuid: String): Boolean

    fun isExistsUserByName(name: String): Boolean

    fun getUser(xuid: String): User

    fun getUserByName(name: String): User

    fun getUserByAddress(address: String): User

    fun getUserByDevice(deviceId: String): User

    fun setUser(xuid: String, name: String, address: List<String>, deviceId: List<String>)
}