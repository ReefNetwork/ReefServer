package net.ree_jp.reefseichi.result

interface IUserResult {

    val xuid: String
    val name: String
    val address: List<String>
    val deviceId: List<String>
}