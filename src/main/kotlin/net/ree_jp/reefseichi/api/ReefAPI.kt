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
import net.ree_jp.reefseichi.sql.MoreDataHelper
import net.ree_jp.reefseichi.sql.ReefHelper

class ReefAPI(private val helper: ReefHelper) : IReefAPI {

    companion object {

        private lateinit var instance: ReefAPI

        private const val IS_VIP = "is_vip"

        fun getInstance(): ReefAPI {
            if (!::instance.isInitialized) {
                instance = ReefAPI(ReefHelper.getInstance())
            }
            return instance
        }
    }

    override fun isExists(xuid: String): Boolean {
        return helper.isExistsUser(xuid)
    }

    override fun getUser(xuid: String): User {
        if (!isExists(xuid)) return User.createResult(xuid)

        return helper.getUser(xuid)
    }

    override fun isVip(xuid: String): Boolean {
        val helper = MoreDataHelper.getInstance()

        if (!helper.isExistsKey(xuid, IS_VIP)) return false
        val vip = helper.getValue(xuid, IS_VIP)
        return vip == "1"
    }

    override fun setVip(xuid: String, bool: Boolean) {
        val helper = MoreDataHelper.getInstance()
        val vip = if (bool) "1" else "0"

        helper.setValue(xuid, IS_VIP, vip)
    }

}