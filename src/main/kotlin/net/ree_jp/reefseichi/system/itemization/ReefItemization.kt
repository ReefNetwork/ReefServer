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

package net.ree_jp.reefseichi.system.itemization

import net.ree_jp.reefseichi.system.itemization.api.ItemizationAPI
import net.ree_jp.reefseichi.system.seichi.ReefSeichi

class ReefItemization {

    companion object {

        private lateinit var instance: ReefSeichi

        fun getInstance(): ReefSeichi {
            if (!::instance.isInitialized) {
                instance = ReefSeichi()
            }
            return instance
        }
    }

    private lateinit var api: ItemizationAPI
}