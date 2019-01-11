package com.github.unassignedxd.voidutils.main.init;

import com.github.unassignedxd.voidutils.main.items.ItemBase;
import com.github.unassignedxd.voidutils.main.items.ItemResourceCatalyst;
import com.github.unassignedxd.voidutils.main.items.ItemVoidCatalyst;
import com.github.unassignedxd.voidutils.main.items.ItemVoidCatalystUninfused;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item VOID_SHARD = new ItemBase("void_shard");

    public static final Item CRAFTING_CATALYST_TIER_1 = new ItemBase("crafting_catalyst_tier_1");
    public static final Item CRAFTING_CATALYST_TIER_2 = new ItemBase("crafting_catalyst_tier_2");
    public static final Item CRAFTING_CATALYST_TIER_3 = new ItemBase("crafting_catalyst_tier_3");
    public static final Item CRAFTING_CATALYST_TIER_4 = new ItemBase("crafting_catalyst_tier_4");
    public static final Item CRAFTING_CATALYST_TIER_5 = new ItemBase("crafting_catalyst_tier_5");

    public static final Item RESOURCE_CATALYST = new ItemResourceCatalyst("resource_catalyst");
    public static final Item VOID_UNINFUSED_CATALYST_TIER_1 = new ItemVoidCatalystUninfused("void_catalyst_uninfused_tier_1", 1);
    public static final Item VOID_UNINFUSED_CATALYST_TIER_2 = new ItemVoidCatalystUninfused("void_catalyst_uninfused_tier_2", 2);
    public static final Item VOID_UNINFUSED_CATALYST_TIER_3 = new ItemVoidCatalystUninfused("void_catalyst_uninfused_tier_3", 3);
    public static final Item VOID_UNINFUSED_CATALYST_TIER_4 = new ItemVoidCatalystUninfused("void_catalyst_uninfused_tier_4", 4);
    public static final Item VOID_UNINFUSED_CATALYST_TIER_5 = new ItemVoidCatalystUninfused("void_catalyst_uninfused_tier_5", 5);
    public static final Item VOID_CATALYST = new ItemVoidCatalyst("void_catalyst");

}
