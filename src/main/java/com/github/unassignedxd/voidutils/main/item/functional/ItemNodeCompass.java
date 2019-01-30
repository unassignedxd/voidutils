package com.github.unassignedxd.voidutils.main.item.functional;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.item.base.ItemBase;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

//will find the nearest void node
public class ItemNodeCompass extends ItemBase {

    public ItemNodeCompass() {
        super("node_compass");

        this.addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            double rotation;
            @SideOnly(Side.CLIENT)
            double rota;
            @SideOnly(Side.CLIENT)
            long lastUpdateTick;

            @SideOnly(Side.CLIENT)
            Field chunkMapping;

            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null && !stack.isOnItemFrame()) {
                    return 0.0F;
                } else {
                    Entity entity = entityIn != null ? entityIn : stack.getItemFrame();
                    if (world == null) {
                        world = ((Entity)entity).world;
                    }

                    double d0;
                    if (world.provider.isSurfaceWorld()) {
                        double d1 = entity != null ? (double)((Entity)entity).rotationYaw : this.getFrameRotation((EntityItemFrame)entity);
                        d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
                        double d2 = this.getSpawnToAngle(world, (Entity)entity) / 6.283185307179586D;
                        d0 = 0.5D - (d1 - 0.25D - d2);
                    } else {
                        d0 = Math.random();
                    }

                    if (entity != null) {
                        d0 = this.wobble(world, d0);
                    }

                    return MathHelper.positiveModulo((float)d0, 1.0F);
                }
            }

            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double p_185093_2_) {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick) {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = p_185093_2_ - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.8D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }

            @SideOnly(Side.CLIENT)
            private double getFrameRotation(EntityItemFrame p_185094_1_) {
                return (double)MathHelper.wrapDegrees(180 + p_185094_1_.facingDirection.getHorizontalIndex() * 90);
            }

            @SideOnly(Side.CLIENT)
            private double getSpawnToAngle(World world, Entity entity) {
                BlockPos closestNode = null;

                Long2ObjectMap<Chunk> chunkMap;
                try {
                    this.chunkMapping = ChunkProviderClient.class.getDeclaredField("loadedChunks");
                    this.chunkMapping.setAccessible(true);
                    chunkMap = (Long2ObjectMap<Chunk>) chunkMapping.get(world.getChunkProvider());
                } catch (Exception e) {
                    return 0D;
                }

                for(Chunk chunk : chunkMap.values()) {
                    if(chunk.hasCapability(CapabilityVoidChunk.VOID_CHUNK_CAPABILITY, null)){
                        IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                        if(voidChunk.getNodePos() != null) {
                            closestNode = voidChunk.getNodePos();
                        }
                    }
                }

                if(closestNode == null) { return world.getTotalWorldTime() % 360; }

                return Math.atan2((double)closestNode.getZ() - entity.posZ, (double)closestNode.getX() - entity.posX);
            }
        });

    }
}
