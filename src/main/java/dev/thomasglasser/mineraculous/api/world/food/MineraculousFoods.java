package dev.thomasglasser.mineraculous.api.world.food;

import net.minecraft.world.food.FoodProperties;

public class MineraculousFoods {
    /// Base food properties of a cheese wedge
    public static final FoodProperties CHEESE = new FoodProperties.Builder().nutrition(2).build();
    /// Food properties of a camembert wedge, like cheese but slightly saturating
    public static final FoodProperties CAMEMBERT = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build();
    /// Food properties of macarons
    public static final FoodProperties MACARON = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25F).build();
}
