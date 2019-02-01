package com.github.unassignedxd.voidutils.api.capability.voidchunk;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.util.ResourceLocation;

public enum EnumVoidType {
    NORMAL(new ResourceLocation(VoidUtils.MOD_ID, "voidtype_normal"), 0, 8422020, false),
    CORRUPTED(new ResourceLocation(VoidUtils.MOD_ID, "voidtype_corrupted"), 1, 11746618, true),
    PURE(new ResourceLocation(VoidUtils.MOD_ID, "voidtype_pure"), 2, 5743871, true);

    protected ResourceLocation location;
    protected int id;
    protected int color;
    protected boolean possibleNaturalNode;

    EnumVoidType(ResourceLocation location, int id, int decimalColor, boolean possibleNaturalNode) {
        this.location = location;
        this.id = id;
        this.color = decimalColor;
        this.possibleNaturalNode = possibleNaturalNode;
    }

    public int getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public boolean getPossibleNaturalNode() {
        return possibleNaturalNode;
    }

    public static EnumVoidType getVoidTypeByID(int id) {
        for(EnumVoidType type : values()){
            if(id == type.getId())
                return type;
        }
        VoidUtils.logger.error("Invalid Void ID! ID given: " + id) ;
        return null;
    }

    public static EnumVoidType getVoidTypeByRL(ResourceLocation location) {
        for(EnumVoidType type : values()) {
            if(type.location.equals(location))
                return type;
        }
        VoidUtils.logger.error("Invalid Void Resource Location! Location: " + location.toString());
        return null;
    }
}
