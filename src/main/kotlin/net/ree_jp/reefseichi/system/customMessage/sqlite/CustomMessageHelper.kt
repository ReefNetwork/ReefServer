package net.ree_jp.reefseichi.system.customMessage.sqlite

import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager

class CustomMessageHelper(path: String) : ICustomMessageHelper {

    private var connection: Connection

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:$path/customMessage.db")
        } catch (ex: SQLiteException) {
            throw ex
        }
    }

    override fun isExists(xuid: String): Boolean {
        return connection.createStatement().executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = '$xuid'").next()
    }

    override fun setTable(xuid: String) {
        if (isExists(xuid)) return

        connection.createStatement()
            .execute("CREATE TABLE IF NOT EXISTS '$xuid' (key TEXT NOT NULL PRIMARY KEY ,value TEXT NOT NULL)")
    }

    override fun isExistsKey(xuid: String, key: String): Boolean {
        if (!isExists(xuid)) return false

        val stmt = connection.prepareStatement("SELECT * FROM '$xuid' WHERE key = ?")
        stmt.setString(1, key)
        return stmt.executeQuery().next()
    }

    override fun getMessage(xuid: String, key: String): String {
        if (!isExistsKey(xuid, key)) return ""

        val stmt = connection.prepareStatement("SELECT value FROM '$xuid' WHERE key = ?")
        stmt.setString(1, key)
        return stmt.executeQuery().getString("value")
    }

    override fun setMessage(xuid: String, key: String, value: String) {
        if (!isExists(xuid)) setTable(xuid)

        val stmt = connection.prepareStatement("REPLACE INTO '$xuid' VALUES (?, ?)")
        stmt.setString(1, key)
        stmt.setString(2, value)
        stmt.execute()
    }
}