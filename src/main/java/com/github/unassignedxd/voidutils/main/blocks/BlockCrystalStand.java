package com.github.unassignedxd.voidutils.main.blocks;

import com.github.unassignedxd.voidutils.main.blocks.tiles.TileCrystalStand;
import com.github.unassignedxd.voidutils.main.blocks.tiles.render.RenderCrystalStand;
import com.github.unassignedxd.voidutils.main.registry.ITESRProvider;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.Tuple;

public class BlockCrystalStand extends BlockTileBase implements ITESRProvider {

    public BlockCrystalStand() {
        super("crystal_stand", TileCrystalStand.class, "crystal_stand");
    }

    @Override
    public Tuple<Class, TileEntitySpecialRenderer> getTESR() {
        return new Tuple<>(TileCrystalStand.class, new RenderCrystalStand());
    }
}
