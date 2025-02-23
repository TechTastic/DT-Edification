package io.github.techtastic.dthexcasting.init;

import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import io.github.techtastic.dthexcasting.genfeatures.DTEGenFeatures;
import io.github.techtastic.dthexcasting.trees.EdifiedFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static io.github.techtastic.dthexcasting.DTHexcasting.MOD_ID;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class DTERegistries {

    public static void setup() {}

    @SubscribeEvent
    public static void onGenFeatureRegistry(final RegistryEvent<GenFeature> event) {
        DTEGenFeatures.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerFamilyTypes (final TypeRegistryEvent<Family> event) {
        event.registerType(new ResourceLocation(MOD_ID, "edified"), EdifiedFamily.TYPE);
    }
}
