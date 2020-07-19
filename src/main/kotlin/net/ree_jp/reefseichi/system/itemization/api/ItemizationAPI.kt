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

package net.ree_jp.reefseichi.system.itemization.api

import cn.nukkit.item.Item
import cn.nukkit.item.enchantment.Enchantment
import cn.nukkit.utils.TextFormat
import net.ree_jp.storage.StackStoragePlugin

class ItemizationAPI {

    val price_nbt = "price_nbt"

    fun getCoin(price: Double, count: Int): Item {
        val item = Item.get(Item.GOLDEN_NUGGET)
        val nbt = item.namedTag
        val storage = StackStoragePlugin.getInstance().getApi()

        item.setCount(count)
        storage.setCanStorage(item, false)
        item.customName = "${TextFormat.MINECOIN_GOLD}$price コイン"
        item.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_DURABILITY).setLevel(100))
        nbt.putDouble(price_nbt, price)
        return item.setCompoundTag(nbt)
    }

    fun getPrice(item: Item): Double {
        val nbt = item.namedTag
        return nbt.getDouble(price_nbt)
    }
}