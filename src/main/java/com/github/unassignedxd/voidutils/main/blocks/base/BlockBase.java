package com.github.unassignedxd.voidutils.main.blocks.base;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBase extends Block {
    protected String name;

    public BlockBase(String name, Material mat, float hardness, float resistance) {
        super(mat);
        this.name = name;
        this.setHardness(hardness);
        this.setResistance(resistance);

        this.setRegistryName(new ResourceLocation(VoidUtils.MOD_ID, name));
        this.setTranslationKey(this.getRegistryName().getNamespace());
    }

    public BlockBase(String name, float hardness, float resistance) { this(name, Material.ROCK, hardness, resistance); }

    public BlockBase(String name) { this(name, Material.ROCK, 5, 5); }

    public String getBaseName() { return this.name; }


}
