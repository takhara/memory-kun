package com.example.memorykun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class CountSphereDao {
    @Autowired
    private JdbcTemplate jdbc;
    
    public int countSphereId(){
        return jdbc.queryForObject("SELECT MAX(id) FROM countSphereId", Integer.class);
    }
    public void insertSphereId(int count){
        jdbc.update("INSERT INTO countSphereId VALUES(?)", count);
   }

}
