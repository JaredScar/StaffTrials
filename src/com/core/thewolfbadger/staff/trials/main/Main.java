package com.core.thewolfbadger.staff.trials.main;

import com.core.thewolfbadger.staff.trials.api.TrialType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created with IntelliJ IDEA.
 * User: TheWolfBadger
 * Date: 7/11/14
 * Time: 8:53 AM
 */
public class Main extends JavaPlugin {
    private FileConfiguration settings;
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        this.settings = getConfig();
        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().length >= 1) {
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if(settings.contains("Players."+players.getUniqueId().toString())) {
                            if(settings.contains("Players."+players.getUniqueId().toString()+".TimeExpired") && settings.contains("Players."+players.getUniqueId().toString()+".Time")) {
                                if(settings.contains("Players."+players.getUniqueId().toString()+".Type")) {
                                    if(settings.contains("Players."+players.getUniqueId().toString()+".Command")) {
                                        settings.set("Players."+players.getUniqueId().toString()+".Time", settings.getInt("Players."+players.getUniqueId().toString()+".Time")+1);
                                        saveConfig();
                                        int time = settings.getInt("Players."+players.getUniqueId().toString()+".Time");
                                        int timeExpired = settings.getInt("Players."+players.getUniqueId().toString()+".TimeExpired");
                                        switch (TrialType.valueOf(settings.getString("Players."+players.getUniqueId().toString()+".Type"))) {
                                            case DAY:
                                                int toEqual = timeExpired * 86400;
                                                if(time >= toEqual) {
                                                    String command = settings.getString("Players."+players.getUniqueId().toString()+".Command");
                                                    getServer().dispatchCommand(getServer().getConsoleSender(), command);
                                                    settings.set("Players."+players.getUniqueId().toString(), null);
                                                    saveConfig();
                                                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getString("Messages.TrialExpired")));
                                                }
                                                break;
                                            case HOUR:
                                                int toEqual1 = timeExpired * 3600;
                                                if(time >= toEqual1) {
                                                    String command = settings.getString("Players."+players.getUniqueId().toString()+".Command");
                                                    getServer().dispatchCommand(getServer().getConsoleSender(), command);
                                                    settings.set("Players."+players.getUniqueId().toString(), null);
                                                    saveConfig();
                                                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getString("Messages.TrialExpired")));
                                                }
                                                break;
                                            case MINUTE:
                                                int toEqual2 = timeExpired * 60;
                                                if(time >= toEqual2) {
                                                    String command = settings.getString("Players."+players.getUniqueId().toString()+".Command");
                                                    getServer().dispatchCommand(getServer().getConsoleSender(), command);
                                                    settings.set("Players."+players.getUniqueId().toString(), null);
                                                    saveConfig();
                                                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getString("Messages.TrialExpired")));
                                                }
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 0, 20);
    }
    public void onDisable() {}
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("sTrial")) {
            if(sender.hasPermission("ServerTrials.Admin")) {
            switch (args.length) {
                case 0:
                    //Help menu
                    sender.sendMessage(ChatColor.YELLOW+"StaffTrials is a plugin made by TheWolfBadger on dev.bukkit.org (http://dev.bukkit.org/profiles/TheWolfBadger).");
                    sender.sendMessage(ChatColor.YELLOW+"It is a plugin meant to manage staff trials.");
                    sender.sendMessage(ChatColor.GRAY+"TrialTypes: DAY, HOUR, MINUTE");
                    sender.sendMessage(ChatColor.GRAY+"Commands should be the last value when you add a trial and it should not include any /. Commands can include spaces also.");
                    sender.sendMessage(ChatColor.GREEN+"Get started creating Trials? /sTrial add <TrialType> <Time> <Player> <Command>");
                    sender.sendMessage(ChatColor.RED+"Removing Trials? /sTrial cancel <player>");
                    sender.sendMessage(ChatColor.YELLOW+"Enjoy it :)");
                    break;
                case 1:
                    //Null
                    sender.sendMessage(ChatColor.RED+"Invalid Usage. "+ChatColor.GREEN+"Correct: /sTrial add <TrialType> <Time> <Player> <Command>");
                    break;
                case 2:
                    //Null
                    if(args[0].equalsIgnoreCase("cancel")) {
                        Player p = Bukkit.getPlayer(args[1]);
                        sender.sendMessage(ChatColor.GRAY+"Attempting to cancel trial of player "+ChatColor.YELLOW+args[1]+ChatColor.GRAY+"...");
                        if(p !=null) {
                            //Online
                            if(settings.contains("Players."+p.getUniqueId().toString())) {
                            getServer().dispatchCommand(getServer().getConsoleSender(), settings.getString("Players."+p.getUniqueId().toString()+".Command"));
                            settings.set("Players."+p.getUniqueId().toString(), null);
                            saveConfig();
                            sender.sendMessage(ChatColor.GREEN+"The player "+ChatColor.YELLOW+args[1]+ChatColor.GREEN+" has been removed from their Trial!");
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED+"ERROR: "+ChatColor.RED+"The player "+ChatColor.YELLOW+args[1]+ChatColor.RED+" is not currently in a trial!");
                            }
                        } else {
                            //Offline
                            OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);
                            if(settings.contains("Players."+pl.getUniqueId().toString())) {
                                getServer().dispatchCommand(getServer().getConsoleSender(), settings.getString("Players."+pl.getUniqueId().toString()+".Command"));
                                settings.set("Players."+pl.getUniqueId().toString(), null);
                                saveConfig();
                                sender.sendMessage(ChatColor.GREEN+"The player "+ChatColor.YELLOW+args[1]+ChatColor.GREEN+" has been removed from their Trial!");
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED+"ERROR: "+ChatColor.RED+"The player "+ChatColor.YELLOW+args[1]+ChatColor.RED+" is not currently in a trial!");
                            }
                        }
                    } else {
                    sender.sendMessage(ChatColor.RED+"Invalid Usage. "+ChatColor.GREEN+"Correct: /sTrial add <TrialType> <Time> <Player> <Command>");
                    }
                    break;
                case 3:
                    //Null
                    sender.sendMessage(ChatColor.RED+"Invalid Usage. "+ChatColor.GREEN+"Correct: /sTrial add <TrialType> <Time> <Player> <Command>");
                    break;
                case 4:
                    //Null
                    sender.sendMessage(ChatColor.RED+"Invalid Usage. "+ChatColor.GREEN+"Correct: /sTrial add <TrialType> <Time> <Player> <Command>");
                    break;
            }
            if(args.length >= 4) {
                //Right
                //sTrial add <TrialType> <Time> <Player> <Command>
                if(args[0].equalsIgnoreCase("add")) {
                    if(TrialType.valueOf(args[1]) !=null) {
                        if(Integer.valueOf(args[2]) !=null) {
                            sender.sendMessage(ChatColor.GRAY+"Attempting to add trial to player "+ChatColor.YELLOW+args[3]+ChatColor.GRAY+"...");
                            Player p = Bukkit.getPlayer(args[3]);
                            if(p !=null) {
                                //Online
                                if(p.hasPlayedBefore()) {
                                    if(!settings.contains("Players."+p.getUniqueId().toString())) {
                                settings.set("Players."+p.getUniqueId().toString()+".TimeExpired", Integer.parseInt(args[2]));
                                settings.set("Players."+p.getUniqueId().toString()+".Type", args[1]);
                                StringBuilder sb = new StringBuilder("");
                                for(int i = 4; i<args.length;i++) {
                                    String s = args[i];
                                    if(!(i == args.length)) {
                                    sb.append(s+" ");
                                        } else {
                                        sb.append(s);
                                        }
                                    }
                                settings.set("Players."+p.getUniqueId().toString()+".Command", sb.toString());
                                settings.set("Players."+p.getUniqueId().toString()+".Time", 0);
                                saveConfig();
                                        sender.sendMessage(ChatColor.GREEN+"- Trial Added -");
                                        } else {
                                        sender.sendMessage(ChatColor.DARK_RED+"ERROR: "+ChatColor.RED+"This player "+ChatColor.YELLOW+p.getName()+ChatColor.RED+" already is in a trial!");
                                        }
                                    } else {
                                    sender.sendMessage(ChatColor.DARK_RED+"ERROR: "+ChatColor.RED+"This player "+ChatColor.YELLOW+p.getName()+ChatColor.RED+" has never actually played before!");
                                    }
                                } else {
                                //Offline
                                OfflinePlayer pl = Bukkit.getOfflinePlayer(args[3]);
                                if(pl.hasPlayedBefore()) {
                                    if(!settings.contains("Players."+pl.getUniqueId().toString())) {
                                settings.set("Players."+pl.getUniqueId().toString()+".TimeExpired", Integer.parseInt(args[2]));
                                settings.set("Players."+pl.getUniqueId().toString()+".Type", TrialType.valueOf(args[1]));
                                StringBuilder sb = new StringBuilder("");
                                for(int i = 4; i<args.length;i++) {
                                    String s = args[i];
                                    if(!(i == args.length)) {
                                        sb.append(s+" ");
                                    } else {
                                        sb.append(s);
                                        }
                                    }
                                settings.set("Players."+pl.getUniqueId().toString()+".Command", sb.toString());
                                settings.set("Players."+p.getUniqueId().toString()+".Time", 0);
                                saveConfig();
                                        sender.sendMessage(ChatColor.GREEN+"- Trial Added -");
                                        } else {
                                        sender.sendMessage(ChatColor.DARK_RED+"ERROR: "+ChatColor.RED+"This player "+ChatColor.YELLOW+pl.getName()+ChatColor.RED+" has never actually played before!");
                                    }
                                    } else {
                                    sender.sendMessage(ChatColor.DARK_RED+"ERROR: "+ChatColor.RED+"This player "+ChatColor.YELLOW+pl.getName()+ChatColor.RED+" has never actually played before!");
                                    }
                                }
                            } else {
                            sender.sendMessage(ChatColor.DARK_RED+"ERROR: "+ChatColor.RED+"That Time value is not an integer! Fix that!");
                            }
                        } else {
                        sender.sendMessage(ChatColor.DARK_RED+"ERROR: "+ChatColor.RED+"That TrialType value is not a TrialType! Fix that!");
                        }
                    }
                }
            }
        }
        return true;
    }
}
