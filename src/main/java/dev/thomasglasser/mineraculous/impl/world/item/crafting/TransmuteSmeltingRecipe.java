package dev.thomasglasser.mineraculous.impl.world.item.crafting;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;

public class TransmuteSmeltingRecipe extends AbstractTransmuteCookingRecipe {

    public TransmuteSmeltingRecipe(RecipeType<?> type, String group, CookingBookCategory category, Ingredient ingredient, Item result, float experience, int cookingTime) {
        super(RecipeType.SMELTING, group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(Blocks.FURNACE);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MineraculousRecipeSerializers.TRANSMUTE_SMELTING.get();
    }
}
