/*
 * Copyright 2016, Red Hat, Inc. and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.zanata.sync.events;

import java.util.Date;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;

/**
 * @author Alex Eng <a href="aeng@redhat.com">aeng@redhat.com</a>
 */
@AllArgsConstructor
public class JobProgressEvent {
    private String firingId;
    private Long configId;
    private Date nextFireTime;

    public static JobProgressEvent running(String fireInstanceId, Long configId) {
        return running(fireInstanceId, configId, null);
    }

    public static JobProgressEvent running(String fireInstanceId, Long configId, Date nextFireTime) {
        return new JobProgressEvent(fireInstanceId, configId, nextFireTime);
    }

    public String getFiringId() {
        return firingId;
    }

    public Long getConfigId() {
        return configId;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firingId", firingId)
                .add("configId", configId)
                .add("nextFireTime", nextFireTime)
                .toString();
    }
}
