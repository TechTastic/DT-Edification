package io.github.techtastic.dtedification.casting.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.worldgen.JoCode;
import io.github.techtastic.dtedification.casting.IotaTypeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TreeIota extends Iota {
    public static IotaType<TreeIota> TYPE = new IotaType<>() {
        @Override
        public @NotNull TreeIota deserialize(Tag tag, ServerLevel serverLevel) throws IllegalArgumentException {
            return TreeIota.deserialize(tag);
        }

        @Override
        public Component display(Tag tag) {
            TreeIota tree = TreeIota.deserialize(tag);
            return TreeIota.display(tree.getSpecies(), tree.getJoCode());
        }

        @Override
        public int color() {
            return 0x3a8a3aFF;
        }
    };



    public TreeIota(@NotNull Species species, @NotNull JoCode code) {
        super(IotaTypeRegistry.TREE.get(), new Tree(species.getRegistryName().toString(), code.toString()));
    }



    public Species getSpecies() {
        return Species.REGISTRY.get(ResourceLocation.tryParse(((Tree) this.payload).species));
    }

    public JoCode getJoCode() {
        return new JoCode(((Tree) this.payload).code);
    }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        return typesMatch(this, that)
                && that instanceof TreeIota tree
                && this.getSpecies().equals(tree.getSpecies())
                && Objects.equals(this.getJoCode().toString(), tree.getJoCode().toString());
    }

    @Override
    public @NotNull Tag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putString("dtedification$species", ((Tree) this.payload).species);
        tag.putString("dtedification$code", ((Tree) this.payload).code);
        return tag;
    }

    public static TreeIota deserialize(Tag tag) {
        CompoundTag compound = (CompoundTag) tag;

        Species species = Species.REGISTRY.get(ResourceLocation.tryParse(compound.getString("dtedification$species")));
        JoCode code = new JoCode(compound.getString("dtedification$code"));

        return new TreeIota(species, code);
    }

    public static Component display(Species species, JoCode code) {
        return Component.literal(String.format("Tree{%s, ", species.getLocalizedName()))
                .append(code.getTextComponent()).append("}");
    }

    public record Tree(String species, String code) { }
}
