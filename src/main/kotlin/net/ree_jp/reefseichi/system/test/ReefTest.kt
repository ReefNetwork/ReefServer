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

package net.ree_jp.reefseichi.system.test

import cn.nukkit.Server
import cn.nukkit.plugin.PluginBase

class ReefTest {

    companion object {
        private lateinit var instance: ReefTest

        fun getInstance(): ReefTest {
            if (!::instance.isInitialized) {
                instance = ReefTest()
            }
            return instance
        }

        fun registerListener(plugin: PluginBase) {
            val pm = Server.getInstance().pluginManager
        }
    }
}