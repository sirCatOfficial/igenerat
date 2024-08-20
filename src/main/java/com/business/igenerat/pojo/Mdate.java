package com.business.igenerat.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Mdate {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    public Date getDate() {
        return date;
    }
}
