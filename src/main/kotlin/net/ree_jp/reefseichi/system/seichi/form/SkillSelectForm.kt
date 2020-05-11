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

package net.ree_jp.reefseichi.system.seichi.form

import cn.nukkit.Player
import cn.nukkit.form.element.ElementButton
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseSimple
import cn.nukkit.form.window.FormWindowSimple
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.seichi.ReefSeichi

class SkillSelectForm(player: Player, content: String) : Response, FormWindowSimple("スキル選択", content) {

    init {
        val xuid = player.loginChainData.xuid
        for (skill in ReefSeichi.getInstance().getHelper().getData(xuid).skills) {
            addButton(ElementButton(skill.name) { player.showFormWindow(SkillSelectCheckForm(skill, player, "")) })
        }
        addButton(ElementButton("スキルを開放する") { player.showFormWindow(SkillShopForm(player, "解放したいスキルを選択してください")) })
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }
}