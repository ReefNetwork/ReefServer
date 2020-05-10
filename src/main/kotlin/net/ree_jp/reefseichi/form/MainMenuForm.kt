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

package net.ree_jp.reefseichi.form

import cn.nukkit.Player
import cn.nukkit.form.element.ElementButton
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseSimple
import cn.nukkit.form.window.FormWindowSimple
import net.ree_jp.reefseichi.system.seichi.form.SkillSelectForm
import net.ree_jp.storage.StackStoragePlugin

class MainMenuForm(player: Player, content: String) : Response, FormWindowSimple("ReefSeichiMenu", content) {

    init {
        addButton(ElementButton("ワールドを移動する") { player.showFormWindow(MainMenuForm(player, "")) })
        addButton(ElementButton("スキルを選択する") { player.showFormWindow(SkillSelectForm(player, "")) })
        addButton(ElementButton("フライを") { player.showFormWindow(MainMenuForm(player, "")) })
        addButton(ElementButton("ストレージを開く") { StackStoragePlugin.getInstance().getApi().sendGui(player) })
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }
}