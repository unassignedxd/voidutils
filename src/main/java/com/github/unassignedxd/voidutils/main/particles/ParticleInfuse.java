package com.github.unassignedxd.voidutils.main.particles;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleInfuse extends Particle {

    public static final ResourceLocation TEXTURE = new ResourceLocation(VoidUtils.MOD_ID, "particles/infuse");

    private final float desiredScale;
    private final boolean shouldFade;

    public ParticleInfuse(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float alpha, float scale,
                                int maxAge, float gravity, boolean collision, boolean fade){
        super(world, posX, posY, posZ);
        this.desiredScale = scale;
        this.particleMaxAge = maxAge;
        this.canCollide = collision;
        this.particleGravity = gravity;
        this.shouldFade = fade;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        float r = ((color >> 16) & 255) / 255f; //conversion from decimal color to RGB wheel
        float g = ((color >> 8) & 255) / 255f;
        float b = ((color & 255) / 255f);
        this.setRBGColorF(r,g,b);

        TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
        this.setParticleTexture(map.getAtlasSprite(TEXTURE.toString()));

        this.particleAlpha = alpha;
        this.particleScale = this.desiredScale;

    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
        } else {
            this.motionY -= 0.04D * (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);

            if(this.shouldFade) {
                float ratio = (float) this.particleAge / (float) this.particleMaxAge;
                this.particleAlpha -= (ratio * this.particleAlpha);
                this.particleScale = this.desiredScale - (this.desiredScale * ratio);
            }
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }


}
