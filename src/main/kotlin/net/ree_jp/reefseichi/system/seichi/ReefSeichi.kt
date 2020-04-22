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

package net.ree_jp.reefseichi.system.seichi

import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.seichi.sqlite.SeichiHelper

class ReefSeichi {

    companion object{
        private lateinit var helper: SeichiHelper

        fun getHelper(): SeichiHelper{
            if (!::helper.isInitialized) {
                helper = SeichiHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
            }
            return helper
        }
    }
}