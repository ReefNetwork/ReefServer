package net.ree_jp.reefseichi.system.customMessage

import net.ree_jp.reefseichi.ReefSeichiPlugin
import net.ree_jp.reefseichi.system.customMessage.sqlite.CustomMessageHelper

class ReefCustomMessage {

    companion object{

        private lateinit var instance: CustomMessageHelper

        fun getInstance(): CustomMessageHelper
        {
            if (!::instance.isInitialized) {
                instance = CustomMessageHelper(ReefSeichiPlugin.getInstance().dataFolder.path)
            }
            return instance
        }
    }
}