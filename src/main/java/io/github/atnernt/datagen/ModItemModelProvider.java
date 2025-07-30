package io.github.atnernt.datagen;

import java.util.LinkedHashMap;

import io.github.atnernt.HotIronAdditions;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
		super(output, modid, existingFileHelper);
		
	}
	private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
			static {
		trimMaterials.put(TrimMaterials.QUARTZ, 0.1f);
		trimMaterials.put(TrimMaterials.IRON, 0.2f);
		trimMaterials.put(TrimMaterials.NETHERITE, 0.3f);
		trimMaterials.put(TrimMaterials.REDSTONE, 0.4f);
		trimMaterials.put(TrimMaterials.COPPER, 0.5f);
		trimMaterials.put(TrimMaterials.GOLD, 0.6f);
		trimMaterials.put(TrimMaterials.EMERALD, 0.7f);
		trimMaterials.put(TrimMaterials.DIAMOND, 0.8f);
		trimMaterials.put(TrimMaterials.LAPIS, 0.9f);
		trimMaterials.put(TrimMaterials.AMETHYST, 1.0f);		
	}
	
	@Override
	protected void registerModels() {
		simpleItem(HotIronAdditions.UNFIRED_IRON_MOLD_ITEM);
		simpleItem(HotIronAdditions.FIRED_IRON_MOLD_ITEM);
		
		simpleItem(HotIronAdditions.HOT_IRON_BUCKET_ITEM);
		simpleItem(HotIronAdditions.ROUGH_IRON_BUCKET_ITEM);
		
		simpleItem(HotIronAdditions.HOT_IRON_PLATE_ITEM);
		simpleItem(HotIronAdditions.ROUGH_IRON_PLATE_ITEM);
		
		simpleItem(HotIronAdditions.ROUGH_IRON_INGOT_ITEM);
		
		trimmedArmourItem(HotIronAdditions.BRIGANDINE_CHESTPLATE_ITEM);
		trimmedArmourItem(HotIronAdditions.BRIGANDINE_GREAVES_ITEM);
		trimmedArmourItem(HotIronAdditions.HEAVY_LEATHER_BOOTS_ITEM);

	}
	
	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0", 
				new ResourceLocation(HotIronAdditions.MODID, "/item/" + item.getId().getPath()));
	}

	
	private void trimmedArmourItem(RegistryObject<Item> itemRegistryObject) {
		final String MOD_ID = HotIronAdditions.MODID;
		
		if(itemRegistryObject.get() instanceof ArmorItem armourItem)
		{
			trimMaterials.entrySet().forEach(entry -> {
				
				ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
				float trimValue = entry.getValue();
				
				
				String armourType = switch(armourItem.getEquipmentSlot()) {
					case HEAD -> "helmet";
					case CHEST -> "chestplate";
					case LEGS -> "leggings";
					case FEET -> "boots";
					default -> "";
				};
				
				String armourItemPath = "item/" + armourItem;
				String trimPath = "trims/items/" + armourType + "_trim_" + trimMaterial.location().getPath();
				String currentTrimName = armourItemPath + "_" + trimMaterial.location().getPath() + "_trim";
				
				ResourceLocation armourItemResLoc = new ResourceLocation(MOD_ID, armourItemPath);
				ResourceLocation trimResLoc = new ResourceLocation(trimPath);
				ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);
				
				existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");
				
				getBuilder(currentTrimName)
				.parent(new ModelFile.UncheckedModelFile("item/generated"))
				.texture("layer0", armourItemResLoc)
				.texture("layer1",trimResLoc);
				
				this.withExistingParent(itemRegistryObject.getId().getPath(), mcLoc("item/generated"))
				.override()
				.model(new ModelFile.UncheckedModelFile(trimNameResLoc))
				.predicate(mcLoc("trim_type"), trimValue).end()
				.texture("layer0", new ResourceLocation(MOD_ID, "item/" + itemRegistryObject.getId().getPath()));	
			});
		}
	}

}
