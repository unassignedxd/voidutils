package com.github.unassignedxd.voidutils.main.item.functional.nodecompass;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.item.base.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import java.util.Iterator;

//will find the nearest void node
public class ItemNodeCompass extends ItemBase {

    public ItemNodeCompass() {
        super("node_compass");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        if(player != null && world != null) {
            if(!world.isRemote) {
                Iterator<Chunk> loadedChunks = world.getPersistentChunkIterable(((WorldServer) world).getPlayerChunkMap().getChunkIterator());
                while(loadedChunks.hasNext()){
                    Chunk chunk = loadedChunks.next();
                    if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)) {
                        IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                        if(voidChunk.getHasNaturalNode()){
                            BlockPos pos = voidChunk.getNodePosition();
                            if(pos != null) {
                                player.sendMessage(new TextComponentString("Node found at: " + pos.getX() + " " + pos.getY() + " " + pos.getZ()));
                                break;
                            }
                        }
                    }
                }
            }
        }

        return super.onItemRightClick(world, player, hand);
    }
}
