package com.github.unassignedxd.voidutils.main.util;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.client.particles.particle.base.EnumParticleType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import javax.vecmath.Vector3f;
import java.util.Random;

/**
 * Useful functions that can be used throughout my code
 */
public final class ModUtil {

    @Nullable
    public static <T> T getCapability(@Nullable ICapabilityProvider provider, Capability<T> capability, @Nullable EnumFacing facing) {
        return provider != null && provider.hasCapability(capability, facing) ? provider.getCapability(capability, facing) : null;
    }

    public static double getRangedDouble(Random rand, double min, double max) {
        return (min + rand.nextDouble() * (max - min));
    }

    public static float getRangedFloat(Random rand, float min, float max) {
        return (min + rand.nextFloat() * (max - min));
    }

    public static BlockPos getCentralBlockPos(Chunk chunk) {
        int x = (chunk.x << 4) + 8;
        int z = (chunk.z << 4) + 8;
        int y = chunk.getWorld().getHeight(x, z);
        return new BlockPos(x, y, z);
    }

    public static void spawnParticle(EnumParticleType particleType, float posX, float posY, float posZ, double motionX, double motionY, double motionZ, float alpha, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade){
        VoidUtils.proxy.spawnParticle(particleType, posX, posY, posZ,
                motionX, motionY, motionZ,
                alpha, color, scale, maxAge, gravity, collision, fade);
    }

    public static void spawnParticleStream(EnumParticleType particleType, float startX, float startY, float startZ, float endX, float endY, float endZ, float speed, float alpha, int color, float scale){
        Vector3f dir = new Vector3f(endX - startX, endY - startY, endZ - startZ);
        if(dir.length() > 0) {
            int maxAge = (int)(dir.length() / speed);
            dir.normalize();
            VoidUtils.proxy.spawnParticle(particleType, startX, startY, startZ,
                    dir.x*speed, dir.y*speed, dir.z*speed,
                    alpha, color, scale, maxAge, 0F, false, false);
        }
    }

}
