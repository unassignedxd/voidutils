package com.github.unassignedxd.voidutils.main.item.base;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.init.IModObject;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ItemBase extends Item implements IModObject {

    private final String baseName;

    public ItemBase(String name) {
        this.baseName = name;

        this.setRegistryName(VoidUtils.MOD_ID, "item_" + name);
        this.setTranslationKey(name);
    }

    @Override
    public String getBaseName() {
        return this.baseName;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

}
