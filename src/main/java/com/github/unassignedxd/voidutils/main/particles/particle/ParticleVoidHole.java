package com.github.unassignedxd.voidutils.main.particles.particle;

import com.github.unassignedxd.voidutils.main.particles.particle.base.ParticleBase;
import net.minecraft.world.World;

public class ParticleVoidHole extends ParticleBase {

    public ParticleVoidHole(World world, double posX, double posY, double posZ,
                        double motionX, double motionY, double motionZ,
                        float alpha, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ, alpha, color, scale, maxAge, gravity, collision, fade);
    }


}
