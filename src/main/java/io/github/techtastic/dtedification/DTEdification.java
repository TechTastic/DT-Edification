package io.github.techtastic.dtedification;

import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DTEdification.MOD_ID)
public class DTEdification
{
    public static final String MOD_ID = "dtedification";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DTEdification() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        RegistryHandler.setup(MOD_ID);
    }
}
