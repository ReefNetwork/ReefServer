package net.ree_jp.reefseichi.system.ban.sqlite

import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager

class BanHelper(path: String) : IBanHelper {

    private var connection: Connection

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:$path/banDB.db")
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS ban (xuid TEXT NOT NULL PRIMARY KEY, reason TEXT)")
        } catch (ex: SQLiteException) {
            throw ex
        }
    }

    override fun isBanUser(xuid: String): Boolean {
        val stmt = connection.prepareStatement("SELECT * FROM ban WHERE xuid = ?")
        stmt.setString(1, xuid)
        return stmt.executeQuery().next()
    }

    override fun getBanReason(xuid: String): String {
        if (!isBanUser(xuid)) return ""

        val stmt = connection.prepareStatement("SELECT reason FROM ban WHERE xuid = ?")
        stmt.setString(1, xuid)
        val result = stmt.executeQuery()
        return result.getString("reason")
    }

    override fun setBanUser(xuid: String, reason: String, ban: Boolean) {
        if (isBanUser(xuid)) {
            if (ban) {
                val stmt = connection.prepareStatement("UPDATE ban SET reason = ? WHERE xuid = ?")
                stmt.setString(1, reason)
                stmt.setString(2, xuid)
                stmt.execute()
            } else {
                val stmt = connection.prepareStatement("DELETE FROM ban WHERE xuid = ?")
                stmt.setString(1, xuid)
                stmt.execute()
            }
        } else {
            if (ban) {
                val stmt = connection.prepareStatement("INSERT INTO ban VALUES (?, ?)")
                stmt.setString(1, xuid)
                stmt.setString(2, reason)
                stmt.execute()
            }
        }
    }

    override fun getAllBanUser(): List<String> {
        val result = connection.createStatement().executeQuery("SELECT xuid FROM ban")
        val list = mutableListOf<String>()
        while (result.next()) list.add(result.getString("xuid"))
        return list
    }
}