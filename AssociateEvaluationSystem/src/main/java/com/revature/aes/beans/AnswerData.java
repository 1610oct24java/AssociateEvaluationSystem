package com.revature.aes.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nick on 1/19/2017.
 */
public class AnswerData implements Serializable {

    private Assessment assessment;

    private List<SnippetUpload> snippetUploads;

    public AnswerData() {
    }

    public AnswerData(Assessment assessment, List<SnippetUpload> snippetUploads) {
        this.assessment = assessment;
        this.snippetUploads = snippetUploads;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public List<SnippetUpload> getSnippetUploads() {
        return snippetUploads;
    }

    public void setSnippetUploads(List<SnippetUpload> snippetUploads) {
        this.snippetUploads = snippetUploads;
    }

    @Override
    public String toString() {
        return "AnswerData{" +
                "assessment=" + assessment +
                ", snippetUploads=" + snippetUploads +
                '}';
    }
}
