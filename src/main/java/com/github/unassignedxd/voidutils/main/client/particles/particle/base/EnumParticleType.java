package com.github.unassignedxd.voidutils.main.client.particles.particle.base;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.util.ResourceLocation;

public enum EnumParticleType {
    VOID_NODE(1, new ResourceLocation(VoidUtils.MOD_ID, "void_node_particle"));

    private int id;
    private ResourceLocation loc;

    EnumParticleType(int id, ResourceLocation location) {
        this.loc = location;
        this.id = id;
    }

    public ResourceLocation getLoc() {
        return loc;
    }

    public int getId() {
        return id;
    }
}
