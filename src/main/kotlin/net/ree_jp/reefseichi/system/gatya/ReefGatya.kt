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

package net.ree_jp.reefseichi.system.gatya

import cn.nukkit.Server
import cn.nukkit.plugin.PluginBase
import net.ree_jp.reefseichi.system.gatya.api.GatyaAPI
import net.ree_jp.reefseichi.system.gatya.event.GatyaListener

class ReefGatya {

    companion object {
        private lateinit var instance: ReefGatya

        fun getInstance(): ReefGatya {
            if (!::instance.isInitialized) {
                instance = ReefGatya()
            }
            return instance
        }

        fun registerListener(plugin: PluginBase) {
            val pm = Server.getInstance().pluginManager

            pm.registerEvents(GatyaListener(), plugin)
        }
    }

    private lateinit var api: GatyaAPI

    fun getApi(): GatyaAPI {
        if (!::api.isInitialized) {
            api = GatyaAPI()
        }
        return api
    }
}