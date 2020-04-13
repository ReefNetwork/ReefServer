package net.ree_jp.reefseichi.result

data class UserResult(
    override val xuid: String,
    override val name: String,
    override val address: List<String>,
    override val deviceId: List<String>
) : IUserResult {

    companion object {

        fun createResult(xuid: String): UserResult {
            return createResult(xuid, "", listOf(), listOf())
        }

        fun createResult(xuid: String, name: String, address: List<String>, deviceId: List<String>): UserResult {
            return UserResult(xuid, name, address, deviceId)
        }
    }
}