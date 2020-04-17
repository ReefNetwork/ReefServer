package net.ree_jp.reefseichi.data

interface IUser {

    val xuid: String
    val name: String
    val address: List<String>
    val deviceId: List<String>
}