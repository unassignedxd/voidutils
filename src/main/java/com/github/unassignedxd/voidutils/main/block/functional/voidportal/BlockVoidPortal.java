package com.github.unassignedxd.voidutils.main.block.functional.voidportal;

import com.github.unassignedxd.voidutils.main.block.base.BlockTileBase;
import net.minecraft.block.material.Material;

//Allows for transmutation of items through use of void nodes.
public class BlockVoidPortal extends BlockTileBase {

    public BlockVoidPortal() {
        super("void_portal", Material.IRON, TileVoidPortal.class, "void_portal");
    }


}
