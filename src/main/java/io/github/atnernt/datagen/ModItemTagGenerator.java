package io.github.atnernt.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import net.regen.hotiron.HotIronMod;
import net.regen.hotiron.init.HotIronModItems;
import net.regen.hotiron.init.HotIronModScreens;
import net.regen.hotiron.init.HotIronModBlocks;
import net.regen.hotiron.init.HotIronModConfigs;
import io.github.atnernt.HotIronAdditions;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagGenerator extends ItemTagsProvider {

	public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<Provider> p_275729_,
			CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_275343_, p_275729_, p_275322_, HotIronAdditions.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider p_256380_) {
		this.tag(ItemTags.TRIMMABLE_ARMOR).add(HotIronAdditions.BRIGANDINE_CHESTPLATE_ITEM.get(), 
				HotIronAdditions.BRIGANDINE_GREAVES_ITEM.get(), 
				HotIronAdditions.HEAVY_LEATHER_BOOTS_ITEM.get());
		
	
	}

}
