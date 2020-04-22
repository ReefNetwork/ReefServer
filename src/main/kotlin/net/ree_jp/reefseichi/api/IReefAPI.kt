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

package net.ree_jp.reefseichi.api

import net.ree_jp.reefseichi.data.User

interface IReefAPI {

    fun isExists(xuid: String): Boolean

    fun register(xuid: String)

    fun getXuid(name: String): String

    fun getName(xuid: String): String

    fun setName(xuid: String)

    fun addAddress(xuid: String, address: String)

    fun addDevice(xuid: String, deviceId: String)

    fun getUser(xuid: String): User
}