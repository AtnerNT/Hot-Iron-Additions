/**
 * 
 */
package io.github.atnernt.item;
import io.github.atnernt.HotIronAdditions;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 
 */
public class FiredIronIngotMoldItem extends Item {

	public FiredIronIngotMoldItem(Properties properties) {
		super(properties);
		
		properties.stacksTo(64);
		
	}
	

public InteractionResult useOn(UseOnContext useContext) {
	
		//Get the world/level in which this interaction took place
		Level currentLevel = useContext.getLevel();
		//What block was clicked on (Position)?
		BlockPos blockPosition = useContext.getClickedPos();
		//Get the block at the clicked location using the above level
		BlockState blockState = currentLevel.getBlockState(blockPosition);
		
		//Check the tags of the block, see if its mineable with a pickaxe
		if(blockState.getTags().anyMatch(x -> x == BlockTags.MINEABLE_WITH_PICKAXE || x == BlockTags.LOGS)) {
			
			//Consume the item
			useContext.getItemInHand().shrink(1);
			
			//Generic playsound action
			currentLevel.playSound((Player)null, blockPosition, SoundEvents.DECORATED_POT_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
			Player player = useContext.getPlayer();
			
			//Create a new item (rough iron ingot)
			ItemStack newItem = new ItemStack(HotIronAdditions.ROUGH_IRON_INGOT_ITEM.get(), 1);
			
			//Try add the item directly to the user's inventory, however, if it fails, drop the item.
			if (player.getInventory().add(newItem) == false) {
	               player.drop(newItem, false);
	        }
			
			//This interaction will succeed if being done on the client
			InteractionResult.sidedSuccess(currentLevel.isClientSide);
		}
	      return InteractionResult.PASS;
	}
	
}
