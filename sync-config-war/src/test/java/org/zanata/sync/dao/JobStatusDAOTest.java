package org.zanata.sync.dao;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.zanata.sync.EntityManagerRule;
import org.zanata.sync.common.model.SyncOption;
import org.zanata.sync.model.JobStatus;
import org.zanata.sync.model.JobStatusType;
import org.zanata.sync.model.JobType;
import org.zanata.sync.model.SyncWorkConfig;

public class JobStatusDAOTest {
    @Rule
    public EntityManagerRule entityManagerRule = new EntityManagerRule();

    private JobStatusDAO dao;

    @Before
    public void setUp() {
        dao = new JobStatusDAO(entityManagerRule.getEm());
    }

    @Test
    public void canSaveNewStatusAndUpdate() {
        SyncWorkConfig syncWorkConfig =
                new SyncWorkConfig(null, "name", null, null, null,
                        SyncOption.SOURCE, "git", null, true, true,
                        "username",
                        "apiKey", "http://localhost:8080/zanata",
                        "https://github.com/zanata/zanata-server.git",
                        null, null, null);
        entityManagerRule.getEm().persist(syncWorkConfig);
        JobStatus jobStatus =
                new JobStatus("id", syncWorkConfig, JobType.REPO_SYNC,
                        JobStatusType.RUNNING, null, null, null);
        dao.saveJobStatus(jobStatus);

        JobStatus status = entityManagerRule.getEm().find(JobStatus.class, "id");
        assertThat(status.getStatus()).isEqualTo(JobStatusType.RUNNING);

        dao.updateJobStatus("id", new Date(), null, JobStatusType.COMPLETED);
        status = entityManagerRule.getEm().find(JobStatus.class, "id");
        assertThat(status.getStatus()).isEqualTo(JobStatusType.COMPLETED);
    }
}
