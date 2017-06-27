package com.example.memorykun;

import lombok.Data;

@Data
public class UpdateWordForm {

    private Integer subjectNumber;
    private Integer sphereNumber;
    private String wordName;
    private boolean checked;
    private int count;
}
