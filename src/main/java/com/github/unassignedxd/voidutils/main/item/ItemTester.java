package com.github.unassignedxd.voidutils.main.item;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.item.base.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ItemTester extends ItemBase {

    public ItemTester() {
        super("temp_tester");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
            if(playerIn != null) {
                Chunk chunk = worldIn.getChunk(playerIn.getPosition());
                if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)) {
                    IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                    System.out.println(voidChunk.getHasNaturalNode());

                    if(playerIn.isSneaking()) {
                        voidChunk.extractVoidEnergy(100, false);
                    } else {
                    }

                }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
