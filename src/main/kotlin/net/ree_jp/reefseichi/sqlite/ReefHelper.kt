package net.ree_jp.reefseichi.sqlite

import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.data.User
import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

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
            connection = DriverManager.getConnection("jdbc:sqlite:$path/userDB.db")
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

    override fun getUser(xuid: String): User {
        if (!isExistsUser(xuid)) return User.createResult(xuid)

        val stmt = connection.prepareStatement("SELECT * FROM user WHERE xuid = ?")
        stmt.setString(1, xuid)
        return toUser(stmt.executeQuery())
    }

    override fun getUserByName(name: String): User {
        val stmt = connection.prepareStatement("SELECT * FROM user WHERE name = ?")
        stmt.setString(1, name)
        return toUser(stmt.executeQuery())
    }

    override fun getUserByAddress(address: String): User {
        val stmt = connection.prepareStatement("SELECT * FROM user WHERE address = ?")
        stmt.setString(1, "%$address%")
        return toUser(stmt.executeQuery())
    }

    override fun getUserByDevice(deviceId: String): User {
        val stmt = connection.prepareStatement("SELECT * FROM user WHERE deviceId = ?")
        stmt.setString(1, "%$deviceId%")
        return toUser(stmt.executeQuery())
    }

    override fun setUser(xuid: String, name: String, address: List<String>, deviceId: List<String>) {

        try {
            if (isExistsUser(xuid)) {
                val stmt = connection.prepareStatement("UPDATE user SET name = ?, address = ?, deviceId = ? WHERE xuid = ?")
                stmt.setString(1, name)
                stmt.setString(2, address.joinToString(","))
                stmt.setString(3, deviceId.joinToString(","))
                stmt.setString(4, xuid)
                stmt.executeUpdate()
            } else {
                val stmt = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?)")
                stmt.setString(1, xuid)
                stmt.setString(2, name)
                stmt.setString(3, address.joinToString(","))
                stmt.setString(4, deviceId.joinToString(","))
                stmt.executeUpdate()
            }
        } catch (ex: SQLiteException) {
            throw ex
        }
    }

    private fun toUser(result: ResultSet): User{
        val addressList: List<String> = result.getString("address").split(",")
        val deviceIdList: List<String> = result.getString("deviceId").split(",")
        return User.createResult(result.getString("xuid"), result.getString("name"), addressList, deviceIdList)
    }
}