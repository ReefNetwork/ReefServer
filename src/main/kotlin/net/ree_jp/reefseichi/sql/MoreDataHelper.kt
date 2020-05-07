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

package net.ree_jp.reefseichi.sql

import net.ree_jp.reefseichi.ReefSeichiPlugin

class MoreDataHelper(path: String) : SqliteHelper("$path/moreData.db") {

    companion object {

        private lateinit var instance: MoreDataHelper

        fun getInstance(): MoreDataHelper {
            if (!::instance.isInitialized) {
                instance = MoreDataHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
            }
            return instance
        }
    }
}