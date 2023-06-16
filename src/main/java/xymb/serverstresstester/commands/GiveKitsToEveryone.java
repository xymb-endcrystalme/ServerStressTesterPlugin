package xymb.serverstresstester.commands;

import xymb.serverstresstester.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.block.ShulkerBox;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public final class GiveKitsToEveryone extends Command implements PluginIdentifiableCommand {
    private final Main plugin;

    public GiveKitsToEveryone(final Main plugin) {
        super("stressgivekitstoeveryone");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 0) {
            sender.sendRichMessage("???");
            return false;
        }
long start = System.nanoTime();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            fillInventoryWithShulkerBoxesOfSwords(player);
        }

System.out.println("AAAA " + (System.nanoTime() - start)/1000000.);
        return true;
    }

    public ItemStack createSword() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta swordMeta = sword.getItemMeta();

        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        swordMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        swordMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
        swordMeta.addEnchant(Enchantment.SWEEPING_EDGE, 3, true);
        swordMeta.addEnchant(Enchantment.MENDING, 1, true);

        sword.setItemMeta(swordMeta);

        return sword;
    }

    public ItemStack createShulkerBoxOfSwords(ItemStack sword) {
        ItemStack shulker = new ItemStack(Material.SHULKER_BOX, 1);
        BlockStateMeta blockMeta = (BlockStateMeta) shulker.getItemMeta();
        ShulkerBox shulkerBox = (ShulkerBox) blockMeta.getBlockState();

        for(int i=0; i<27; i++) {
            shulkerBox.getInventory().setItem(i, sword);
        }

        blockMeta.setBlockState(shulkerBox);
        shulker.setItemMeta(blockMeta);

        return shulker;
    }

    public void fillInventoryWithShulkerBoxesOfSwords(Player player) {
        ItemStack sword = createSword();
        ItemStack shulker = createShulkerBoxOfSwords(sword);

        for (int i = 0; i < 36; i++) {
            player.getInventory().setItem(i, shulker);
        }
    }

    public void giveEnchantedArmorAndTotem(Player player) {
        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemStack[] armor = {boots, leggings, chestplate, helmet};

        for (ItemStack piece : armor) {
            ItemMeta meta = piece.getItemMeta();
            // Set the enchantments to the maximum level
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
            meta.addEnchant(Enchantment.MENDING, 1, true);

            // Add specific enchantments for helmet and boots
            if (piece.getType() == Material.DIAMOND_HELMET) {
                meta.addEnchant(Enchantment.OXYGEN, 3, true);
                meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
            }
            if (piece.getType() == Material.DIAMOND_BOOTS) {
                meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
                meta.addEnchant(Enchantment.FROST_WALKER, 2, true);
                meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
            }
            piece.setItemMeta(meta);
        }

        // Add the armor to the player's inventory
        player.getInventory().setArmorContents(armor);

        // Give the player a totem
        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING, 1);
        player.getInventory().setItemInOffHand(totem);
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }
}
