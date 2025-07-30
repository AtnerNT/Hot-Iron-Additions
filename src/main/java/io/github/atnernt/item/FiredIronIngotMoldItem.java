/**
 * 
 */
package io.github.atnernt.item;
import io.github.atnernt.HotIronAdditions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 
 */
public class FiredIronIngotMoldItem extends Item {

	public Item itemToGive = null;
	public FiredIronIngotMoldItem(Properties properties, Item itemToGive) {
		super(properties);
	
		properties.stacksTo(64);
		this.itemToGive = itemToGive;
	}
	
public Vec3 GetCenterFace(Direction clickedFace) {
	switch(clickedFace){
	case UP:
		return new Vec3(0.5f,1.0f,0.5f);
	case DOWN:
		return new Vec3(0.5f,-1.0f,0.5f);
	case WEST:
		return new Vec3(0.0f, 0.5f, 0.5f);
	case EAST:
		return new Vec3(1.0f, 0.5f, 0.5f);
	case NORTH:
		return new Vec3(0.5f, 0.5f, 0.0f);
	case SOUTH:
		return new Vec3(0.5f, 0.5f, 1.0f);
	default:
		return new Vec3(0.5f,1.0f,0.5f);
	}
}

public InteractionResult useOn(UseOnContext useContext) {
	
		//Get the world/level in which this interaction took place
		Level currentLevel = useContext.getLevel();
		//What block was clicked on (Position)?
		BlockPos blockPosition = useContext.getClickedPos();
		//Get the block at the clicked location using the above level
		BlockState blockState = currentLevel.getBlockState(blockPosition);

		 if(blockState.getTags().anyMatch(x -> x == BlockTags.MINEABLE_WITH_PICKAXE || x == BlockTags.LOGS) == false) {
			 return InteractionResult.FAIL;
		 }
		 
		//Consume the item
		useContext.getItemInHand().shrink(1);
		
		//Generic playsound action
		currentLevel.playSound((Player)null, blockPosition, SoundEvents.DECORATED_POT_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
		
		 if(currentLevel.isClientSide() == false) {
			//Generic Particles
			 Vec3 direction = GetCenterFace(useContext.getClickedFace());
			
			((ServerLevel) currentLevel).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.DECORATED_POT.defaultBlockState()),
		              blockPosition.getX() + direction.x, blockPosition.getY() + direction.y,
		              blockPosition.getZ() + direction.z, 10, 0, 0, 0, 1);
		 }
			
		Player player = useContext.getPlayer();
			
		player.getCooldowns().addCooldown(this, 15);
		
		//Create a new item (rough iron ingot)
		ItemStack newItem = new ItemStack(itemToGive, 1);
			
		//Try add the item directly to the user's inventory, however, if it fails, drop the item.
		if (player.getInventory().add(newItem) == false) {
	           player.drop(newItem, false);
	    }
			
		return InteractionResult.sidedSuccess(currentLevel.isClientSide);
}

@Override
public void appendHoverText(ItemStack itemStack, @Nullable Level levelContext, List<Component> toolTipComponents, TooltipFlag toolTipFlags) {
    if(Screen.hasShiftDown() == false) {
        toolTipComponents.add(Component.translatable("item.hot_iron_additions.tooltips.hold_shift"));
    } else {
    	toolTipComponents.add(Component.translatable("item.hot_iron_additions.tooltips.break_it"));
    }
    super.appendHoverText(itemStack, levelContext ,toolTipComponents, toolTipFlags);
}
	
}
