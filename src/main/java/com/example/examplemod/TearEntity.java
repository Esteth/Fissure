package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

/**
 * Created by Adam on 30/11/2015.
 */
public class TearEntity extends TileEntity implements IUpdatePlayerListBox {

  private static final String NAME = "tearEntity";
  private static final int INVALID_VALUE = -1;

  static {
    GameRegistry.registerTileEntity(TearEntity.class, NAME);
  }

  private int ticksLeftTilDisappear = INVALID_VALUE;

  public void setTicksLeftTilDisappear(int ticks) {
    ticksLeftTilDisappear = ticks;
  }

  @Override
  public void update() {
    if (!hasWorldObj()) {
      return;
    }
    World world = getWorld();
    if (world.isRemote) {
      return;
    }
    if (ticksLeftTilDisappear == INVALID_VALUE) {
      return;
    }

    ticksLeftTilDisappear--;

    if (ticksLeftTilDisappear > 0) {
      return;
    }

    Block[] choices = {Blocks.diamond_block, Blocks.obsidian};
    Random random = new Random();
    Block chosenBlock = choices[random.nextInt(choices.length)];
    world.setBlockState(this.pos, chosenBlock.getDefaultState());
  }

  @Override
  public Packet getDescriptionPacket() {
    NBTTagCompound tag = new NBTTagCompound();
    writeToNBT(tag);
    int metadata = getBlockMetadata();
    return new S35PacketUpdateTileEntity(this.pos, metadata, tag);
  }

  @Override
  public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
    readFromNBT(pkt.getNbtCompound());
  }

  @Override
  public void writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    tag.setInteger("ticksLeft", ticksLeftTilDisappear);
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    int readTicks = INVALID_VALUE;
    if (tag.hasKey("ticksLeft")) {
      readTicks = tag.getInteger("ticksLeft");
      if (readTicks < 0) {
        readTicks = INVALID_VALUE;
      }
    }
    ticksLeftTilDisappear = readTicks;
  }
}
