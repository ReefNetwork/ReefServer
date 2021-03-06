/*
 * RRRRRR                         jjj
 * RR   RR   eee    eee               pp pp
 * RRRRRR  ee   e ee   e _____    jjj ppp  pp
 * RR  RR  eeeee  eeeee           jjj pppppp
 * RR   RR  eeeee  eeeee          jjj pp
 *                              jjjj  pp
 *
 * Copyright (c) 2020. Ree-jp.  All Rights Reserved.
 */

package net.ree_jp.reefseichi.sql

import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager

open class SqliteHelper(path: String) {

    protected var connection: Connection

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:$path")
        } catch (ex: SQLiteException) {
            throw ex
        }
    }

    fun isExists(xuid: String): Boolean {
        return connection.createStatement()
            .executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = '$xuid'").next()
    }

    fun setTable(xuid: String) {
        if (isExists(xuid)) return

        connection.createStatement()
            .execute("CREATE TABLE IF NOT EXISTS '$xuid' (key TEXT NOT NULL PRIMARY KEY ,value TEXT NOT NULL)")
    }

    fun isExistsKey(xuid: String, key: String): Boolean {
        if (!isExists(xuid)) return false

        val stmt = connection.prepareStatement("SELECT * FROM '$xuid' WHERE key = ?")
        stmt.setString(1, key)
        return stmt.executeQuery().next()
    }

    fun getValue(xuid: String, key: String): String {
        if (!isExistsKey(xuid, key)) throw Exception("データが存在しません")

        val stmt = connection.prepareStatement("SELECT value FROM '$xuid' WHERE key = ?")
        stmt.setString(1, key)
        return stmt.executeQuery().getString("value")
    }

    fun <V> setValue(xuid: String, key: String, value: V) {
        if (!isExists(xuid)) setTable(xuid)

        val stmt = connection.prepareStatement("REPLACE INTO '$xuid' VALUES (?, ?)")
        stmt.setString(1, key)
        stmt.setObject(2, value)
        stmt.execute()
    }

    fun deleteValue(xuid: String, key: String) {
        if (!isExists(xuid)) throw Exception("データが存在しません")

        val stmt = connection.prepareStatement("DELETE FROM '$xuid' WHERE key = ?")
        stmt.setString(1, key)
        stmt.execute()
    }
}