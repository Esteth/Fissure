package com.example.examplemod;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Generates the seams that hold the world together.
 */
public class SeamGenerator implements IWorldGenerator {

  private int height = -1;

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
    if (height < 0) {
      height = world.getTopSolidOrLiquidBlock(new BlockPos(chunkX * 16, 0, chunkZ * 16)).add(0, 20, 0).getY();
    }

    int xCoord = chunkX * 16;
    int zCoord = chunkZ * 16;
    for (int offset = 0; offset < 16; offset++) {
      BlockPos newXPos = new BlockPos(xCoord + offset, height, zCoord + 8);
      BlockPos newZPos = new BlockPos(xCoord + 8, height, zCoord + offset);
      world.setBlockState(newXPos, Blocks.diamond_block.getDefaultState(), 2);
      world.setBlockState(newZPos, Blocks.diamond_block.getDefaultState(), 2);
    }
  }

}
