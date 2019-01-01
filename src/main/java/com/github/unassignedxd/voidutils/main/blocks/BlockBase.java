package com.github.unassignedxd.voidutils.main.blocks;

import com.github.unassignedxd.voidutils.main.registry.ICreativeItem;
import com.github.unassignedxd.voidutils.main.registry.IModItem;
import com.github.unassignedxd.voidutils.main.registry.IModelProvider;
import com.github.unassignedxd.voidutils.main.registry.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class BlockBase extends Block implements IModItem, ICreativeItem, IModelProvider {
    protected String name;

    public BlockBase(String name, Material mat, float hardness, float resistance) {
        super(mat);
        this.name = name;
        this.setHardness(hardness);
        this.setResistance(resistance);

        RegistryHandler.addItemToRegister(this);
    }

    public BlockBase(String name, float hardness, float resistance) { this(name, Material.ROCK, hardness, resistance); }

    public BlockBase(String name) { this(name, Material.ROCK, 5, 5); }

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
