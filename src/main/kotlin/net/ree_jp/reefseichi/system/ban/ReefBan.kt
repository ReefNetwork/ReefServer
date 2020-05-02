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

package net.ree_jp.reefseichi.system.ban

import cn.nukkit.Server
import cn.nukkit.plugin.PluginBase
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.ban.event.BanListener
import net.ree_jp.reefseichi.system.ban.sqlite.BanHelper

class ReefBan {

    companion object{

        private lateinit var helper: BanHelper

        fun getHelper(): BanHelper {
            if (!::helper.isInitialized) {
                helper = BanHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
            }
            return helper
        }

        fun registerListener(plugin: PluginBase) {
            val pm = Server.getInstance().pluginManager

            pm.registerEvents(BanListener(), plugin)
        }
    }
}