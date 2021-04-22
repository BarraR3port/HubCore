package cl.bebt.hubcore.menu;

import cl.bebt.hubcore.main;
import cl.bebt.hubcore.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Menu implements InventoryHolder {
    
    protected Inventory inventory;
    
    protected PlayerMenuUtility playerMenuUtility;
    
    public Menu( PlayerMenuUtility playerMenuUtility ){
        this.playerMenuUtility = playerMenuUtility;
    }
    
    protected ItemStack redPanel( ){
        ItemStack panel = new ItemStack( Material.RED_STAINED_GLASS_PANE );
        ItemMeta panel_meta = panel.getItemMeta( );
        panel_meta.setDisplayName( " " );
        panel_meta.getPersistentDataContainer( ).set( new NamespacedKey( main.plugin , "panel" ) , PersistentDataType.STRING , "panel" );
        panel.setItemMeta( panel_meta );
        return panel;
    }
    
    protected ItemStack greenPanel( ){
        ItemStack panel = new ItemStack( Material.LIME_STAINED_GLASS_PANE );
        ItemMeta panel_meta = panel.getItemMeta( );
        panel_meta.setDisplayName( " " );
        panel_meta.getPersistentDataContainer( ).set( new NamespacedKey( main.plugin , "panel" ) , PersistentDataType.STRING , "panel" );
        panel.setItemMeta( panel_meta );
        return panel;
    }
    
    protected ItemStack bluePanel( ){
        ItemStack panel = new ItemStack( Material.CYAN_STAINED_GLASS_PANE );
        ItemMeta panel_meta = panel.getItemMeta( );
        panel_meta.setDisplayName( " " );
        panel_meta.getPersistentDataContainer( ).set( new NamespacedKey( main.plugin , "panel" ) , PersistentDataType.STRING , "panel" );
        panel.setItemMeta( panel_meta );
        return panel;
    }
    
    public abstract String getMenuName( );
    
    public abstract int getSlots( );
    
    public abstract void handleMenu( InventoryClickEvent e );
    
    public abstract void setMenuItemsPlayer( Player p );
    
    public void open( Player p ){
        inventory = Bukkit.createInventory( this , getSlots( ) , getMenuName( ) );
        
        //grab all the items specified to be used for this menu and add to inventory
        this.setMenuItemsPlayer( p );
        //open the inventory for the player
        playerMenuUtility.getOwner( ).openInventory( inventory );
        
    }
    
    public ItemStack makeItem( Material material , String displayName , String... lore ){
        
        ItemStack item = new ItemStack( material );
        ItemMeta itemMeta = item.getItemMeta( );
        itemMeta.setDisplayName( utils.chat( displayName ) );
        itemMeta.setLore( Arrays.asList( lore ) );
        if ( item.getType( ).equals( Material.BARRIER ) ) {
            ArrayList < String > go_back = new ArrayList < String >( );
            go_back.add( utils.chat( "&aRight click to close" ) );
            go_back.add( utils.chat( "&aLeft click to go back" ) );
            itemMeta.setLore( go_back );
            itemMeta.getPersistentDataContainer( ).set( new NamespacedKey( main.plugin , "BARRIER" ) , PersistentDataType.STRING , "BARRIER" );
        }
        item.setItemMeta( itemMeta );
        
        return item;
    }
    
    public ItemStack makeItem( Material material , String displayName , ArrayList < String > lore ){
        ItemStack item = new ItemStack( material );
        ItemMeta itemMeta = item.getItemMeta( );
        itemMeta.setDisplayName( utils.chat( displayName ) );
        itemMeta.setLore( lore );
        item.setItemMeta( itemMeta );
        return item;
    }
    
    public static ItemStack makeItem( String material , String displayName , Boolean enchanted , String server , ArrayList < String > lore ){
        ItemStack item = new ItemStack( Material.valueOf( material ) );
        ItemMeta itemMeta = item.getItemMeta( );
        itemMeta.setDisplayName( utils.chat( displayName ) );
        itemMeta.setLore( lore );
        
        itemMeta.getPersistentDataContainer( ).set( new NamespacedKey( main.plugin , "server" ) , PersistentDataType.STRING , server );
        if ( enchanted ) {
            itemMeta.addEnchant( Enchantment.DAMAGE_ALL , 1 , false );
            itemMeta.addItemFlags( ItemFlag.HIDE_ENCHANTS );
        }
        item.setItemMeta( itemMeta );
        return item;
    }
    
    @Override
    public Inventory getInventory( ){
        return inventory;
    }
    
}
