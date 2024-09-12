package io.github.techtastic.dtedification.trees;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.common.lib.HexBlocks;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.branch.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.util.Optionals;
import com.ferreusveritas.dynamictrees.util.ResourceLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.function.Supplier;

public class ExtraLogFamily extends Family {
    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(ExtraLogFamily::new);

    protected Supplier<BranchBlock> amethystBranch;
    protected Block amethystLogBlock = HexBlocks.EDIFIED_LOG_AMETHYST;

    protected Supplier<BranchBlock> aventurineBranch;
    protected Block aventurineLogBlock = HexBlocks.EDIFIED_LOG_AVENTURINE;

    protected Supplier<BranchBlock> citrineBranch;
    protected Block citrineLogBlock = HexBlocks.EDIFIED_LOG_CITRINE;

    public ExtraLogFamily(ResourceLocation name) {
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
                branch.setPrimitiveLogDrops(new ItemStack(amethystLogBlock));
            else if (name.getPath().contains("aventurine"))
                branch.setPrimitiveLogDrops(new ItemStack(aventurineLogBlock));
            else
                branch.setPrimitiveLogDrops(new ItemStack(citrineLogBlock));
            return branch;
        });
    }

    public Optional<Block> getAmethystLogBlock() {
        return Optionals.ofBlock(amethystLogBlock);
    }
    public Optional<Block> getAventurineLogBlock() {
        return Optionals.ofBlock(aventurineLogBlock);
    }
    public Optional<Block> getCitrineLogBlock() {
        return Optionals.ofBlock(citrineLogBlock);
    }

    public Family setAmethystLogBlock(Block amethystLogBlock) {
        this.amethystLogBlock = amethystLogBlock;

        if (amethystBranch != null)
            amethystBranch.get().setPrimitiveLogDrops(new ItemStack(amethystLogBlock));

        return this;
    }
    public Family setAventurineLogBlock(Block aventurineLogBlock) {
        this.aventurineLogBlock = aventurineLogBlock;

        if (aventurineBranch != null)
            aventurineBranch.get().setPrimitiveLogDrops(new ItemStack(aventurineLogBlock));

        return this;
    }
    public Family setCitrineLogBlock(Block citrineLogBlock) {
        this.citrineLogBlock = citrineLogBlock;

        if (citrineBranch != null)
            citrineBranch.get().setPrimitiveLogDrops(new ItemStack(citrineLogBlock));

        return this;
    }
}
