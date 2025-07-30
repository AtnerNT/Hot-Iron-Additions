/**
 * 
 */
package io.github.atnernt.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;

public class HotItem extends Item {

	public HotItem(Properties properties) {
		super(properties);
		
		properties.stacksTo(64);
	}
	
public void inventoryTick(ItemStack itemStack, Level currentLevel, Entity playerEntity, int p_41407_, boolean p_41408_) {
	 if(currentLevel.isClientSide() == false) {
		playerEntity.setRemainingFireTicks(3);
		 playerEntity.hurt(currentLevel.damageSources().onFire(), 1);
	 }
	 
}


@Override
public void appendHoverText(ItemStack itemStack, @Nullable Level levelContext, List<Component> toolTipComponents, TooltipFlag toolTipFlags) {
    if(Screen.hasShiftDown() == false) {
        toolTipComponents.add(Component.translatable("item.hot_iron_additions.tooltips.hold_shift"));
    } else {
    	toolTipComponents.add(Component.translatable("item.hot_iron_additions.tooltips.pick_it"));
    }
    super.appendHoverText(itemStack, levelContext ,toolTipComponents, toolTipFlags);
}
	
}
