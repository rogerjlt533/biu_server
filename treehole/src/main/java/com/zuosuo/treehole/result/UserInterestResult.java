package com.zuosuo.treehole.result;

public class UserInterestResult {
    private long id = 0;
    private int checked = 0;
    private String name = "";

    public UserInterestResult(long id, String name, int checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
