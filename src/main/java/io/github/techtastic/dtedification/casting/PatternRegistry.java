package io.github.techtastic.dtedification.casting;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;
import io.github.techtastic.dtedification.casting.actions.selectors.OpGetTreeAt;
import io.github.techtastic.dtedification.casting.actions.selectors.OpGetTrees;
import io.github.techtastic.dtedification.casting.actions.spells.OpTransform;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static io.github.techtastic.dtedification.DTEdification.MOD_ID;

public class PatternRegistry {
    private static final DeferredRegister<ActionRegistryEntry> ACTIONS = DeferredRegister.create(HexRegistries.ACTION, MOD_ID);

    public static final RegistryObject<ActionRegistryEntry> GET_TREE_AT = ACTIONS.register("get_tree_at", () ->
            new ActionRegistryEntry(HexPattern.fromAngles("wedewa", HexDir.NORTH_WEST), new OpGetTreeAt()));
    public static final RegistryObject<ActionRegistryEntry> GET_TREES = ACTIONS.register("get_trees", () ->
            new ActionRegistryEntry(HexPattern.fromAngles("wweeeeewaqwd", HexDir.NORTH_EAST), new OpGetTrees()));
    public static final RegistryObject<ActionRegistryEntry> TRANSFORM = ACTIONS.register("transform", () ->
            new ActionRegistryEntry(HexPattern.fromAngles("wqaqwewqaqwd", HexDir.NORTH_EAST), new OpTransform()));

    public static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
