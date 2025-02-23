package io.github.techtastic.dthexcasting.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import com.ferreusveritas.dynamictrees.tree.species.Species
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel

class SpeciesIota(payload: Species) : Iota(TYPE, payload) {
    fun getSpecies() = payload as Species

    override fun isTruthy() = getSpecies() != Species.NULL_SPECIES && getSpecies().isValid

    override fun toleratesOther(other: Iota?) = false

    override fun serialize(): Tag {
        val tag = CompoundTag()
        val species = getSpecies()
        tag.putString("species", species.registryName.toString())
        return tag
    }

    companion object {
        val TYPE = object : IotaType<SpeciesIota>() {
            override fun deserialize(tag: Tag, level: ServerLevel?): SpeciesIota? {
                val tag = tag as CompoundTag
                val speciesName = ResourceLocation(tag.getString("species"))
                if (!Species.REGISTRY.has(speciesName))
                    return null
                return SpeciesIota(Species.REGISTRY.get(speciesName))
            }

            override fun display(tag: Tag): Component {
                val species = deserialize(tag, null)?.getSpecies()
                    ?: return Component.literal("How do you have a null SpeciesIota...")
                return Component.translatable("dthexcasting.iota.species", species.localizedName)
            }

            override fun color() = 0x00FF00
        }

        fun List<Iota>.getSpecies(idx: Int, argc: Int): Species {
            val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
            if (x is SpeciesIota)
                return x.getSpecies()
            throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "species")
        }
    }
}