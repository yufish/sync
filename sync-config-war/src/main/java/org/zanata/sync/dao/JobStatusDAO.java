package org.zanata.sync.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zanata.sync.model.JobStatus;
import org.zanata.sync.model.JobStatusList;
import org.zanata.sync.model.JobType;
import org.zanata.sync.model.SyncWorkConfig;


/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@Stateless
public class JobStatusDAO {
    private static final Logger log =
            LoggerFactory.getLogger(JobStatusDAO.class);
    @PersistenceContext
    private EntityManager entityManager;


    public List<JobStatus> getJobStatusList(SyncWorkConfig config, JobType type) {
        /*DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

        Result<Record> statusRecords = dslContext.select()
                .from(JOB_STATUS_TABLE)
                .where(JOB_STATUS_TABLE.WORKID.eq(config.getId())
                        .and(JOB_STATUS_TABLE.JOBTYPE
                                .eq(type.name())))
                .orderBy(JOB_STATUS_TABLE.ID.desc())
                .fetch();

        List<JobStatus> jobStatusList = statusRecords.stream()
                .map(record -> new JobStatus(record.getValue(
                        JOB_STATUS_TABLE.JOBSTATUSTYPE),
                        toDate(record.getValue(JOB_STATUS_TABLE.STARTTIME)),
                        toDate(record.getValue(JOB_STATUS_TABLE.ENDTIME)),
                        toDate(record
                                .getValue(JOB_STATUS_TABLE.NEXTSTARTTIME))))
                .collect(Collectors.toList());
        return new JobStatusList(jobStatusList);*/
        return entityManager.createQuery("from JobStatus status where status.workConfig = :workConfig and status.jobType = :jobType order by endTime desc", JobStatus.class)
                .setParameter("workConfig", config)
                .setParameter("jobType", type)
//                .setMaxResults(1)
                .getResultList();
    }

    private static Date toDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

    @TransactionAttribute
    public void saveJobStatus(SyncWorkConfig config, JobType type, JobStatus jobStatus) {
        entityManager.persist(jobStatus);
        log.info("JobStatus saved." + config.getName() + ":" + type);
    }

    private static Timestamp toTimestamp(Date date) {
        // TODO pahuang for manual fired job, some of the fireTime will be null
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }
}
