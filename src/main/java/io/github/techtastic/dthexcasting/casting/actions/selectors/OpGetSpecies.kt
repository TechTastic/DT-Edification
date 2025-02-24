package io.github.techtastic.dthexcasting.casting.actions.selectors

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import com.ferreusveritas.dynamictrees.api.TreeHelper
import com.ferreusveritas.dynamictrees.tree.species.Species
import io.github.techtastic.dthexcasting.casting.iota.SpeciesIota
import io.github.techtastic.dthexcasting.casting.mishap.MishapNullSpecies

object OpGetSpecies : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val pos = args.getBlockPos(0, argc)
        env.assertPosInRange(pos)

        val species = TreeHelper.getBestGuessSpecies(env.world, pos)
        if (species == Species.NULL_SPECIES)
            throw MishapNullSpecies()

        return listOf(SpeciesIota(species))
    }
}