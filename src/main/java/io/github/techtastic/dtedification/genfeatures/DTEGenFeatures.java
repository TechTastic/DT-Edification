package io.github.techtastic.dtedification.genfeatures;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import net.minecraft.resources.ResourceLocation;

import static io.github.techtastic.dtedification.DTEdification.MOD_ID;

public class DTEGenFeatures {
    public static final GenFeature EDIFIED_BRANCHES = new EdifiedBranchesGenFeature(new ResourceLocation(MOD_ID, "alt_branches"));

    public static void register(final Registry<GenFeature> registry) {
        registry.registerAll(EDIFIED_BRANCHES);
    }
}
