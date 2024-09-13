package io.github.techtastic.dtedification.mixin;

import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.TrunkShellBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = { "at.petrak.hexcasting.common.casting.actions.spells.OpBreakBlock$Spell" })
public class OpBreakBlockMixin {
    @Redirect(method = "cast(Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;destroyBlock(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/entity/Entity;)Z"
    ))
    private boolean dtedification$breakTreesProperly(ServerLevel instance, BlockPos blockPos, boolean b, Entity entity) {
        BlockState state = instance.getBlockState(blockPos);
        if (state.getBlock() instanceof BranchBlock branch) {
            return branch.removedByEntity(state, instance, blockPos, (LivingEntity) entity);
        }

        if (state.getBlock() instanceof TrunkShellBlock trunk) {
            return trunk.onDestroyedByPlayer(state, instance, blockPos, (Player) entity, true, instance.getFluidState(blockPos));
        }

        return instance.destroyBlock(blockPos, b, entity);
    }
}
