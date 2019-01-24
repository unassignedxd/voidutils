package com.github.unassignedxd.voidutils.main.world;

import com.github.unassignedxd.voidutils.main.VoidConfig;
import com.github.unassignedxd.voidutils.main.block.ModBlocks;
import com.github.unassignedxd.voidutils.main.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class WorldOreGenerator implements IWorldGenerator {

    public WorldOreGenerator() {
        GameRegistry.registerWorldGenerator(this, 10);
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider) {
        int dimID = world.provider.getDimension();

        if(VoidConfig.world.shouldVoidOreSpawn){
            if(world.getWorldType() != WorldType.FLAT && !ArrayUtils.contains(VoidConfig.world.voidOreSpawnBlacklistDimIDs, dimID)) {
                generateOre(ModBlocks.VOID_ORE.getDefaultState(), Blocks.STONE, world,
                        random, chunkX<<4, chunkZ<<4,
                        MathHelper.getInt(random, 3, VoidConfig.world.voidMaxVeinSize),
                        VoidConfig.world.voidOreSpawnChance, VoidConfig.world.voidOreMinY, VoidConfig.world.voidOreMaxY);
            }
        }
    }

    public void generateOre(IBlockState state, Block block, World world, Random rand, int blockX, int blockZ, int maxVeinSize, int spawnChance, int minY, int maxY){
        for(int i = 0; i < spawnChance; i++){
            int posX = blockX+rand.nextInt(16);
            int posY = minY+rand.nextInt(maxY-minY);
            int posZ = blockZ+rand.nextInt(16);
            new WorldGenMinable(state, maxVeinSize, BlockMatcher.forBlock(block)).generate(world, rand, new BlockPos(posX, posY, posZ));
        }
    }


}
