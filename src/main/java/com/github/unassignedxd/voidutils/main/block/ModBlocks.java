package com.github.unassignedxd.voidutils.main.block;

import com.github.unassignedxd.voidutils.main.block.functional.voidnode.BlockVoidNode;
import com.github.unassignedxd.voidutils.main.block.functional.voidportal.BlockVoidPortal;
import com.github.unassignedxd.voidutils.main.block.world.BlockVoidOre;
import net.minecraft.block.Block;

public class ModBlocks {

    public static Block VOID_ORE = new BlockVoidOre();

    public static Block VOID_NODE = new BlockVoidNode();

    public static Block VOID_PORTAL = new BlockVoidPortal();

    public static final Block[] BLOCKS = new Block[]{
            VOID_ORE,
            VOID_NODE,
            VOID_PORTAL
    };
}
