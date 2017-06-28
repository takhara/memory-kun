package com.example.memorykun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class CountSubjectDao {
    @Autowired
    private JdbcTemplate jdbc;
    
    public int countSubjectId(){
        return jdbc.queryForObject("SELECT MAX(id) FROM countSubjectId", Integer.class);
    }
    
    public void insertSubjectId(int count){
         jdbc.update("INSERT INTO countSubjectId VALUES(?)", count);
    }
    

}
