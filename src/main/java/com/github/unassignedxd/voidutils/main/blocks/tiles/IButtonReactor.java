package com.github.unassignedxd.voidutils.main.blocks.tiles;

import net.minecraft.entity.player.EntityPlayer;

public interface IButtonReactor {
    void onButtonPressed(int buttonID, EntityPlayer player);
}
