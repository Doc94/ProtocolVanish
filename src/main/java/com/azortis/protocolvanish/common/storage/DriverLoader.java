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

package com.azortis.protocolvanish.common.storage;

import com.azortis.azortislib.utils.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class DriverLoader {

    public static void loadDriver(Object plugin, File dataFolder, String name, String driverURL){
        File saveLocation = new File(dataFolder, name);
        if(!saveLocation.exists()){
            try{
                URL url = new URL(driverURL);
                try(InputStream is = url.openStream()){
                    FileUtils.copy(is, saveLocation);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        URLClassLoader classLoader = (URLClassLoader) plugin.getClass().getClassLoader();
        try{
            Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURLMethod.setAccessible(true);
            addURLMethod.invoke(classLoader, saveLocation.toURI().toURL());
        }catch (MalformedURLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex){
            ex.printStackTrace();
        }
    }

}
