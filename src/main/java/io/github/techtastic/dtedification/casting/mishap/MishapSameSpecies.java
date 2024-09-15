package io.github.techtastic.dtedification.casting.mishap;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.api.utils.HexUtils;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MishapSameSpecies extends Mishap {
    Species firstSpecies;
    Species secondSpecies;

    public MishapSameSpecies(Species firstSpecies, Species secondSpecies) {
        this.firstSpecies = firstSpecies;
        this.secondSpecies = secondSpecies;
    }

    @NotNull
    @Override
    public FrozenPigment accentColor(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
        return dyeColor(DyeColor.GREEN);
    }

    @Override
    public void execute(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context, @NotNull List<Iota> list) {
        castingEnvironment.getMishapEnvironment().blind(10);
    }

    @Nullable
    @Override
    protected Component errorMessage(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
        return HexUtils.asTranslatedComponent("dtedification.mishap.bad_species.same", this.firstSpecies.getLocalizedName(), this.secondSpecies.getLocalizedName());
    }
}
