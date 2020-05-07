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

package net.ree_jp.reefseichi.system.ban.sql

interface IBanHelper {

    fun isBanUser(xuid: String): Boolean

    fun getBanReason(xuid: String): String

    fun setBanUser(xuid: String, reason: String, ban: Boolean)

    fun getAllBanUser(): List<String>
}