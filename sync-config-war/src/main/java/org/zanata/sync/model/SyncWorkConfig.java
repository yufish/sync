package org.zanata.sync.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zanata.sync.common.model.SyncOption;
import org.zanata.sync.util.AutoCloseableDependentProvider;
import org.zanata.sync.util.CronType;
import org.zanata.sync.util.JSONObjectMapper;
import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.zanata.sync.util.AutoCloseableDependentProvider.*;

/**
 * @author Alex Eng <a href="aeng@redhat.com">aeng@redhat.com</a>
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Sync_Work_Config_table")
@Access(AccessType.FIELD)
public class SyncWorkConfig {
    private static final Logger log =
            LoggerFactory.getLogger(SyncWorkConfig.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private CronType syncToZanataCron;
    private CronType syncToRepoCron;

    @Enumerated(EnumType.STRING)
    private SyncOption syncToZanataOption;

    private String srcRepoPluginName;

    private String encryptionKey;

    private boolean syncToServerEnabled = true;

    private boolean syncToRepoEnabled = true;

    private String zanataUsername;
    private String zanataSecret;
    private String zanataServerUrl;

    private String srcRepoUrl;
    private String srcRepoUsername;
    private String srcRepoSecret;
    private String srcRepoBranch;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @OneToMany(mappedBy = "workConfig")
    private List<JobStatus> jobStatusHistory = Collections.emptyList();


    // TODO may not need the id parameter
    public SyncWorkConfig(Long id, String name, String description,
            CronType syncToZanataCron, CronType syncToRepoCron,
            SyncOption syncToZanataOption,
            String srcRepoPluginName,
            String encryptionKey,
            boolean syncToServerEnabled, boolean syncToRepoEnabled,
            String zanataUsername,
            String zanataSecret, String zanataServerUrl, String srcRepoUrl,
            String srcRepoUsername, String srcRepoSecret, String srcRepoBranch) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.syncToZanataOption = syncToZanataOption;
        this.srcRepoPluginName = srcRepoPluginName;
        this.encryptionKey = encryptionKey;
        this.syncToServerEnabled = syncToServerEnabled;
        this.syncToRepoEnabled = syncToRepoEnabled;
        this.syncToZanataCron = syncToZanataCron;
        this.syncToRepoCron = syncToRepoCron;
        this.zanataUsername = zanataUsername;
        this.zanataSecret = zanataSecret;
        this.zanataServerUrl = zanataServerUrl;
        this.srcRepoUrl = srcRepoUrl;
        this.srcRepoUsername = srcRepoUsername;
        this.srcRepoSecret = srcRepoSecret;
        this.srcRepoBranch = srcRepoBranch;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("zanataUsername", zanataUsername)
                .add("srcRepoPluginName", srcRepoPluginName)
                .toString();
    }
}
