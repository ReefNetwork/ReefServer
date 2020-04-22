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

interface ISqliteHelper {

    fun isExists(xuid: String): Boolean

    fun setTable(xuid: String)

    fun isExistsKey(xuid: String, key: String): Boolean

    fun getMessage(xuid: String, key: String): String

    fun setMessage(xuid: String, key: String, value: String)
}