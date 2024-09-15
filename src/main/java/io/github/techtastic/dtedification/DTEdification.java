package io.github.techtastic.dtedification;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.mojang.logging.LogUtils;
import io.github.techtastic.dtedification.casting.IotaTypeRegistry;
import io.github.techtastic.dtedification.casting.PatternRegistry;
import io.github.techtastic.dtedification.init.DTERegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DTEdification.MOD_ID)
public class DTEdification {
    public static final String MOD_ID = "dtedification";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DTEdification() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::gatherData);

        PatternRegistry.register(modEventBus);
        IotaTypeRegistry.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        RegistryHandler.setup(MOD_ID);
        DTERegistries.setup();
    }

    private void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherAllData(MOD_ID, event,
                Family.REGISTRY
        );
    }
}