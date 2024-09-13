package io.github.techtastic.dtedification.trees;

import at.petrak.hexcasting.common.lib.HexBlocks;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.branch.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.init.DTConfigs;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.util.Optionals;
import com.ferreusveritas.dynamictrees.util.ResourceLocationUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.EffectDurationFix;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
                () -> new BasicBranchBlock(name, this.getProperties()).setCanBeStripped(true));
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

    public EdifiedFamily setupDrops() {
        this.amethystBranch.get().setPrimitiveLogDrops(new ItemStack(HexBlocks.EDIFIED_LOG_AMETHYST));
        this.aventurineBranch.get().setPrimitiveLogDrops(new ItemStack(HexBlocks.EDIFIED_LOG_AVENTURINE));
        this.citrineBranch.get().setPrimitiveLogDrops(new ItemStack(HexBlocks.EDIFIED_LOG_CITRINE));
        this.purpleBranch.get().setPrimitiveLogDrops(new ItemStack(HexBlocks.EDIFIED_LOG_PURPLE));
        return this;
    }
}
