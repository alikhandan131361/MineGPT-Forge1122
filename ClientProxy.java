package com.minegpt.client;

import com.minegpt.core.CommonProxy;
import com.minegpt.entity.EntityMineGPT;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMineGPT.class,
                (RenderManager manager) -> new RenderBiped(manager, new ModelBiped(), 0.45F));
    }
}
