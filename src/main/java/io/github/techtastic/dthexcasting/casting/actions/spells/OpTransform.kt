package io.github.techtastic.dthexcasting.casting.actions.spells

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import com.ferreusveritas.dynamictrees.api.TreeHelper
import com.ferreusveritas.dynamictrees.api.network.MapSignal
import com.ferreusveritas.dynamictrees.compat.waila.WailaOther
import com.ferreusveritas.dynamictrees.systems.nodemapper.TransformNode
import com.ferreusveritas.dynamictrees.tree.species.Species
import com.ferreusveritas.dynamictrees.worldgen.JoCode
import io.github.techtastic.dthexcasting.casting.iota.SpeciesIota.Companion.getSpecies
import io.github.techtastic.dthexcasting.casting.mishap.MishapNonTransformableSpecies
import io.github.techtastic.dthexcasting.casting.mishap.MishapSameSpecies
import net.minecraft.core.BlockPos

object OpTransform : SpellAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val pos = args.getBlockPos(0, argc)
        val species = args.getSpecies(1, argc)

        env.assertPosInRange(pos)

        val rootPos = TreeHelper.findRootNode(env.world, pos)
        val fromSpecies = TreeHelper.getBestGuessSpecies(env.world, rootPos)
        if (species == fromSpecies)
            throw MishapSameSpecies(species, fromSpecies)
        if (!species.isTransformable)
            throw MishapNonTransformableSpecies(species)
        if (!fromSpecies.isTransformable)
            throw MishapNonTransformableSpecies(fromSpecies)

        return SpellAction.Result(
            Spell(rootPos, fromSpecies, species),
            TreeHelper.getJoCode(env.world, rootPos).orElse(JoCode("")).toString().length * MediaConstants.SHARD_UNIT,
            listOf(ParticleSpray.cloud(rootPos.center, 20.0, 20))
        )
    }

    class Spell(val rootPos: BlockPos, val fromSpecies: Species, val toSpecies: Species) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val rootyState = env.world.getBlockState(rootPos)
            val rootyBlock = TreeHelper.getRooty(rootyState)
            rootyBlock?.startAnalysis(env.world, rootPos, MapSignal(TransformNode(fromSpecies, toSpecies)))
            if (rootyBlock?.getSpecies(rootyState, env.world, rootPos) != toSpecies)
                toSpecies.placeRootyDirtBlock(env.world, rootPos, rootyBlock?.getFertility(rootyState, env.world, rootPos) ?: 0)
            WailaOther.invalidateWailaPosition()
        }

    }
}