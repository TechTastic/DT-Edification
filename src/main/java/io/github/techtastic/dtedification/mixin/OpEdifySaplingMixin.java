package io.github.techtastic.dtedification.mixin;

import at.petrak.hexcasting.common.misc.AkashicTreeGrower;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.util.LevelContext;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import com.ferreusveritas.dynamictrees.worldgen.GenerationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

import static io.github.techtastic.dtedification.DTEdification.MOD_ID;

@Mixin(targets = { "at.petrak.hexcasting.common.casting.actions.spells.OpEdifySapling$Spell" })
public class OpEdifySaplingMixin {
    @Redirect(method = "cast(Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;)V", at = @At(
            value = "INVOKE",
            target = "Lat/petrak/hexcasting/common/misc/AkashicTreeGrower;growTree(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;)Z"
    ))
    private boolean dtedification$spawnDTTree(AkashicTreeGrower instance, ServerLevel level, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());

        Set<Species> species = Family.REGISTRY.get(new ResourceLocation(MOD_ID, "edified")).getSpecies();
        Species selected = (Species) species.toArray()[randomSource.nextInt(0, species.size())];
        GenerationContext context = new GenerationContext(
                LevelContext.create(level),
                selected,
                blockPos.below(),
                blockPos.below().mutable(),
                level.getBiome(blockPos),
                Direction.getRandom(randomSource),
                6,
                SafeChunkBounds.ANY
        );
        selected.getJoCode("JP").setCareful(true).generate(context);
        return true;
    }
}
