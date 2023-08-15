package net.abraxator.moresnifferflowers.init;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;

public class ModFoods {
    public static final FoodComponent DAWNBERRY = new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).build();
    public static final FoodComponent DRAGONFLY = new FoodComponent.Builder().hunger(1).saturationModifier(0.3F).snack().meat().build();
}
