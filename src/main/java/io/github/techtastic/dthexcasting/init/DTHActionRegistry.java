package io.github.techtastic.dthexcasting.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;
import io.github.techtastic.dthexcasting.casting.actions.OpGetJoCode;
import io.github.techtastic.dthexcasting.casting.actions.selectors.OpGetSpecies;
import io.github.techtastic.dthexcasting.casting.actions.spells.OpTransform;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.units.qual.A;

import static io.github.techtastic.dthexcasting.DTHexcasting.MOD_ID;

public class DTHActionRegistry {
    private static final DeferredRegister<ActionRegistryEntry> ACTIONS = DeferredRegister.create(HexRegistries.ACTION, MOD_ID);

    public static final RegistryObject<ActionRegistryEntry> GET_SPECIES = ACTIONS.register("get_species", () ->
            new ActionRegistryEntry(HexPattern.fromAngles("wedewaqawa", HexDir.NORTH_WEST), OpGetSpecies.INSTANCE));

    public static final RegistryObject<ActionRegistryEntry> GET_JOCODE = ACTIONS.register("get_jocode", () ->
            new ActionRegistryEntry(HexPattern.fromAngles("wqaqwdedwd", HexDir.NORTH_EAST), OpGetJoCode.INSTANCE));

    public static final RegistryObject<ActionRegistryEntry> TRANSFORM = ACTIONS.register("transform", () ->
            new ActionRegistryEntry(HexPattern.fromAngles("wqaqwqwqaqwqwqaqwqwqaqwqwqaqwqwqaqw", HexDir.NORTH_EAST), OpTransform.INSTANCE));

    public static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
