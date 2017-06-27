package com.example.memorykun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class CountIdDao {
    @Autowired
    private JdbcTemplate jdbc;
    
    public int countId(){
        return jdbc.queryForObject("SELECT MAX(id) FROM countId", Integer.class);
    }
    

}
