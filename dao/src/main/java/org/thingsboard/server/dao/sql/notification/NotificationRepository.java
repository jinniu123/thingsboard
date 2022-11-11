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
package org.thingsboard.server.dao.sql.notification;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thingsboard.server.common.data.notification.NotificationStatus;
import org.thingsboard.server.dao.model.sql.NotificationEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    Page<NotificationEntity> findByRecipientIdAndStatusNot(UUID recipientId, NotificationStatus status, Pageable pageable);

    Page<NotificationEntity> findByRecipientId(UUID recipientId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationEntity n SET n.status = :status " +
            "WHERE n.id = :id AND n.recipientId = :recipientId AND n.status <> :status")
    int updateStatusByIdAndRecipientId(@Param("id") UUID id,
                                       @Param("recipientId") UUID recipientId,
                                       @Param("status") NotificationStatus status);

    int countByRecipientIdAndStatusNot(UUID recipientId, NotificationStatus status);

    Page<NotificationEntity> findByRequestId(UUID requestId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationEntity n " +
            "SET n.info = :info, n.reason = :reason " +
            "WHERE n.requestId = :requestId")
    int updateReasonAndInfoByRequestId(@Param("requestId") UUID requestId,
                                       @Param("reason") String reason,
                                       @Param("info") JsonNode info);

    int countByRequestId(UUID requestId);

    int countByRequestIdAndStatus(UUID requestId, NotificationStatus status);

    @Query("SELECT u.email, n.status FROM NotificationEntity n INNER JOIN UserEntity u ON n.recipientId = u.id " +
            "WHERE n.requestId = :requestId")
    List<Object[]> getStatusesByRecipientForRequestId(@Param("requestId") UUID requestId);

}
