package io.github.techtastic.dtedification.genfeatures;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.configuration.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.api.network.NodeInspector;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGrowContext;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import io.github.techtastic.dtedification.trees.EdifiedFamily;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EdifiedBranchesGenFeature extends GenFeature {
    public static final ConfigurationProperty<String> TYPE = ConfigurationProperty.string("type");

    public EdifiedBranchesGenFeature(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(TYPE, PLACE_CHANCE);
    }

    @Override
    protected @NotNull GenFeatureConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(TYPE, "AMETHYST")
                .with(PLACE_CHANCE, 0.125f);
    }

    @Override
    protected boolean postGrow(@NotNull GenFeatureConfiguration configuration, PostGrowContext context) {
        if (context.fertility() == 0 || !(context.species().getFamily() instanceof EdifiedFamily family)) return false;

        LevelAccessor level = context.level();
        BlockPos rootPos = context.pos();
        final BlockState blockState = level.getBlockState(rootPos.above());
        final BranchBlock branch = TreeHelper.getBranch(blockState);

        if (branch != null && context.natural()) {
            if (level.getRandom().nextFloat() < configuration.get(PLACE_CHANCE)) {
                placeAltBranches(false, configuration, level, rootPos, family);
            }
        }

        return true;
    }

    @Override
    protected boolean postGenerate(GenFeatureConfiguration configuration, PostGenerationContext context) {
        LevelAccessor level = context.level();
        BlockPos rootPos = context.pos();
        final BlockState blockState = level.getBlockState(rootPos.above());
        final BranchBlock branch = TreeHelper.getBranch(blockState);

        if (branch != null && context.species().getFamily() instanceof EdifiedFamily family) {
            placeAltBranches(true, configuration, level, rootPos, family);
        }

        return true;
    }

    private void placeAltBranches(boolean isWorldgen, GenFeatureConfiguration configuration, LevelAccessor world, BlockPos rootPos, EdifiedFamily family) {
        SimpleWeightedRandomList.Builder<BlockPos> listBuilder = new SimpleWeightedRandomList.Builder<>();
        final FindValidBranchesNode altBranchPlacer = new FindValidBranchesNode(listBuilder, family);
        TreeHelper.startAnalysisFromRoot(world, rootPos, new MapSignal(altBranchPlacer));
        SimpleWeightedRandomList<BlockPos> validSpots = listBuilder.build();

        if (validSpots.isEmpty()) return;

        if (isWorldgen){
            for (BlockPos listPos : validSpots.unwrap().stream().map(WeightedEntry.Wrapper::getData).toList())
                if (world.getRandom().nextFloat() < configuration.get(PLACE_CHANCE))
                    placeBranch(configuration, world, listPos, family);
        } else {
            WeightedEntry.Wrapper<BlockPos> posWrapper = validSpots.getRandom(world.getRandom()).orElse(null);
            if (posWrapper == null) return;
            placeBranch(configuration, world, posWrapper.getData(), family);
        }
    }

    private void placeBranch(GenFeatureConfiguration configuration, LevelAccessor world, BlockPos pos, EdifiedFamily family) {
        BranchBlock branchToPlace = (switch (EdifiedTrees.valueOf(configuration.get(TYPE))) {
            case AMETHYST -> family.getAmethystBranch();
            case AVENTURINE -> family.getAventurineBranch();
            case CITRINE -> family.getCitrineBranch();
            case PURPLE -> family.getPurpleBranch();
        }).orElse(family.getValidBranchBlock(0));
        int radius = TreeHelper.getRadius(world, pos);

        branchToPlace.setRadius(world, pos, radius, null);
    }

    public static class FindValidBranchesNode implements NodeInspector {

        private final SimpleWeightedRandomList.Builder<BlockPos> validSpots;
        private final Family family;

        public FindValidBranchesNode(SimpleWeightedRandomList.Builder<BlockPos> validSpots, Family family) {
            this.validSpots = validSpots;
            this.family = family;
        }

        @Override
        public boolean run(BlockState state, LevelAccessor level, BlockPos pos, Direction fromDir) {
            int radius = TreeHelper.getRadius(level, pos);
            boolean valid = state.getBlock() == family.getValidBranchBlock(0);
            if (valid) validSpots.add(pos, radius);
            return valid;
        }

        @Override
        public boolean returnRun(BlockState state, LevelAccessor level, BlockPos pos, Direction fromDir) {
            return false;
        }
    }

    public enum EdifiedTrees {
        AMETHYST,
        AVENTURINE,
        CITRINE,
        PURPLE
    }
}
