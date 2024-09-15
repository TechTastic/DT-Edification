package io.github.techtastic.dtedification.casting.actions.spells;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.misc.MediaConstants;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.block.rooty.RootyBlock;
import com.ferreusveritas.dynamictrees.compat.waila.WailaOther;
import com.ferreusveritas.dynamictrees.systems.nodemapper.TransformNode;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.worldgen.JoCode;
import io.github.techtastic.dtedification.casting.iota.TreeIota;
import io.github.techtastic.dtedification.casting.mishap.MishapHandler;
import io.github.techtastic.dtedification.casting.mishap.MishapMissingJoCode;
import io.github.techtastic.dtedification.casting.mishap.MishapNonTranformableSpecies;
import io.github.techtastic.dtedification.casting.mishap.MishapSameSpecies;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OpTransform implements SpellAction {
    @Override
    public int getArgc() {
        return 2;
    }

    @Override
    public boolean hasCastingSound(@NotNull CastingEnvironment castingEnvironment) {
        return DefaultImpls.hasCastingSound(this, castingEnvironment);
    }

    @Override
    public boolean awardsCastingStat(@NotNull CastingEnvironment castingEnvironment) {
        return DefaultImpls.awardsCastingStat(this, castingEnvironment);
    }

    @NotNull
    @Override
    public Result execute(@NotNull List<? extends Iota> args, @NotNull CastingEnvironment env) {
        Vec3 vec = OperatorUtils.getVec3(args, 0, this.getArgc());
        Iota iota = args.get(1);
        if (iota.getType() != TreeIota.TYPE)
            MishapHandler.INSTANCE.throwMishap(new MishapInvalidIota(iota, 1, TreeIota.display(Species.NULL_SPECIES, new JoCode(""))));
        TreeIota tree = (TreeIota) iota;

        BlockPos rootPos = TreeHelper.findRootNode(env.getWorld(), BlockPos.containing(vec.x, vec.y, vec.z));
        Species fromSpecies = TreeHelper.getBestGuessSpecies(env.getWorld(), rootPos);
        if (tree.getSpecies() == fromSpecies)
            MishapHandler.INSTANCE.throwMishap(new MishapSameSpecies(fromSpecies, tree.getSpecies()));
        if (!tree.getSpecies().isTransformable())
            MishapHandler.INSTANCE.throwMishap(new MishapNonTranformableSpecies(tree.getSpecies()));
        if (!fromSpecies.isTransformable())
            MishapHandler.INSTANCE.throwMishap(new MishapNonTranformableSpecies(fromSpecies));

        return new Result(new Spell(rootPos, fromSpecies, tree.getSpecies()), TreeHelper.getJoCode(env.getWorld(), rootPos).orElse(new JoCode("")).toString().length() * MediaConstants.SHARD_UNIT, List.of(ParticleSpray.cloud(rootPos.getCenter(), 20, 20)), 1);
    }

    @NotNull
    @Override
    public Result executeWithUserdata(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment, @NotNull CompoundTag compoundTag) {
        return DefaultImpls.executeWithUserdata(this, list, castingEnvironment, compoundTag);
    }

    @NotNull
    @Override
    public OperationResult operate(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage, @NotNull SpellContinuation spellContinuation) {
        return DefaultImpls.operate(this, castingEnvironment, castingImage, spellContinuation);
    }

    class Spell implements RenderedSpell {
        BlockPos rootPos;
        Species fromSpecies;
        Species toSpecies;

        public Spell(BlockPos rootPos, Species fromSpecies, Species toSpecies) {
            this.rootPos = rootPos;
            this.fromSpecies = fromSpecies;
            this.toSpecies = toSpecies;
        }

        @Override
        public void cast(@NotNull CastingEnvironment env) {
            BlockState rootyState = env.getWorld().getBlockState(rootPos);
            RootyBlock rootyBlock = TreeHelper.getRooty(rootyState);
            rootyBlock.startAnalysis(env.getWorld(), rootPos, new MapSignal(new TransformNode(fromSpecies, toSpecies)));
            if (rootyBlock.getSpecies(rootyState, env.getWorld(), rootPos) != toSpecies)
                toSpecies.placeRootyDirtBlock(env.getWorld(), rootPos, rootyBlock.getFertility(rootyState, env.getWorld(), rootPos));
            WailaOther.invalidateWailaPosition();
        }

        @Nullable
        @Override
        public CastingImage cast(@NotNull CastingEnvironment castingEnvironment, @NotNull CastingImage castingImage) {
            return DefaultImpls.cast(this, castingEnvironment, castingImage);
        }
    }
}
