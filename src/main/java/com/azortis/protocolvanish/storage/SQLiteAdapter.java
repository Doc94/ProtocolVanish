/*
 * Hides you completely from players on your servers by using packets!
 *     Copyright (C) 2019  Azortis
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.azortis.protocolvanish.storage;

import com.azortis.protocolvanish.PermissionManager;
import com.azortis.protocolvanish.ProtocolVanish;
import com.azortis.protocolvanish.VanishPlayer;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@SuppressWarnings("all")
public class SQLiteAdapter implements IDatabase{

    private ProtocolVanish plugin;
    private String jdbcurl;

    SQLiteAdapter(ProtocolVanish plugin){
        this.plugin = plugin;
        File dbFile = new File(plugin.getDataFolder(), "storage.db");
        try{
            if(!dbFile.exists()){
                dbFile.createNewFile();
            }
        }catch (IOException ex){
            ex.printStackTrace();
            plugin.getAzortisLib().getLogger().severe("Cannot create database file, shutting down!");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
        this.jdbcurl = "jdbc:sqlite:" + dbFile.getPath();
        createTable();
    }

    @Override
    public VanishPlayer getVanishPlayer(UUID uuid) {
        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT vanished FROM vanishPlayers WHERE uuid=?");
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new VanishPlayer(Bukkit.getPlayer(uuid), resultSet.getBoolean(1));
            }else if(plugin.getPermissionManager().hasPermission(Bukkit.getPlayer(uuid), PermissionManager.Permission.USE)){
                VanishPlayer vanishPlayer = new VanishPlayer(Bukkit.getPlayer(uuid), false);
                Bukkit.getScheduler().runTaskAsynchronously(plugin, ()-> createVanishPlayer(vanishPlayer));
                return vanishPlayer;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public VanishPlayer.PlayerSettings getPlayerSettings(UUID uuid) {
        try(Connection connection = createConnection()){

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveVanishPlayer(VanishPlayer vanishPlayer) {

    }

    @Override
    public void createVanishPlayer(VanishPlayer vanishPlayer) {

    }

    @Override
    public void deleteVanishPlayer(VanishPlayer vanishPlayer) {

    }

    private void createTable(){
        try(Connection connection = createConnection()){
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE vanishPlayers (uuid varchar(36), vanished boolean, playerSettings blob)");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private Connection createConnection(){
        try{
            return DriverManager.getConnection(jdbcurl);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
