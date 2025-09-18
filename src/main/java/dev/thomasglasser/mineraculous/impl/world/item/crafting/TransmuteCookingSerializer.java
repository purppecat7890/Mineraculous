package dev.thomasglasser.mineraculous.impl.world.item.crafting;

import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class TransmuteCookingSerializer<T extends AbstractTransmuteCookingRecipe> implements RecipeSerializer<T> {
    private final AbstractTransmuteCookingRecipe.Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public TransmuteCookingSerializer(AbstractTransmuteCookingRecipe.Factory<T> factory, int cookingTime) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(
                p_300831_ -> p_300831_.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(p_300832_ -> p_300832_.group),
                                CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(p_300828_ -> p_300828_.category),
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(p_300833_ -> p_300833_.ingredient),
                                ItemStack.CODEC.fieldOf("result").forGetter(p_300827_ -> p_300827_.result),
                                Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(p_300826_ -> p_300826_.experience),
                                Codec.INT.fieldOf("cookingtime").orElse(cookingTime).forGetter(p_300834_ -> p_300834_.cookingTime)
                        )
                        .apply(p_300831_, factory::create)
        );
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    private T fromNetwork(RegistryFriendlyByteBuf buffer) {
        String s = buffer.readUtf();
        CookingBookCategory cookingbookcategory = buffer.readEnum(CookingBookCategory.class);
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Item item = ByteBufCodecs.registry(Registries.ITEM).decode(buffer);
        float f = buffer.readFloat();
        int i = buffer.readVarInt();
        return this.factory.create(s, cookingbookcategory, ingredient, item, f, i);
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        buffer.writeEnum(recipe.category());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeFloat(recipe.getExperience());
        buffer.writeVarInt(recipe.getCookingTime());
    }

    public AbstractTransmuteCookingRecipe create(
            String group, CookingBookCategory category, Ingredient ingredient, Item result, float experience, int cookingTime
    ) {
        return this.factory.create(group, category, ingredient, result, experience, cookingTime);
    }
}
