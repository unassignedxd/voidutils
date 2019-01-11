package com.github.unassignedxd.voidutils.main.blocks;


import com.github.unassignedxd.voidutils.main.blocks.tiles.TileCrystal;

/**
 * This will hold the crystal type (ex. void crystal or resource crystal) and store the data.
 * This can be put into a Crystal Stand in which it can be manipulated.
 */
public class BlockCrystal extends BlockTileBase {

    public BlockCrystal(String name){
        super(name, TileCrystal.class, "crystal");
    }
}
