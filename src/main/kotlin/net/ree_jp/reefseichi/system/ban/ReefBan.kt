package net.ree_jp.reefseichi.system.ban

import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.ban.sqlite.BanHelper

class ReefBan {

    companion object{

        private lateinit var helper: BanHelper

        fun getHelper(): BanHelper
        {
            if (!::helper.isInitialized) {
                helper = BanHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
            }
            return helper
        }
    }
}