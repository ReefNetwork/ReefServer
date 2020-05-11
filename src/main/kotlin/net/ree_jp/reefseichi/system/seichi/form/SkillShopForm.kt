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
import cn.nukkit.utils.TextFormat
import net.bbo51dog.ecokkit.api.EcokkitAPI
import net.ree_jp.reefseichi.form.Response
import net.ree_jp.reefseichi.system.seichi.ReefSeichi
import net.ree_jp.reefseichi.system.seichi.data.Skill

class SkillShopForm(player: Player, content: String) : Response, FormWindowSimple("スキルショップ", content) {

    val sellSkills = listOf(
        SellSkill(
            Skill(
                "1*2",
                3,
                3,
                1,
                2,
                1
            ), 3, 100
        ),
        SellSkill(
            Skill(
                "3*3",
                10,
                5,
                1,
                3,
                3
            ), 5, 5000
        ),
        SellSkill(
            Skill(
                "3*3*3",
                20,
                20,
                3,
                3,
                3
            ), 15, 50000
        )
    )

    init {
        val xuid = player.loginChainData.xuid
        val helper = ReefSeichi.getInstance().getHelper()
        val skills = helper.getData(xuid).skills
        for (skill in sellSkills) {
            for (have in skills) {

            }
            if (skill.isCanBuy(player)) {
                addButton(ElementButton(skill.skill.name) { player.showFormWindow(SkillSelectForm(player, "")) })
            } else addButton(ElementButton(TextFormat.RED.toString() + skill.skill.name) {
                player.showFormWindow(
                    SkillShopCheckForm(player, skill, "")
                )
            })
        }
        addButton(ElementButton("スキルを選択に戻る") { player.showFormWindow(SkillSelectForm(player, "")) })
    }

    override fun handleResponse(player: Player, response: FormResponse) {
        if (response !is FormResponseSimple) throw Exception("不正なformの返り値")

        response.clickedButton.runTask()
    }
}

data class SellSkill(
    val skill: Skill,
    val level: Int,
    val coin: Int
) {

    fun getDescription(): String {
        val space = "${skill.height}.${skill.wight}.${skill.depth}"
        return "値段:$coin\n名前:${skill.name}\nクールタイム:${skill.coolTime / 20.toDouble()}秒\n消費マナ:${skill.mana}\n範囲:$space\n必要レベル:$level"
    }

    fun isCanBuy(p: Player): Boolean {
        val xuid = p.loginChainData.xuid
        val seichi = ReefSeichi.getInstance()
        val data = seichi.getHelper().getData(xuid)

        if ((EcokkitAPI.instance.getMoney(xuid) >= coin) &&
            (seichi.getLevel(data.xp) >= level) &&
            (!data.skills.contains(skill))
        ) return true
        return false
    }
}