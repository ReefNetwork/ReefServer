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

package net.ree_jp.reefseichi.system.seichi.sqlite

import com.google.gson.Gson
import net.ree_jp.reefseichi.system.seichi.data.SeichiData
import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager

class SeichiHelper(path: String): ISeichiHelper {

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
        val stmt = connection.prepareStatement("SELECT * FROM seichi WHERE xuid = ?")
        stmt.setString(1, xuid)
        return stmt.executeQuery().next()
    }

    override fun getData(xuid: String): SeichiData {
        if (!isExists(xuid)) throw Exception("data not found")

        val stmt = connection.prepareStatement("SELECT data FROM seichi WHERE xuid = ?")
        stmt.setString(1, xuid)
        val json = stmt.executeQuery().getString("data")
        return Gson().fromJson(json, SeichiData::class.java)
    }

    override fun setData(xuid: String, seichiData: SeichiData) {
        val stmt = connection.prepareStatement("REPLACE INTO seichi VALUES (?, ?)")
        stmt.setString(1, xuid)
        stmt.setString(2, seichiData.toJson())
        stmt.execute()
    }

    private fun createTable(){
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS seichi(xuid TEXT NOT NULL PRIMARY KEY ,data TEXT NOT NULL)")
    }
}