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

package net.ree_jp.reefseichi

import cn.nukkit.utils.TextFormat

class ReefNotice {

    companion object {
        val SUCCESS = "${TextFormat.GREEN}>> ${TextFormat.RESET} "
        val PROCESSING = "${TextFormat.GRAY}>> ${TextFormat.RESET} "
        val ERROR = "${TextFormat.RED}>> ${TextFormat.RESET} "
    }
}