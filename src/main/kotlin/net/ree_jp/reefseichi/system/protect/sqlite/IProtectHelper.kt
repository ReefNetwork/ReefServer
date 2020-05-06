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

import net.ree_jp.reefseichi.system.protect.data.LandData

interface IProtectHelper {

    fun isExists(xuid: String, id: String): Boolean

    fun getLand(xuid: String, id: String): LandData

    fun getAll(): List<LandData>

    fun getAll(xuid: String): List<LandData>

    fun setLand(xuid: String, id: String, land: LandData)

    fun removeLand(xuid: String, id: String)
}