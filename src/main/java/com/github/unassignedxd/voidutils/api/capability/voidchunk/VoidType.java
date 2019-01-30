package com.github.unassignedxd.voidutils.api.capability.voidchunk;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.util.ResourceLocation;

public enum VoidType {
    NORMAL(0, false, 8751773, new ResourceLocation(VoidUtils.MOD_ID, "voidtype_normal")),
    CORRUPTED(1, true, 16733467, new ResourceLocation(VoidUtils.MOD_ID, "voidtype_corrupted")),
    PURE(2, true,  12304127, new ResourceLocation(VoidUtils.MOD_ID, "voidtype_pure"));

    private int id;
    private boolean hasPossibleNode;
    private int decimalColor;
    private ResourceLocation location;

    VoidType(int id, boolean hasPossibleNode, int decimalColor, ResourceLocation location) {
        this.id = id;
        this.hasPossibleNode = hasPossibleNode;
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

    public boolean isHasPossibleNode() {
        return hasPossibleNode;
    }

    public static VoidType getVoidTypeFromID(int id) {
        for (VoidType type : values()) {
            if (type.getId() == id) return type;
        }

        VoidUtils.logger.error("Failed to get Void Type from a proper ID! #" + id);
        return null;
    }
}
