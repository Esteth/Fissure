package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A tear from an alternate dimension into the current one.
 */
public class SeamBlock extends Block implements ITileEntityProvider {

  public static final String NAME = "seamBlock";

  public SeamBlock() {
    super(Material.rock);
    GameRegistry.registerBlock(this, NAME);
    setUnlocalizedName(FissureMod.MODID + "_" + NAME);
    setCreativeTab(CreativeTabs.tabBlock);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public EnumWorldBlockLayer getBlockLayer() {
    return EnumWorldBlockLayer.TRANSLUCENT;
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return null;
    //return new TearEntity();
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    TileEntity tileEntity = worldIn.getTileEntity(pos);
    if (tileEntity instanceof TearEntity) {
      TearEntity tearEntity = (TearEntity) tileEntity;
      tearEntity.setTicksLeftTilDisappear(100);
    }
  }
}
