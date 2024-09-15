package io.github.techtastic.dtedification.casting.actions.selectors;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.worldgen.JoCode;
import io.github.techtastic.dtedification.casting.iota.TreeIota;
import io.github.techtastic.dtedification.casting.mishap.MishapHandler;
import io.github.techtastic.dtedification.casting.mishap.MishapMissingJoCode;
import io.github.techtastic.dtedification.casting.mishap.MishapNullSpecies;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class OpGetTreeAt implements ConstMediaAction {
    @Override
    public int getArgc() {
        return 1;
    }

    @Override
    public long getMediaCost() {
        return 0;
    }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> args, @NotNull CastingEnvironment env) {
        Vec3 vec = OperatorUtils.getVec3(args, 0, this.getArgc());
        try {
            env.assertVecInRange(vec);
        } catch (MishapBadLocation e)  {
            MishapHandler.INSTANCE.throwMishap(e);
        }
        BlockPos pos = BlockPos.containing(vec.x, vec.y, vec.z);

        Species species = TreeHelper.getBestGuessSpecies(env.getWorld(), pos);
        if (species == Species.NULL_SPECIES)
            MishapHandler.INSTANCE.throwMishap(new MishapNullSpecies(species));

        Optional<JoCode> code = TreeHelper.getJoCode(env.getWorld(), pos);
        if (code.isEmpty())
            MishapHandler.INSTANCE.throwMishap(new MishapMissingJoCode());

        return List.of(new TreeIota(species, code.get()));
    }

    @NotNull
    @Override
    public CostMediaActionResult executeWithOpCount(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) {
        return DefaultImpls.executeWithOpCount(this, list, castingEnvironment);
    }

    @NotNull
    @Override
    public OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {
        return DefaultImpls.operate(this, castingEnvironment, castingImage, spellContinuation);
    }
}
