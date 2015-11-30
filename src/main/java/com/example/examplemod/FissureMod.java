package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = FissureMod.MODID, version = FissureMod.VERSION)
public class FissureMod {
  public static final String MODID = "fissure";
  public static final String VERSION = "1.0";

  public Block seamBlock;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    seamBlock = new SeamBlock();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    if (event.getSide() == Side.CLIENT) {
      RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

      renderItem.getItemModelMesher().register(
          Item.getItemFromBlock(seamBlock),
          0,
          new ModelResourceLocation(MODID + ":" + SeamBlock.NAME, "inventory")
      );

      GameRegistry.registerWorldGenerator(new SeamGenerator(), 1);
    }
  }
}
