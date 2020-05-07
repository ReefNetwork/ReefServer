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

import net.ree_jp.reefseichi.data.User

interface IReefHelper {

    fun isExistsUser(xuid: String): Boolean

    fun isExistsUserByName(name: String): Boolean

    fun getUser(xuid: String): User

    fun getUserByName(name: String): User

    fun getUserByAddress(address: String): User

    fun getUserByDevice(deviceId: String): User

    fun setUser(xuid: String, name: String, address: List<String>, deviceId: List<String>)
}