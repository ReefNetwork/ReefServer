package net.ree_jp.reefseichi.data

data class User(
    override val xuid: String,
    override val name: String,
    override val address: List<String>,
    override val deviceId: List<String>
) : IUser {

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