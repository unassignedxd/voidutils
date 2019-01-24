package com.github.unassignedxd.voidutils.api.capability.voidchunk;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.util.ResourceLocation;

public enum VoidType {
    NORMAL(0, 0, new ResourceLocation(VoidUtils.MOD_ID, "voidtype_normal")),
    CORRUPTED(1, 0, new ResourceLocation(VoidUtils.MOD_ID, "voidtype_corrupted")),
    PURE(2, 0, new ResourceLocation(VoidUtils.MOD_ID, "voidtype_pure"));

    private int id;
    private int decimalColor;
    private ResourceLocation location;

    VoidType(int id, int decimalColor, ResourceLocation location) {
        this.id = id;
        this.decimalColor = decimalColor;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public int getDecimalColor() {
        return decimalColor;
    }
}
