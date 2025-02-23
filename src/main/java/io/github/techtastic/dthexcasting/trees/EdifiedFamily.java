package io.github.techtastic.dthexcasting.trees;

import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.branch.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.compat.waila.WailaOther;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.util.Optionals;
import com.ferreusveritas.dynamictrees.util.ResourceLocationUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public class EdifiedFamily extends Family {
    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(EdifiedFamily::new);

    protected Supplier<BranchBlock> amethystBranch;
    protected Supplier<BranchBlock> aventurineBranch;
    protected Supplier<BranchBlock> citrineBranch;
    protected Supplier<BranchBlock> purpleBranch;

    public EdifiedFamily(ResourceLocation name) {
        super(name);
    }

    @Override
    public void setupBlocks() {
        super.setupBlocks();
        this.amethystBranch = this.setupBranch(this.createBranch(ResourceLocationUtils.suffix(this.getRegistryName(), "_amethyst")), false);
        this.aventurineBranch = this.setupBranch(this.createBranch(ResourceLocationUtils.suffix(this.getRegistryName(), "_aventurine")), false);
        this.citrineBranch = this.setupBranch(this.createBranch(ResourceLocationUtils.suffix(this.getRegistryName(), "_citrine")), false);
        this.purpleBranch = this.setupBranch(this.createBranch(ResourceLocationUtils.suffix(this.getRegistryName(), "_purple")), false);
    }

    protected Supplier<BranchBlock> createBranch(ResourceLocation name) {
        return RegistryHandler.addBlock(ResourceLocationUtils.suffix(name, getBranchNameSuffix()),
                () -> {
                    var branch = new BasicBranchBlock(name, this.getProperties());
                    branch.setFamily(this);
                    branch.setCanBeStripped(true);
                    addValidBranches(branch);
                    return branch;
                });
    }

    @Override
    public boolean stripBranch(BlockState state, Level level, BlockPos pos, Player player, ItemStack heldItem) {
        if (this.hasStrippedBranch()) {
            BranchBlock branch = state.is(amethystBranch.get()) ? getAmethystBranch().orElse(null) :
                    state.is(aventurineBranch.get()) ? getAventurineBranch().orElse(null) :
                            state.is(citrineBranch.get()) ? getCitrineBranch().orElse(null) :
                                    state.is(purpleBranch.get()) ? getPurpleBranch().orElse(null) :
                                            getBranch().orElse(null);
            if (branch != null && !branch.isStrippedBranch()) {
                branch.stripBranch(state, level, pos, player, heldItem);
                if (level.isClientSide) {
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    WailaOther.invalidateWailaPosition();
                }
            }
            return this.getBranch().isPresent();
        }

        return false;
    }

    public Optional<BranchBlock> getAmethystBranch() {
        return Optionals.ofBlock(amethystBranch.get());
    }

    public Optional<BranchBlock> getAventurineBranch() {
        return Optionals.ofBlock(aventurineBranch.get());
    }

    public Optional<BranchBlock> getCitrineBranch() {
        return Optionals.ofBlock(citrineBranch.get());
    }

    public Optional<BranchBlock> getPurpleBranch() {
        return Optionals.ofBlock(purpleBranch.get());
    }
}
