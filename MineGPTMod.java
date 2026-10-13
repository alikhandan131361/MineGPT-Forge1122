package com.minegpt.core;

import com.minegpt.command.CommandMineGPT;
import net.minecraftforge.fml.common.SidedProxy;
import com.minegpt.entity.EntityMineGPT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = MineGPTMod.MODID, name = MineGPTMod.NAME, version = MineGPTMod.VERSION, acceptableRemoteVersions = "*")
public class MineGPTMod {
    public static final String MODID = "minegpt";
    public static final String NAME = "MineGPT";
    public static final String VERSION = "0.1.0";

    @SidedProxy(clientSide = "com.minegpt.client.ClientProxy", serverSide = "com.minegpt.core.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.registerRenderers();
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "minegpt"), EntityMineGPT.class,
                "minegpt", 1, this, 64, 3, true, 0x2E86C1, 0xD6EAF8);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandMineGPT());
    }
}
