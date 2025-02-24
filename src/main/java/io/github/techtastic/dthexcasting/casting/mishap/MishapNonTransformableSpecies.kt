package io.github.techtastic.dthexcasting.casting.mishap

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import com.ferreusveritas.dynamictrees.tree.species.Species
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor

class MishapNonTransformableSpecies(val species: Species) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context) = dyeColor(DyeColor.BROWN)

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) =
        Component.translatable("dthexcasting.mishap.species.transformable", species.localizedName)

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {}
}