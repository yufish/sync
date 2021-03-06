/*
 * Copyright 2015, Red Hat, Inc. and individual contributors
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
package org.zanata.sync.jobs.plugin.git.service;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zanata.sync.common.model.SyncJobDetail;
import org.zanata.sync.jobs.common.exception.RepoSyncException;
import com.google.common.base.Strings;

public interface RepoSyncService {
    Logger log =
            LoggerFactory.getLogger(RepoSyncService.class);

    void cloneRepo(SyncJobDetail jobDetail, Path path) throws RepoSyncException;

    void syncTranslationToRepo(SyncJobDetail jobDetail, Path path)
            throws RepoSyncException;

    default String commitAuthorName() {
        return "Zanata Sync";
    }

    default String commitAuthorEmail() {
        return "zanata-devel@redhat.com";
    }

    default String commitAuthor() {
        return String
                .format("%s <%s>", commitAuthorName(), commitAuthorEmail());
    }

    default String commitMessage(String zanataUsername) {
        return String
                .format("Zanata Sync job triggered by %s", zanataUsername);
    }

    default String getBranchOrDefault(String branch) {
        if (Strings.isNullOrEmpty(branch)) {
            log.debug("will use master as default branch");
            return "master";
        } else {
            return branch;
        }
    }

    String supportedRepoType();

}
