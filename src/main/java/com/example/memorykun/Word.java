package com.example.memorykun;

import lombok.Data;

@Data
public class Word {

    private int id;
    private String name;
    private String mean;
    private int subject_number;
    private int sphere_number;
    private boolean checked;

}
