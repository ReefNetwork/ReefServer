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
import cn.nukkit.form.response.FormResponse
import cn.nukkit.form.response.FormResponseModal
import cn.nukkit.form.window.FormWindowModal
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import net.ree_jp.reefseichi.system.seichi.data.Skill

class SkillSelectCheckForm(
    skill: Skill,
    player: Player,
    content: String
) : Response, FormWindowModal(
    "スキル確認",
    "",
    "",
    ""
) {

    init {
        val xuid = player.loginChainData.xuid
        setButton1("使用する") {
            ReefSeichi.getInstance().getAPI().setSkill(xuid, skill)
            player.sendMessage("${ReefNotice.SUCCESS}スキルを${skill.name}に変更しました")
        }
        setButton2("スキル選択に戻る") { player.showFormWindow(SkillSelectForm(player, "")) }
        val space = "${skill.height}.${skill.wight}.${skill.depth}"
        val message =
            "$content このスキルを使用しますか?\n名前:${skill.name}\nクールタイム:${skill.coolTime / 20.toDouble()}秒\n消費マナ:${skill.mana}\n範囲:$space."
        setContent(message)
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseModal) throw Exception("不正なformの返り値")

        runTask(response.clickedButtonId == 0)
    }
}