package net.ree_jp.reefseichi.sqlite

import net.ree_jp.reefseichi.result.UserResult

interface IReefHelper {

    fun isExistsUser(xuid: String): Boolean

    fun isExistsUserByName(name: String): Boolean

    fun getUser(xuid: String): UserResult

    fun getUserByName(name: String): UserResult

    fun getUserByAddress(address: String): UserResult

    fun getUserByDevice(deviceId: String): UserResult

    fun setUser(xuid: String, name: String, address: List<String>, deviceId: List<String>)
}