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

package net.ree_jp.reefseichi.system.fly

import cn.nukkit.Server
import cn.nukkit.plugin.PluginBase
import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.fly.api.FlyAPI
import net.ree_jp.reefseichi.system.fly.event.FlyListener

class ReefFly {

    companion object {

        const val FLY_PRICE = 10

        private lateinit var instance: ReefFly

        fun getInstance(): ReefFly {
            if (!::instance.isInitialized) {
                instance = ReefFly()
            }
            return instance
        }

        fun registerListener(plugin: PluginBase) {
            val pm = Server.getInstance().pluginManager

            pm.registerEvents(FlyListener(), plugin)

            Server.getInstance().scheduler.scheduleRepeatingTask(
                ReefSeichiPlugin.getInstance(),
                { getInstance().getApi().cutAll() },
                1200
            )
        }
    }

    private lateinit var api: FlyAPI

    fun getApi(): FlyAPI {
        if (!::api.isInitialized) {
            api = FlyAPI()
        }
        return api
    }
}