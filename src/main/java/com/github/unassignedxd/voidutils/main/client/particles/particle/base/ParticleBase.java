package com.github.unassignedxd.voidutils.main.client.particles.particle.base;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleBase extends Particle {

    public static final ResourceLocation LOC = new ResourceLocation(VoidUtils.MOD_ID, "particles/base");

    private final float partScale;
    private final boolean shouldFade;

    public ParticleBase(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float alpha, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        super(world, posX, posY, posZ);

        this.partScale = scale;
        this.shouldFade = fade;

        this.particleMaxAge = maxAge;
        this.canCollide = collision;
        this.particleGravity = gravity;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        float r = ((color >> 16) & 255) / 255F;
        float g = ((color >> 8) & 255) / 255F;
        float b = (color & 255) / 255F;
        this.setRBGColorF(r, g, b);

        TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
        this.setParticleTexture(textureMap.getAtlasSprite(LOC.toString()));

        this.particleAlpha = alpha;
        this.particleScale = this.partScale;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.particleAge++;
        if (this.particleAge >= this.particleMaxAge) {
            this.setExpired();
        } else {
            this.motionY -= 0.04D * (double) this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);

            if (this.shouldFade) {
                float lifeRatio = (float) this.particleAge / (float) this.particleMaxAge;
                this.particleAlpha = 0.75F - (lifeRatio * 0.75F);
                this.particleScale = this.partScale - (this.partScale * lifeRatio);
            }
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }
}
