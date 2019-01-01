package com.github.unassignedxd.voidutils.main.registry;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.creativetab.CreativeTabs;

public interface ICreativeItem {
    default CreativeTabs getCreativeTab() {
        return VoidUtils.CREATIVE_TAB;
    }
}
