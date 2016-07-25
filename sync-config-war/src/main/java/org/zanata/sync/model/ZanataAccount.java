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
package org.zanata.sync.model;

import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.zanata.sync.dto.ZanataUserAccount;


/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@Entity
@Access(AccessType.FIELD)
@NamedQueries(
        {
                @NamedQuery(
                        name = ZanataAccount.FIND_BY_USERNAME_SERVER_QUERY,
                        query = "from ZanataAccount where username = :username and server = :server"
                ),
                @NamedQuery(
                        name = ZanataAccount.FIND_BY_LOCAL_USERNAME_QUERY,
                        query = "from ZanataAccount where localUsername = :username"
                )
        }
)
@Table(uniqueConstraints = {
//        @UniqueConstraint(columnNames = "localUsername"),
        @UniqueConstraint(columnNames = { "server", "username" })
})
public class ZanataAccount {
    public static final String FIND_BY_USERNAME_SERVER_QUERY =
            "findByUsernameAndServer";
    public static final String FIND_BY_LOCAL_USERNAME_QUERY =
            "FindByLocalUsername";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String localUsername;
    private String server;
    private String username;
    private String secret;

    @OneToMany(mappedBy = "zanataAccount")
    private Set<RepoAccount> repoAccounts;

    @OneToMany(mappedBy = "zanataAccount")
    private Set<SyncWorkConfig> workConfigs;

    public ZanataAccount() {
    }

    public ZanataAccount(String localUsername) {
        this.localUsername = localUsername;
    }

    public ZanataAccount(String zanataUsername, String zanataServer, String zanataSecret) {
        this.username = zanataUsername;
        this.server = zanataServer;
        this.secret = zanataSecret;
    }

    public String getServer() {
        return server;
    }

    public String getUsername() {
        return username;
    }

    public String getSecret() {
        return secret;
    }

    public Set<RepoAccount> getRepoAccounts() {
        return repoAccounts;
    }

    public Set<SyncWorkConfig> getWorkConfigs() {
        return workConfigs;
    }

    public String getLocalUsername() {
        return localUsername;
    }

    public Long getId() {
        return id;
    }

    public void updateZanataAccount(ZanataUserAccount zanataUserAccount) {
        this.username = zanataUserAccount.getUsername();
        this.server = zanataUserAccount.getZanataServer();
        this.secret = zanataUserAccount.getApiKey();
    }
}
