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

package net.ree_jp.reefseichi.system.mywarp

import net.ree_jp.reefseichi.system.mywarp.api.MyWarpAPI
import net.ree_jp.reefseichi.system.mywarp.sql.MyWarpHelper

class ReefMyWarp {

    companion object {

        const val CREATE_PRICE = 1000

        private lateinit var instance: ReefMyWarp

        fun getInstance(): ReefMyWarp {
            if (!::instance.isInitialized) {
                instance = ReefMyWarp()
            }
            return instance
        }
    }

    private lateinit var helper: MyWarpHelper

    private lateinit var api: MyWarpAPI

    fun getHelper(): MyWarpHelper {
        if (!::helper.isInitialized) {
            helper = MyWarpHelper()
        }
        return helper
    }

    fun getAPI(): MyWarpAPI {
        if (!::api.isInitialized) {
            api = MyWarpAPI()
        }
        return api
    }
}