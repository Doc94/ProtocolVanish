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

package com.azortis.protocolvanish.common.settings;

public class PermissionSettings {

    private boolean useSeePermission;
    private boolean useLayeredPermissions;
    private int maxLevel;

    public boolean getUseSeePermission() {
        return useSeePermission;
    }

    public void setEnableSeePermission(boolean useSeePermission) {
        this.useSeePermission = useSeePermission;
    }

    public boolean getUseLayeredPermissions() {
        return useLayeredPermissions;
    }

    public void setUseLayeredPermissions(boolean useLayeredPermissions) {
        this.useLayeredPermissions = useLayeredPermissions;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}
