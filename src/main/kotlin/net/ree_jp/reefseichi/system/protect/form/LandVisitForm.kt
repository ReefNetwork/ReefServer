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

package net.ree_jp.reefseichi.system.protect.form

import cn.nukkit.Player
import cn.nukkit.form.element.ElementInput
import cn.nukkit.form.element.ElementLabel
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseCustom
import cn.nukkit.form.window.FormWindowCustom
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.sql.ReefHelper
import net.ree_jp.reefseichi.system.protect.ReefProtect

class LandVisitForm(content: String) : Response, FormWindowCustom("土地訪問") {

    init {
        addElement(ElementLabel(content))
        addElement(ElementInput("訪れる土地の所有者を入力してください", "name"))
        addElement(ElementInput("土地のidを入力してください", "id"))
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseCustom) throw Exception("不正なformの返り値")

        val helper = ReefProtect.getInstance().getHelper()
        val user = ReefHelper.getInstance().getUser(response.getInputResponse(1))
        val id = response.getInputResponse(2)

        if (user.xuid == "") {
            player.sendMessage("${ReefNotice.SUCCESS}プレイヤーが見つかりません")
        } else if (!helper.isExists(user.xuid, id)) {
            player.sendMessage("${ReefNotice.SUCCESS}土地が見つかりません")
        } else {
            val land = helper.getLand(user.xuid, id)
            player.teleport(land.spawnPoint)
            player.sendMessage("${ReefNotice.SUCCESS}$id にテレポートしました")
        }
    }
}