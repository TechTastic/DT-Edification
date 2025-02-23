package io.github.techtastic.dthexcasting;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.mojang.logging.LogUtils;
import io.github.techtastic.dthexcasting.init.DTHActionRegistry;
import io.github.techtastic.dthexcasting.init.DTHIotaRegistry;
import io.github.techtastic.dthexcasting.init.DTHRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DTHexcasting.MOD_ID)
public class DTHexcasting {
    public static final String MOD_ID = "dthexcasting";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DTHexcasting() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::gatherData);
        modEventBus.register(DTHRegistries.class);

        MinecraftForge.EVENT_BUS.register(this);

        RegistryHandler.setup(MOD_ID);
        DTHRegistries.setup();

        DTHActionRegistry.register(modEventBus);
        DTHIotaRegistry.register(modEventBus);
    }

    private void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherAllData(MOD_ID, event, Family.REGISTRY);
    }
}