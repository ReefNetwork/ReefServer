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
import cn.nukkit.utils.TextFormat
import me.onebone.economyapi.EconomyAPI
import net.ree_jp.reefseichi.ReefNotice
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import net.ree_jp.reefseichi.system.seichi.data.Skill

class SkillShopCheckForm(
    player: Player,
    skill: SellSkill,
    content: String
) : Response, FormWindowModal(
    "スキル確認",
    "",
    "",
    ""
) {

    init {
        val skillName = skill.skill.name
        val xuid = player.loginChainData.xuid
        if (skill.isCanBuy(player)) {
            setButton1("解放する") {
                EconomyAPI.getInstance().reduceMoney(player, skill.coin.toDouble())
                addSkill(xuid, skill.skill)
                player.sendMessage("${ReefNotice.SUCCESS}${skillName}を開放しました")
                player.showFormWindow(
                    SkillSelectCheckForm(
                        skill.skill,
                        player,
                        "$skillName を開放しました\nすぐにスキルを使用するには'使用する'を選択してください\n"
                    )
                )
            }
        } else setButton1("${TextFormat.RED}解放できません") {
            player.showFormWindow(
                SkillSelectForm(
                    player,
                    "$skillName はまだ解放できません\nお金かレベルが足りない可能性があります\n\n"
                )
            )
        }
        setButton2("スキルショップに戻る") { player.showFormWindow(SkillSelectForm(player, "")) }
        setContent("$content${skill.getDescription()}")
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseModal) throw Exception("不正なformの返り値")

        runTask(response.clickedButtonId == 0)
    }

    private fun addSkill(xuid: String, skill: Skill) {
        val helper = ReefSeichi.getInstance().getHelper()
        val data = helper.getData(xuid)
        val skills = data.skills.toMutableList()
        skills.add(skill)
        data.skills = skills
        helper.setData(xuid, data)
    }
}