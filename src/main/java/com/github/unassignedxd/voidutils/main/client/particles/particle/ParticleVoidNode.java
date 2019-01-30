package com.github.unassignedxd.voidutils.main.client.particles.particle;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.client.particles.particle.base.ParticleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleVoidNode extends ParticleBase {

    public static final ResourceLocation LOC = new ResourceLocation(VoidUtils.MOD_ID, "particles/vnode");

    public ParticleVoidNode(World world, double posX, double posY, double posZ,
                            double motionX, double motionY, double motionZ,
                            float alpha, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ, alpha, color, scale, maxAge, gravity, collision, fade);

        TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
        this.setParticleTexture(textureMap.getAtlasSprite(LOC.toString()));
    }

}
