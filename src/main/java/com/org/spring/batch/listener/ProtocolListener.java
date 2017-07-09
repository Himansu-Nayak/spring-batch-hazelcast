package com.org.spring.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;

@Slf4j
public class ProtocolListener implements JobExecutionListener {

    @Value("${watched.path.drop}")
    private String dropPath;

    @Value("${watched.path.inprogress}")
    private String inprogress;

    @Value("${watched.path.completed}")
    private String completed;

    public void afterJob(JobExecution jobExecution) {
        StringBuilder protocol = new StringBuilder();
        protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
        protocol.append("Protocol for " + jobExecution.getJobInstance().getJobName() + " \n");
        protocol.append("  Started     : " + jobExecution.getStartTime() + "\n");
        protocol.append("  Finished    : " + jobExecution.getEndTime() + "\n");
        protocol.append("  Exit-Code   : " + jobExecution.getExitStatus().getExitCode() + "\n");
        protocol.append("  Exit-Descr. : " + jobExecution.getExitStatus().getExitDescription() + "\n");
        protocol.append("  Status      : " + jobExecution.getStatus() + "\n");
        protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

        protocol.append("Job-Parameter: \n");
        String filename = "";
        JobParameters jp = jobExecution.getJobParameters();
        for (Iterator<Entry<String, JobParameter>> iter = jp.getParameters().entrySet().iterator(); iter.hasNext(); ) {
            Entry<String, JobParameter> entry = iter.next();
            protocol.append("  " + entry.getKey() + "=" + entry.getValue() + "\n");
            if (entry.getKey().equals("filename")) {
                filename = entry.getValue().toString();
            }
        }
        File file = new File(inprogress + "/" + filename);
        file.renameTo(new File(completed + "/" + filename));

        protocol.append("Filename found, moved file to completed: " + completed + "\n");

        protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
            protocol.append("Step " + stepExecution.getStepName() + " \n");
            protocol.append("WriteCount: " + stepExecution.getWriteCount() + "\n");
            protocol.append("Commits: " + stepExecution.getCommitCount() + "\n");
            protocol.append("SkipCount: " + stepExecution.getSkipCount() + "\n");
            protocol.append("Rollbacks: " + stepExecution.getRollbackCount() + "\n");
            protocol.append("Filter: " + stepExecution.getFilterCount() + "\n");
            protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
        }
        log.info(protocol.toString());
    }

    public void beforeJob(JobExecution jobExecution) {
        StringBuilder protocol = new StringBuilder();
        String filename = "";
        JobParameters jp = jobExecution.getJobParameters();
        for (Iterator<Entry<String, JobParameter>> iter = jp.getParameters().entrySet().iterator(); iter.hasNext(); ) {
            Entry<String, JobParameter> entry = iter.next();
            protocol.append("  " + entry.getKey() + "=" + entry.getValue() + "\n");
            if (entry.getKey().equals("filename")) {
                filename = entry.getValue().toString();
            }
        }
        File file = new File(dropPath + "/" + filename);
        file.renameTo(new File(inprogress + "/" + filename));

        protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
        protocol.append("\nBefore Job Starts: \n");
        protocol.append("Moving file " + filename + "  to inprogress: \n" + inprogress + "\n");
        protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
        log.info(protocol.toString());
    }

}