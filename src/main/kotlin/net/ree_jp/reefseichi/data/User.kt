package net.ree_jp.reefseichi.data

import com.google.gson.Gson

data class User(
    override val xuid: String,
    override val name: String,
    override val address: List<String>,
    override val deviceId: List<String>
) : IUser, DataJson() {

    companion object {

        fun createResult(xuid: String): User {
            return createResult(
                xuid,
                "",
                listOf(),
                listOf()
            )
        }

        fun createResult(xuid: String, name: String, address: List<String>, deviceId: List<String>): User {
            return User(xuid, name, address, deviceId)
        }
    }
}

abstract class DataJson {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}