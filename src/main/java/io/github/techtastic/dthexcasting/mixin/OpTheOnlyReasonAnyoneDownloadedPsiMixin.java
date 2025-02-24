package io.github.techtastic.dthexcasting.mixin;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = { "at.petrak.hexcasting.common.casting.actions.spells.OpTheOnlyReasonAnyoneDownloadedPsi$Spell" })
public class OpTheOnlyReasonAnyoneDownloadedPsiMixin {
    @WrapOperation(method = "cast(Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/Item;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;"
    ))
    private InteractionResult dthexcasting$properlyApplyBonemeal(Item instance, UseOnContext context, Operation<InteractionResult> original) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Species species = TreeHelper.getExactSpecies(level, pos);
        BlockPos rootPos = TreeHelper.findRootNode(level, pos);

        if (species.isValid() && species.applySubstance(level, rootPos, rootPos, context.getPlayer(), context.getHand(), context.getItemInHand()))
            return InteractionResult.SUCCESS;
        return instance.useOn(context);
    }
}