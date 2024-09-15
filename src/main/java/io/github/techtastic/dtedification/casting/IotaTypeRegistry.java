package io.github.techtastic.dtedification.casting;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.HexRegistries;
import io.github.techtastic.dtedification.casting.iota.TreeIota;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static io.github.techtastic.dtedification.DTEdification.MOD_ID;

public class IotaTypeRegistry {
    private static final DeferredRegister<IotaType<?>> IOTAS = DeferredRegister.create(HexRegistries.IOTA_TYPE, MOD_ID);

    public static final RegistryObject<IotaType<TreeIota>> TREE = IOTAS.register("tree", () -> TreeIota.TYPE);

    public static void register(IEventBus bus) {
        IOTAS.register(bus);
    }
}
