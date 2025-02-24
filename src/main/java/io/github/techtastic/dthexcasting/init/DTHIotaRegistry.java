package io.github.techtastic.dthexcasting.init;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.HexRegistries;
import io.github.techtastic.dthexcasting.casting.iota.SpeciesIota;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static io.github.techtastic.dthexcasting.DTHexcasting.MOD_ID;

public class DTHIotaRegistry {
    private static final DeferredRegister<IotaType<?>> IOTAS = DeferredRegister.create(HexRegistries.IOTA_TYPE, MOD_ID);

    public static final RegistryObject<IotaType<SpeciesIota>> SPECIES =
            IOTAS.register("species", SpeciesIota.Companion::getTYPE);

    public static void register(IEventBus bus) {
        IOTAS.register(bus);
    }
}
