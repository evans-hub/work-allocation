package com.example.workallocation.Entity;

public class Uploads {
    private String taskid,file;

    public Uploads(String taskid, String file) {
        this.taskid = taskid;
        this.file = file;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
