/**
 * ownCloud Android client application
 *
 * @author David González Verdugo
 * Copyright (C) 2019 ownCloud GmbH.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.owncloud.android.data.capabilities.datasources.implementation

import com.owncloud.android.data.capabilities.datasources.RemoteCapabilitiesDataSource
import com.owncloud.android.domain.capabilities.model.OCCapability
import com.owncloud.android.lib.common.OwnCloudClient
import com.owncloud.android.lib.common.operations.awaitToRemoteOperationResult
import com.owncloud.android.lib.resources.status.GetRemoteCapabilitiesOperation
import com.owncloud.android.lib.resources.status.RemoteCapabilityMapper

class OCRemoteCapabilitiesDataSource(
    private val client: OwnCloudClient,
    private val remoteCapabilityMapper: RemoteCapabilityMapper
) : RemoteCapabilitiesDataSource {

    override suspend fun getCapabilities(
        accountName: String,
        getRemoteCapabilitiesOperation: GetRemoteCapabilitiesOperation
    ): OCCapability {
        awaitToRemoteOperationResult {
            getRemoteCapabilitiesOperation.execute(client)
        }.let { remoteCapability ->
            return remoteCapabilityMapper.toModel(remoteCapability)!!.also { it.accountName = accountName }
        }
    }
}