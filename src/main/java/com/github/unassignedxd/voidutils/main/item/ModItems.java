package com.github.unassignedxd.voidutils.main.item;

import com.github.unassignedxd.voidutils.main.item.functional.ItemVoidGoggles;
import com.github.unassignedxd.voidutils.main.item.ingred.ItemVoidShard;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item TEMP_TEST = new ItemTester();

    public static final Item VOID_SHARD = new ItemVoidShard();

    public static final Item VOID_GOGGLES = new ItemVoidGoggles();

    public static final Item[] ITEMS = new Item[]{
            TEMP_TEST,
            VOID_SHARD,
            VOID_GOGGLES
    };

}
