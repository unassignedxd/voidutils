package com.github.unassignedxd.voidutils.main.init;

import com.github.unassignedxd.voidutils.main.items.ItemBase;
import com.github.unassignedxd.voidutils.main.items.ItemResourceCatalyst;
import com.github.unassignedxd.voidutils.main.items.ItemVoidCatalyst;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item VOID_UNINFUSED_CATALYST = new ItemBase("void_uninfused_catalyst");
    public static final Item VOID_CATALYST = new ItemVoidCatalyst("void_catalyst");

    public static final Item RESOURCE_CATALYST = new ItemResourceCatalyst("resource_catalyst");
}
