package com.example.memorykun;

import javax.validation.constraints.Max;

import lombok.Data;

@Data

public class Subject {
    private int id;
    private int number;
    
    @Max(20)
    private String name;
    
    
}

/*
・EP図を基に、エンティティクラスを作製。
・@Dataを書く
*/