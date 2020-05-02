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

package net.ree_jp.reefseichi.sqlite

import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager

open class SqliteHelper(path: String) {

    private var connection: Connection

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
            .execute("CREATE TABLE IF NOT EXISTS '$xuid' (key TEXT NOT NULL PRIMARY KEY ,value NOT NULL)")
    }

    fun isExistsKey(xuid: String, key: String): Boolean {
        if (!isExists(xuid)) return false

        val stmt = connection.prepareStatement("SELECT * FROM '$xuid' WHERE key = ?")
        stmt.setString(1, key)
        return stmt.executeQuery().next()
    }

    fun <T> getValue(xuid: String, key: String, type: Class<T>): T {
        if (!isExistsKey(xuid, key)) throw Exception("data not found")

        val stmt = connection.prepareStatement("SELECT value FROM '$xuid' WHERE key = ?")
        stmt.setString(1, key)
        return stmt.executeQuery().getObject("value", type)
    }

    fun <V> setValue(xuid: String, key: String, value: V) {
        if (!isExists(xuid)) setTable(xuid)

        val stmt = connection.prepareStatement("REPLACE INTO '$xuid' VALUES (?, ?)")
        stmt.setString(1, key)
        stmt.setObject(2, value)
        stmt.execute()
    }
}