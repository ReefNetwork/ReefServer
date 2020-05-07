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

package net.ree_jp.reefseichi.system.seichi.sql

import net.ree_jp.reefseichi.system.seichi.data.SeichiData

interface ISeichiHelper {

    fun isExists(xuid: String): Boolean

    fun getData(xuid: String): SeichiData

    fun setData(xuid: String, seichiData: SeichiData)

    fun save()
}