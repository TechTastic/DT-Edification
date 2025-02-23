package io.github.techtastic.dthexcasting.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import com.ferreusveritas.dynamictrees.api.TreeHelper
import com.ferreusveritas.dynamictrees.worldgen.JoCode
import ram.talia.moreiotas.api.casting.iota.StringIota

object OpGetJoCode : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val pos = args.getBlockPos(0, argc)
        env.assertPosInRange(pos)

        val joCode = TreeHelper.getJoCode(env.world, pos).orElse(JoCode(""))
        return listOf(StringIota.make(joCode.toString()))
    }
}