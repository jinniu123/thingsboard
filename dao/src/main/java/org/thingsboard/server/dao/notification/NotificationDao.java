/**
 * Copyright © 2016-2022 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.dao.notification;

import org.thingsboard.server.common.data.id.NotificationId;
import org.thingsboard.server.common.data.id.NotificationRequestId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.UserId;
import org.thingsboard.server.common.data.notification.Notification;
import org.thingsboard.server.common.data.notification.NotificationInfo;
import org.thingsboard.server.common.data.notification.NotificationStatus;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.dao.Dao;

public interface NotificationDao extends Dao<Notification> {

    PageData<Notification> findUnreadByUserIdAndPageLink(TenantId tenantId, UserId userId, PageLink pageLink);

    PageData<Notification> findByUserIdAndPageLink(TenantId tenantId, UserId userId, PageLink pageLink);

    boolean updateStatusByIdAndUserId(TenantId tenantId, UserId userId, NotificationId notificationId, NotificationStatus status);

    int countUnreadByUserId(TenantId tenantId, UserId userId);

    PageData<Notification> findByRequestId(TenantId tenantId, NotificationRequestId notificationRequestId, PageLink pageLink);

    int updateByRequestId(TenantId tenantId, NotificationRequestId notificationRequestId, String notificationReason, NotificationInfo notificationInfo);

}
