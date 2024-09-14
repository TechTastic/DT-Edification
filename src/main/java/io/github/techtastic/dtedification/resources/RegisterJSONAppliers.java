package io.github.techtastic.dtedification.resources;

import com.ferreusveritas.dynamictrees.api.applier.ApplierRegistryEvent;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.google.gson.JsonElement;
import io.github.techtastic.dtedification.trees.EdifiedFamily;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static io.github.techtastic.dtedification.DTEdification.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterJSONAppliers {
    @SubscribeEvent
    public static void registerAppliersFamily(final ApplierRegistryEvent.Reload<Family, JsonElement> event) {
        event.getAppliers().register("has_alt_branch", EdifiedFamily.class, (_o, edifiedFamily) -> edifiedFamily.setupDrops());
    }

    @SubscribeEvent
    public static void registerAppliersFamily(final ApplierRegistryEvent.Setup<Family, JsonElement> event) {
        event.getAppliers().register("has_alt_branch", EdifiedFamily.class, (_o, edifiedFamily) -> edifiedFamily.setupDrops());
    }

    @SubscribeEvent
    public static void registerAppliersFamily(final ApplierRegistryEvent.Common<Family, JsonElement> event) {
        event.getAppliers().register("has_alt_branch", EdifiedFamily.class, (_o, edifiedFamily) -> edifiedFamily.setupDrops());
    }

    @SubscribeEvent
    public static void registerAppliersFamily(final ApplierRegistryEvent.GatherData<Family, JsonElement> event) {
        event.getAppliers().register("has_alt_branch", EdifiedFamily.class, (_o, edifiedFamily) -> edifiedFamily.setupDrops());
    }
}
