package io.github.techtastic.dthexcasting.mixin;

import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.TrunkShellBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = { "at.petrak.hexcasting.common.casting.actions.spells.OpBreakBlock$Spell" })
public class OpBreakBlockMixin {
    @WrapOperation(method = "cast(Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;destroyBlock(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/entity/Entity;)Z"
    ))
    private boolean dthexcasting$breakTreesProperly(ServerLevel instance, BlockPos blockPos, boolean b, Entity entity, Operation<Boolean> original) {
        BlockState state = instance.getBlockState(blockPos);
        if (state.getBlock() instanceof BranchBlock branch) {
            return branch.removedByEntity(state, instance, blockPos, (LivingEntity) entity);
        } else if (state.getBlock() instanceof TrunkShellBlock trunk) {
            return trunk.onDestroyedByPlayer(state, instance, blockPos, (Player) entity, true, instance.getFluidState(blockPos));
        }

        return original.call(instance, blockPos, b, entity);
    }
}
