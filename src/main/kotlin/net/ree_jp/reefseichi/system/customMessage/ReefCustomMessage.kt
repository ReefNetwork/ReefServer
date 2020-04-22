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

package net.ree_jp.reefseichi.system.customMessage

import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.customMessage.sqlite.CustomMessageHelper

class ReefCustomMessage {

    companion object{

        const val JOIN_KEY = "joinMessage"
        const val REJOIN_KEY = "rejoinMessage"
        const val QUIT_KEY = "quitMessage"

        const val DISPLAY_NAME_TAG_KEY = "nameTag"

        private lateinit var instance: CustomMessageHelper

        fun getHelper(): CustomMessageHelper
        {
            if (!::instance.isInitialized) {
                instance = CustomMessageHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
            }
            return instance
        }
    }
}