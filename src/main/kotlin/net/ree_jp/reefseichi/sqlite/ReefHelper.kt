package net.ree_jp.reefseichi.sqlite

import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.result.UserResult
import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager

class ReefHelper(path: String) : IReefHelper {

    companion object {

        private lateinit var instance: ReefHelper

        fun getInstance(): ReefHelper {
            if (!::instance.isInitialized) {
                instance = ReefHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
            }
            return instance
        }
    }

    private var connection: Connection

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:$path\\userDB.db")
            connection.createStatement()
                .execute("CREATE TABLE IF NOT EXISTS user (xuid TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, address TEXT NOT NULL, deviceId TEXT NOT NULL)")
        } catch (ex: SQLiteException) {
            throw ex
        }
    }

    override fun isExistsUser(xuid: String): Boolean {
        val stmt = connection.prepareStatement("SELECT * FROM user WHERE xuid = ?")
        stmt.setString(1, xuid)
        return stmt.executeQuery().next()
    }

    override fun isExistsUserByName(name: String): Boolean {
        val stmt = connection.prepareStatement("SELECT * FROM user WHERE name = ?")
        stmt.setString(1, name)
        return stmt.executeQuery().next()
    }

    override fun getUser(xuid: String): UserResult {
        if (!isExistsUser(xuid)) return UserResult.createResult(xuid)

        val stmt = connection.prepareStatement("SELECT * FROM user WHERE xuid = ?")
        stmt.setString(1, xuid)
        val result = stmt.executeQuery()
        val address: List<String> = result.getString("address").split(",")
        val deviceId: List<String> = result.getString("deviceId").split(",")
        return UserResult.createResult(xuid, result.getString("name"), address, deviceId)
    }

    override fun getUserByName(name: String): UserResult {
        val stmt = connection.prepareStatement("SELECT * FROM user WHERE name = ?")
        stmt.setString(1, name)
        val result = stmt.executeQuery()
        val address: List<String> = result.getString("address").split(",")
        val deviceId: List<String> = result.getString("deviceId").split(",")
        return UserResult.createResult(result.getString("xuid"), name, address, deviceId)
    }

    override fun getUserByAddress(address: String): UserResult {
        val stmt = connection.prepareStatement("SELECT * FROM user WHERE address = ?")
        stmt.setString(1, "%$address%")
        val result = stmt.executeQuery()
        val addressList: List<String> = result.getString("address").split(",")
        val deviceIdList: List<String> = result.getString("deviceId").split(",")
        return UserResult.createResult(result.getString("xuid"), result.getString("name"), addressList, deviceIdList)
    }

    override fun getUserByDevice(deviceId: String): UserResult {
        val stmt = connection.prepareStatement("SELECT * FROM user WHERE deviceId = ?")
        stmt.setString(1, "%$deviceId%")
        val result = stmt.executeQuery()
        val addressList: List<String> = result.getString("address").split(",")
        val deviceIdList: List<String> = result.getString("deviceId").split(",")
        return UserResult.createResult(result.getString("xuid"), result.getString("name"), addressList, deviceIdList)
    }

    override fun setUser(xuid: String, name: String, address: List<String>, deviceId: List<String>) {

        try {
            val stmt = if (isExistsUser(xuid)) {
                connection.prepareStatement("UPDATE user SET name = ?, address = ?, deviceId = ?, WHERE xuid = ?")
            } else {
                connection.prepareStatement("INSERT INTO user VALUES (xuid = ?, name = ?, address = ?, deviceId = ?)")
            }
            stmt.setString(1, name)
            stmt.setString(2, address.joinToString(","))
            stmt.setString(3, deviceId.joinToString(","))
            stmt.setString(4, xuid)
            stmt.executeUpdate()
        } catch (ex: SQLiteException) {
            throw ex
        }
    }
}