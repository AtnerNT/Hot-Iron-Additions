package io.github.atnernt.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.regen.hotiron.init.HotIronModItems;

import java.util.function.Supplier;

import io.github.atnernt.HotIronAdditions;

public enum ModArmourMaterials implements ArmorMaterial { 
	BRIGANDINE("brigandine", 13, new int[]{	2, 5, 3, 2}, 12, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 0, () -> Ingredient.of(HotIronModItems.IRON_PLATE.get())) ;
	
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantmentValue;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Supplier<Ingredient> repairIngredient;
	
	private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };
	
	ModArmourMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, 
			SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantmentValue = enchantmentValue;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = repairIngredient;
	}
	
	@Override
	public int getDurabilityForType(ArmorItem.Type pType){
		return BASE_DURABILITY[pType.ordinal()] * durabilityMultiplier;
	}
	
	@Override
	public int getDefenseForType(ArmorItem.Type pType){
		return protectionAmounts[pType.ordinal()];
	}
	
	@Override
	public int getEnchantmentValue(){
		return this.enchantmentValue;
	}
	
	@Override
	public SoundEvent getEquipSound(){
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	@Override
	public String getName() {
		return HotIronAdditions.MODID + ":" + this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
	
    
}