package cl.bebt.hubcore.commands;

import cl.bebt.hubcore.main;
import cl.bebt.hubcore.utils.UpdateChecker;
import cl.bebt.hubcore.utils.utils;
import cl.bebt.staffcore.API.StaffCoreAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class hubcore implements TabExecutor {
    private final main plugin;
    
    public hubcore( main plugin ){
        this.plugin = plugin;
        plugin.getCommand( "hubcore" ).setExecutor( this );
    }
    
    public List < String > onTabComplete( CommandSender sender , Command command , String alias , String[] args ){
        if ( args.length == 1 ) {
            List < String > version = new ArrayList <>( );
            version.add( "version" );
            version.add( "reload" );
            return version;
        }
        return null;
    }
    
    public boolean onCommand( CommandSender sender , Command cmd , String label , String[] args ){
        
        if ( !(sender instanceof Player) ) {
            if ( args.length == 0 ) {
                utils.tell( sender , " " );
                utils.tell( sender , " " );
                utils.tell( sender , "&aMysql: &d" + StaffCoreAPI.mysqlEnabled( ) );
                utils.tell( sender , " " );
                utils.tell( sender , " " );
                utils.tell( sender , " " );
                utils.tell( sender , " " );
                utils.tell( sender , " " );
                utils.tell( sender , " " );
            }
            
            if ( args.length == 1 ) {
                if ( args[0].equalsIgnoreCase( "version" ) ) {
                    utils.tell( sender , " " );
                    utils.tell( sender , "                 &bAuthor: &5" + this.plugin.getDescription( ).getAuthors( ) );
                    utils.tell( sender , " " );
                    utils.tell( sender , "                 &aHub Core Version: &7" + this.plugin.getDescription( ).getVersion( ) );
                    (new UpdateChecker( this.plugin , 82324 )).getLatestVersion( version -> {
                        if ( this.plugin.getDescription( ).getVersion( ).equalsIgnoreCase( version ) ) {
                            utils.tell( sender , "&a                 You are using the latest version!" );
                        } else {
                            ComponentBuilder cb = new ComponentBuilder( utils.chat( "&aClick to download!" ) );
                            TextComponent v = new TextComponent( utils.chat( "&c                 Hey, there is a new version out!" ) );
                            v.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT , cb.create( ) ) );
                            v.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL , "https://www.spigotmc.org/resources/hubcore.82324/" ) );
                            sender.spigot( ).sendMessage( v );
                        }
                    } );
                    utils.tell( sender , " " );
                } else if ( args[0].equalsIgnoreCase( "reload" ) ) {
                    this.plugin.reloadConfig( );
                    utils.tell( sender , "&aHubCore Reloaded!" );
                } else if ( args[0].equalsIgnoreCase( "vanished" ) ) {
                    int i = 0;
                    utils.tell( sender , " " );
                    utils.tell( sender , "&b                     &5Vanished Players:" );
                    utils.tell( sender , " " );
                    for ( Player people : Bukkit.getOnlinePlayers( ) ) {
                        if ( people.getPersistentDataContainer( ).has( new NamespacedKey( this.plugin , "vanished" ) , PersistentDataType.STRING ) ) {
                            ComponentBuilder cb = new ComponentBuilder( utils.chat( "&aClick to teleport!" ) );
                            TextComponent v = new TextComponent( utils.chat( "&a            &r" + people.getDisplayName( ) ) );
                            v.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT , cb.create( ) ) );
                            v.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND , "/tp " + people.getName( ) ) );
                            sender.spigot( ).sendMessage( v );
                            i++;
                        }
                    }
                    if ( i == 0 )
                        utils.tell( sender , "             &cNo players vanished!" );
                    utils.tell( sender , " " );
                    
                } else if ( args[0].equalsIgnoreCase( "staff" ) ) {
                    int i = 0;
                    utils.tell( sender , " " );
                    utils.tell( sender , "&b                 &5Players in Staff mode:" );
                    utils.tell( sender , " " );
                    for ( Player people : Bukkit.getOnlinePlayers( ) ) {
                        if ( people.getPersistentDataContainer( ).has( new NamespacedKey( this.plugin , "staff" ) , PersistentDataType.STRING ) ) {
                            ComponentBuilder cb = new ComponentBuilder( utils.chat( "&aClick to teleport!" ) );
                            TextComponent v = new TextComponent( utils.chat( "&a            &r" + people.getDisplayName( ) ) );
                            v.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT , cb.create( ) ) );
                            v.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND , "/tp " + people.getName( ) ) );
                            sender.spigot( ).sendMessage( v );
                            i++;
                        }
                    }
                    if ( i == 0 )
                        utils.tell( sender , "             &cNo players in staff mode!" );
                    utils.tell( sender , " " );
                    
                }
            } else if ( !args[0].equalsIgnoreCase( "version" ) && !args[0].equalsIgnoreCase( "vanished" ) && !args[0].equalsIgnoreCase( "staff" ) ) {
                utils.tell( sender , this.plugin.getConfig( ).getString( "server_prefix" ) + "&9/hubcore version/vanished/staff/reload" );
            }
        } else if ( sender.hasPermission( "hubcore.staff" ) ) {
            if ( args.length == 0 ) {
                utils.tell( sender , " " );
                utils.tell( sender , "                 &6&5StaffCore  &6" );
                utils.tell( sender , " " );
                utils.tell( sender , "          &aVersion: &d" + this.plugin.getDescription( ).getVersion( ) );
                utils.tell( sender , "          &aAuthor: &d" + this.plugin.getDescription( ).getAuthors( ) );
                utils.tell( sender , "          &aMysql: &d" + StaffCoreAPI.mysqlEnabled( ) );
                utils.tell( sender , "          &aBungeeCord: &d" + utils.getBoolean( "bungeecord.enabled" ) );
                //utils.tell( sender,"          &aServer Version: &d" + StaffCoreAPI.getServerVersion() );
                //utils.tell( sender,"          &aTPS: &d" + ( int ) StaffCoreAPI.getTPS( ) );
                utils.tell( sender , " " );
            } else if ( args.length == 1 ) {
                if ( args[0].equalsIgnoreCase( "version" ) ) {
                    utils.tell( sender , " " );
                    utils.tell( sender , "                 &bAuthor: &5" + this.plugin.getDescription( ).getAuthors( ) );
                    utils.tell( sender , " " );
                    utils.tell( sender , "                 &aStaff Core Version: &7" + this.plugin.getDescription( ).getVersion( ) );
                    (new UpdateChecker( this.plugin , 82324 )).getLatestVersion( version -> {
                        if ( this.plugin.getDescription( ).getVersion( ).equalsIgnoreCase( version ) ) {
                            utils.tell( sender , "&a                 You are using the latest version!" );
                        } else {
                            ComponentBuilder cb = new ComponentBuilder( utils.chat( "&aClick to download!" ) );
                            TextComponent v = new TextComponent( utils.chat( "&c                 Hey, there is a new version out!" ) );
                            v.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT , cb.create( ) ) );
                            v.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL , "https://www.spigotmc.org/resources/hubcore.82324/" ) );
                            sender.spigot( ).sendMessage( v );
                        }
                    } );
                    utils.tell( sender , " " );
                } else if ( args[0].equalsIgnoreCase( "reload" ) ) {
                    if ( sender.hasPermission( "hubcore.reload" ) ) {
                        this.plugin.reloadConfig( );
                        utils.tell( sender , "&aStaffCore Reload!" );
                    } else {
                        utils.tell( sender , this.plugin.getConfig( ).getString( "server_prefix" ) + this.plugin.getConfig( ).getString( "no_permissions" ) );
                    }
                } else if ( args[0].equalsIgnoreCase( "vanished" ) ) {
                    Player p = ( Player ) sender;
                    int i = 0;
                    p.sendMessage( " " );
                    utils.tell( p , "&b                     &5Vanished Players:" );
                    p.sendMessage( " " );
                    for ( Player people : Bukkit.getOnlinePlayers( ) ) {
                        if ( people.getPersistentDataContainer( ).has( new NamespacedKey( this.plugin , "vanished" ) , PersistentDataType.STRING ) ) {
                            ComponentBuilder cb = new ComponentBuilder( utils.chat( "&aClick to teleport!" ) );
                            TextComponent v = new TextComponent( utils.chat( "&a            &r" + people.getDisplayName( ) ) );
                            v.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT , cb.create( ) ) );
                            v.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND , "/tp " + people.getName( ) ) );
                            sender.spigot( ).sendMessage( v );
                            i++;
                        }
                    }
                    if ( i == 0 )
                        utils.tell( sender , "             &cNo players vanished!" );
                    utils.tell( sender , " " );
                    
                } else if ( args[0].equalsIgnoreCase( "staff" ) ) {
                    Player p = ( Player ) sender;
                    int i = 0;
                    p.sendMessage( " " );
                    utils.tell( p , "&b                 &5Players in Staff mode:" );
                    p.sendMessage( " " );
                    for ( Player people : Bukkit.getOnlinePlayers( ) ) {
                        if ( people.getPersistentDataContainer( ).has( new NamespacedKey( this.plugin , "staff" ) , PersistentDataType.STRING ) ) {
                            ComponentBuilder cb = new ComponentBuilder( utils.chat( "&aClick to teleport!" ) );
                            TextComponent v = new TextComponent( utils.chat( "&a            &r" + people.getDisplayName( ) ) );
                            v.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT , cb.create( ) ) );
                            v.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND , "/tp " + people.getName( ) ) );
                            sender.spigot( ).sendMessage( v );
                            i++;
                        }
                    }
                    if ( i == 0 )
                        utils.tell( sender , "             &cNo players in staff mode!" );
                    utils.tell( sender , " " );
                    
                } else if ( !args[0].equalsIgnoreCase( "version" ) && !args[0].equalsIgnoreCase( "vanished" ) && !args[0].equalsIgnoreCase( "staff" ) ) {
                    utils.tell( sender , this.plugin.getConfig( ).getString( "server_prefix" ) + "&7Use &9/hubcore version/vanished/staff/reload" );
                }
            }
        } else {
            utils.tell( sender , this.plugin.getConfig( ).getString( "server_prefix" ) + this.plugin.getConfig( ).getString( "no_permissions" ) );
        }
        
        return false;
    }
}