package io.github.techtastic.dtedification.trees;

import at.petrak.hexcasting.common.lib.HexBlocks;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.branch.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.util.Optionals;
import com.ferreusveritas.dynamictrees.util.ResourceLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Supplier;

public class EdifiedFamily extends Family {
    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(EdifiedFamily::new);

    protected Supplier<BranchBlock> amethystBranch;
    protected Supplier<BranchBlock> aventurineBranch;
    protected Supplier<BranchBlock> citrineBranch;

    public EdifiedFamily(ResourceLocation name) {
        super(name);
    }

    @Override
    public void setupBlocks() {
        super.setupBlocks();
        this.amethystBranch = this.setupBranch(this.createBranch(ResourceLocationUtils.suffix(this.getRegistryName(), "_amethyst")), false);
        this.aventurineBranch = this.setupBranch(this.createBranch(ResourceLocationUtils.suffix(this.getRegistryName(), "_aventurine")), false);
        this.citrineBranch = this.setupBranch(this.createBranch(ResourceLocationUtils.suffix(this.getRegistryName(), "_citrine")), false);
    }

    protected Supplier<BranchBlock> createBranch(ResourceLocation name) {
        return RegistryHandler.addBlock(ResourceLocationUtils.suffix(name, getBranchNameSuffix()),
                () -> {
            BasicBranchBlock branch = new BasicBranchBlock(name, this.getProperties());
            if (name.getPath().contains("amethyst"))
                branch.setPrimitiveLogDrops(new ItemStack(HexBlocks.EDIFIED_LOG_AMETHYST));
            else if (name.getPath().contains("aventurine"))
                branch.setPrimitiveLogDrops(new ItemStack(HexBlocks.EDIFIED_LOG_AVENTURINE));
            else
                branch.setPrimitiveLogDrops(new ItemStack(HexBlocks.EDIFIED_LOG_CITRINE));
            return branch;
        });
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
}
