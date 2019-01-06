package com.github.unassignedxd.voidutils.main.items;

import com.github.unassignedxd.voidutils.main.registry.ICreativeItem;
import com.github.unassignedxd.voidutils.main.registry.IModItem;
import com.github.unassignedxd.voidutils.main.registry.IModelProvider;
import com.github.unassignedxd.voidutils.main.registry.RegistryHandler;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ItemBase extends Item implements IModItem, ICreativeItem, IModelProvider {

    public final String name;

    public ItemBase(String name){
        this.name = name;
        RegistryHandler.addItemToRegister(this);
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

    @Override
    public String getUnlocalizedName() {
        return this.name;
    }
}
