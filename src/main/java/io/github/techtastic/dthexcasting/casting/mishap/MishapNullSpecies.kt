package io.github.techtastic.dthexcasting.casting.mishap

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor

class MishapNullSpecies : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context) = dyeColor(DyeColor.BLACK)

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context) =
        Component.translatable("dthexcasting.mishap.species.null")

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {}
}