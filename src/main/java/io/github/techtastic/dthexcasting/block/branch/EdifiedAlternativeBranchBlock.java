package io.github.techtastic.dthexcasting.block.branch;

import com.ferreusveritas.dynamictrees.block.branch.BasicBranchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EdifiedAlternativeBranchBlock extends BasicBranchBlock {
    public EdifiedAlternativeBranchBlock(ResourceLocation name, Properties properties) {
        super(name, properties);
    }

    @Override
    public boolean canBeStripped(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull ItemStack heldItem) {
        return true;
    }
}
