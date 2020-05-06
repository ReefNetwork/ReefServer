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

package net.ree_jp.reefseichi.system.protect.sqlite

import com.google.gson.Gson
import net.ree_jp.reefseichi.system.protect.data.LandData
import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class ProtectHelper(path: String) : IProtectHelper {

    private val connection: Connection

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:$path/protect.db")
            createTable()
        } catch (ex: SQLiteException) {
            throw ex
        }
    }

    override fun isExists(xuid: String, id: String): Boolean {
        val stmt = connection.prepareStatement("SELECT * FROM land WHERE xuid = ? AND id = ?")
        stmt.setString(1, xuid)
        stmt.setString(2, id)
        return stmt.executeQuery().next()
    }

    override fun getLand(xuid: String, id: String): LandData {
        val stmt = connection.prepareStatement("SELECT data FROM land WHERE xuid = ? AND id = ?")
        stmt.setString(1, xuid)
        stmt.setString(2, id)
        return Gson().fromJson(stmt.executeQuery().getString("data"), LandData::class.java)
    }

    override fun getAll(): List<LandData> {
        return resultToLandData(connection.createStatement().executeQuery("SELECT data FROM land"))
    }

    override fun getAll(xuid: String): List<LandData> {
        val stmt = connection.prepareStatement("SELECT data FROM land WHERE xuid = ?")
        stmt.setString(1, xuid)
        return resultToLandData(stmt.executeQuery())
    }

    override fun setLand(xuid: String, id: String, land: LandData) {
        val json = Gson().toJson(land)
        val stmt = connection.prepareStatement("REPLACE INTO land VALUES (?, ?, ?)")
        stmt.setString(1, xuid)
        stmt.setString(2, id)
        stmt.setString(3, json)
        stmt.execute()
    }

    override fun removeLand(xuid: String, id: String) {
        val stmt = connection.prepareStatement("DELETE FROM land WHERE xuid = ? AND id = ?")
        stmt.setString(1, xuid)
        stmt.setString(2, id)
        stmt.execute()
    }

    private fun resultToLandData(result: ResultSet): List<LandData> {
        val gson = Gson()
        val list = mutableListOf<LandData>()
        while (result.next()) list.add(gson.fromJson(result.getString("data"), LandData::class.java))
        return list
    }

    private fun createTable() {
        connection.createStatement()
            .execute("CREATE TABLE IF NOT EXISTS land (xuid TEXT NOT NULL ,id TEXT NOT NULL ,data TEXT NOT NULL ,PRIMARY KEY (xuid ,id))")
    }
}