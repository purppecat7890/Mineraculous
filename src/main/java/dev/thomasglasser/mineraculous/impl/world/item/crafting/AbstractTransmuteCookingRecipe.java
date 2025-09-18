package dev.thomasglasser.mineraculous.impl.world.item.crafting;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public abstract class AbstractTransmuteCookingRecipe extends AbstractCookingRecipe {

    public AbstractTransmuteCookingRecipe(RecipeType<?> type, String group, CookingBookCategory category, Ingredient ingredient, Item result, float experience, int cookingTime) {
        super(type, group, category, ingredient, result.getDefaultInstance(), experience, cookingTime);
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return input.item().transmuteCopy(result.getItem(), 1);
    }

    public interface Factory<T extends AbstractTransmuteCookingRecipe> {
        T create(String group, CookingBookCategory category, Ingredient ingredient, Item result, float experience, int cookingTime);
    }
}
