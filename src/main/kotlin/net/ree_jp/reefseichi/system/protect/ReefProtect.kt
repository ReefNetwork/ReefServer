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

package net.ree_jp.reefseichi.system.protect

import cn.nukkit.Server
import cn.nukkit.plugin.PluginBase
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.protect.api.ProtectAPI
import net.ree_jp.reefseichi.system.protect.event.ProtectListener
import net.ree_jp.reefseichi.system.protect.sql.ProtectHelper

class ReefProtect {

    companion object {

        private lateinit var instance: ReefProtect

        fun getInstance(): ReefProtect {
            if (!::instance.isInitialized) {
                instance = ReefProtect()
            }
            return instance
        }

        fun registerListener(plugin: PluginBase) {
            val pm = Server.getInstance().pluginManager

            pm.registerEvents(ProtectListener(), plugin)
        }
    }

    private lateinit var api: ProtectAPI

    private lateinit var helper: ProtectHelper

    fun getApi(): ProtectAPI {
        if (!::api.isInitialized) {
            api = ProtectAPI()
        }
        return api
    }

    fun getHelper(): ProtectHelper {
        if (!::helper.isInitialized) {
            helper = ProtectHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
        }
        return helper
    }
}