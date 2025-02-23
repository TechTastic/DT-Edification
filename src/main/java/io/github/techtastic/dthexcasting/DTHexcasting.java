package io.github.techtastic.dthexcasting;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.mojang.logging.LogUtils;
import io.github.techtastic.dthexcasting.genfeatures.DTEGenFeatures;
import io.github.techtastic.dthexcasting.init.DTERegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
        modEventBus.register(DTERegistries.class);

        MinecraftForge.EVENT_BUS.register(this);

        RegistryHandler.setup(MOD_ID);
        DTERegistries.setup();
    }

    private void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherAllData(MOD_ID, event, Family.REGISTRY);
    }
}