package io.github.techtastic.dtedification.casting.actions.selectors;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.util.ThrowableRunnable;
import com.ferreusveritas.dynamictrees.worldgen.JoCode;
import io.github.techtastic.dtedification.casting.iota.TreeIota;
import io.github.techtastic.dtedification.casting.mishap.MishapHandler;
import io.github.techtastic.dtedification.casting.mishap.MishapMissingJoCode;
import io.github.techtastic.dtedification.casting.mishap.MishapNullSpecies;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class OpGetTrees implements ConstMediaAction {
    @Override
    public int getArgc() {
        return 2;
    }

    @Override
    public long getMediaCost() {
        return 0;
    }

    @NotNull
    @Override
    public List<Iota> execute(@NotNull List<? extends Iota> args, @NotNull CastingEnvironment env) {
        Vec3 vec = OperatorUtils.getVec3(args, 0, this.getArgc());
        double radius = OperatorUtils.getPositiveDouble(args, 1, this.getArgc());
        try {
            env.assertVecInRange(vec);
        } catch (MishapBadLocation e) {
            MishapHandler.INSTANCE.throwMishap(e);
        }

        HashMap<BlockPos, Iota> trees = new HashMap<>();
        AABB aabb = new AABB(vec.add(-radius, -radius, -radius), vec.add(radius, radius, radius));
        for (double x = aabb.minX; x <= aabb.maxX; x++) {
            for (double y = aabb.minY; y <= aabb.maxY; y++) {
                for (double z = aabb.minZ; z <= aabb.maxZ; z++) {
                    BlockPos pos = BlockPos.containing(x, y, z);
                    BlockPos rootPos = TreeHelper.findRootNode(env.getWorld(), pos);
                    if (trees.containsKey(rootPos)) continue;
                    Species species = TreeHelper.getBestGuessSpecies(env.getWorld(), rootPos);
                    if (species == Species.NULL_SPECIES) continue;
                    Optional<JoCode> code = TreeHelper.getJoCode(env.getWorld(), rootPos);
                    if (code.isEmpty()) continue;
                    TreeIota tree = new TreeIota(species, code.get());
                    trees.put(rootPos, tree);
                }
            }
        }

        return List.of(new ListIota(trees.values().stream().toList()));
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
