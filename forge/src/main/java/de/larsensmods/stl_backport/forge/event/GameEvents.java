package de.larsensmods.stl_backport.forge.event;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.block.STLBlocks;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = SpringToLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameEvents {

    public static final VillagerTrades.ItemListing DRY_GRASS_TRADE = (trader, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(STLBlocks.TALL_DRY_GRASS.get()), 12, 0, 0);
    public static final VillagerTrades.ItemListing WILDFLOWERS_TRADE = (trader, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(STLBlocks.WILDFLOWERS.get()), 12, 0, 0);
    public static final  VillagerTrades.ItemListing FIREFLY_BUSH_TRADE = (trader, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(STLBlocks.FIREFLY_BUSH.get()), 12, 0, 0);

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(Set.of(DRY_GRASS_TRADE, WILDFLOWERS_TRADE));
        event.getRareTrades().add(FIREFLY_BUSH_TRADE);
    }

}
