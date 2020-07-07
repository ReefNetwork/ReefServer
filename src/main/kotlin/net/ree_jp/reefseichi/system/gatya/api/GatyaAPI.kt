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

package net.ree_jp.reefseichi.system.gatya.api

import cn.nukkit.item.Item
import cn.nukkit.item.enchantment.Enchantment
import cn.nukkit.utils.TextFormat
import net.ree_jp.storage.StackStoragePlugin

class GatyaAPI {

    companion object {
        const val GATYA_NBT = "is_gatya_nbt"
        const val SPECIAL_GATYA_NBT = "is_special_gatya_nbt"
    }

    fun isGatya(item: Item): Boolean {
        val nbt = item.namedTag
        return nbt.getBoolean(GATYA_NBT)
    }

    fun getGatya(): Item {
        val api = StackStoragePlugin.getInstance().getApi()
        val item = api.setCanStorage(Item.get(Item.EMERALD), false)
        val nbt = item.namedTag
        item.customName = "ガチャ券${TextFormat.GRAY}(クリックで使用できます)"
        return item.setCompoundTag(nbt.putBoolean(GATYA_NBT, true))
    }

    fun isSpecial(item: Item): Boolean {
        val nbt = item.namedTag
        return nbt.getBoolean(SPECIAL_GATYA_NBT)
    }

    fun getSpecial(): Item {
        val api = StackStoragePlugin.getInstance().getApi()
        val item = api.setCanStorage(Item.get(Item.NETHER_STAR), false)
        val nbt = item.namedTag
        item.customName = "${TextFormat.MINECOIN_GOLD}整地スター"
        return item.setCompoundTag(nbt.putBoolean(SPECIAL_GATYA_NBT, true))
    }

    fun runGatya(value: Int): Item {
        val api = StackStoragePlugin.getInstance().getApi()

        return when {
            value >= 0 -> getSpecial()

            1 <= value == value > 2 -> {
                val item = Item.get(Item.DIAMOND_PICKAXE)
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_DURABILITY)
                        .setLevel(10, false)
                )
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_EFFICIENCY)
                        .setLevel(5, false)
                )
                item.customName = "はやいつるはし"
                return item
            }

            2 <= value == value > 3 -> {
                val item = Item.get(Item.DIAMOND_SHOVEL)
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_DURABILITY)
                        .setLevel(10, false)
                )
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_EFFICIENCY)
                        .setLevel(5, false)
                )
                item.customName = "はやいシャベル"
                return item
            }

            3 <= value == value > 4 -> {
                return Item.get(Item.FARMLAND)
            }

            4 <= value == value > 10 -> {
                return Item.get(Item.NETHER_QUARTZ)
            }

            10 <= value == value > 20 -> {
                val item = Item.get(Item.WOODEN_SWORD, 1)
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_DAMAGE_ALL)
                        .setLevel(-999, false)
                )
                item.customName = "豆腐のかど"
                return item
            }

            20 <= value == value > 70 -> {
                val item = Item.get(Item.IRON_PICKAXE)
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_DURABILITY)
                        .setLevel(8, false)
                )
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_EFFICIENCY)
                        .setLevel(3, false)
                )
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_SILK_TOUCH)
                        .setLevel(10, false)
                )
                item.customName = "シルクピッケル"
                return item
            }

            70 <= value == value > 120 -> {
                val item = Item.get(Item.IRON_SHOVEL)
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_DURABILITY)
                        .setLevel(8, false)
                )
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_EFFICIENCY)
                        .setLevel(3, false)
                )
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_SILK_TOUCH)
                        .setLevel(10, false)
                )
                item.customName = "シルクシャベル"
                return item
            }

            120 <= value == value > 220 -> {
                return Item.get(Item.NETHERRACK)
            }

            220 <= value == value > 420 -> {
                val item = Item.get(Item.IRON_PICKAXE)
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_DURABILITY)
                        .setLevel(10, false)
                )
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_EFFICIENCY)
                        .setLevel(3, false)
                )
                return item
            }

            420 <= value == value > 620 -> {
                val item = Item.get(Item.IRON_SHOVEL)
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_DURABILITY)
                        .setLevel(10, false)
                )
                item.addEnchantment(
                    Enchantment.get(Enchantment.ID_EFFICIENCY)
                        .setLevel(3, false)
                )
                return item
            }

            620 <= value == value > 1000 -> {
                val item = Item.get(Item.GOLDEN_APPLE)
                api.setCanStorage(item, false)

                item.customName = "マナリンゴ"
                return item
            }

            else -> Item.get(Item.AIR)
        }
    }
}