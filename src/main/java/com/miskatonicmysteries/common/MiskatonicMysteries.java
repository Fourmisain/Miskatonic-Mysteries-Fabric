package com.miskatonicmysteries.common;

import com.miskatonicmysteries.common.handler.InsanityHandler;
import com.miskatonicmysteries.common.handler.PacketHandler;
import com.miskatonicmysteries.common.lib.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class MiskatonicMysteries implements ModInitializer {
    public static CommonConfig config;

    static {
        AutoConfig.register(CommonConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(CommonConfig.class).getConfig(); //is this even a good idea
    }

    @Override
    public void onInitialize() {
        ModObjects.init();
        ModEntities.init();
        ModRegistries.init();
        ModRecipes.init();
        InsanityHandler.init();
        PacketHandler.registerC2S();
        ModWorld.init();
    }
}
