package com.github.unassignedxd.voidutils.api.capability.voidchunk;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.util.ResourceLocation;

public enum EnumVoidTypes{

    NORMAL(new ResourceLocation(VoidUtils.MOD_ID, "voidtype_normal"), 1, 6579556, false),
    CORRUPTED(new ResourceLocation(VoidUtils.MOD_ID, "voidtype_corrupted"), 2, 13251133, true),
    PURE(new ResourceLocation(VoidUtils.MOD_ID, "voidtype_pure"), 3, 5452501, true);

    public ResourceLocation name;
    public int id;
    public int decimalColor;
    public boolean hasPossibleNaturalNode;

    EnumVoidTypes(ResourceLocation name, int id, int decimalColor, boolean hasPossibleNaturalNode) {
        this.name = name;
        this.id = id;
        this.decimalColor = decimalColor;
        this.hasPossibleNaturalNode = hasPossibleNaturalNode;
    }

    public ResourceLocation getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getDecimalColor() {
        return decimalColor;
    }

    public boolean isHasPossibleNaturalNode() {
        return hasPossibleNaturalNode;
    }

    public static EnumVoidTypes getVoidTypeByID(int id) {
        for(EnumVoidTypes types : values()) {
            if(types.getId() == id) return types;
        }
        return null;
    }

    public static EnumVoidTypes getVoidTypeByLocation(ResourceLocation location) {
        for(EnumVoidTypes types : values()) {
            if(types.getName().equals(location)) return types;
        }
        return null;
    }
}
