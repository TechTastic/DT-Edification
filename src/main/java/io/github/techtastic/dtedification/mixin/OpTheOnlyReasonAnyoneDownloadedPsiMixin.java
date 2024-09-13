package io.github.techtastic.dtedification.mixin;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.TrunkShellBlock;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(targets = { "at.petrak.hexcasting.common.casting.actions.spells.OpTheOnlyReasonAnyoneDownloadedPsi$Spell" })
public class OpTheOnlyReasonAnyoneDownloadedPsiMixin {
    @Redirect(method = "cast(Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/Item;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;"
    ))
    private InteractionResult dtedification$properlyApplyBonemeal(Item instance, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Species species = TreeHelper.getExactSpecies(level, pos);
        BlockPos rootPos = TreeHelper.findRootNode(level, pos);

        if (species.isValid() && species.applySubstance(level, rootPos, rootPos, context.getPlayer(), context.getHand(), context.getItemInHand()))
            return InteractionResult.SUCCESS;
        return instance.useOn(context);
    }
}
