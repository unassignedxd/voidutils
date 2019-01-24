package com.github.unassignedxd.voidutils.main.block.base;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.init.IModObject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class BlockBase extends Block implements IModObject {

    private final String baseName;

    public BlockBase(String name, Material material) {
        super(material);
        this.baseName = name;

        this.setRegistryName(VoidUtils.MOD_ID, "block_" + name);
        this.setTranslationKey(name);
    }

    @Override
    public String getBaseName() {
        return baseName;
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
