package com.example.memorykun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SphereDao {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Sphere> findSphereBySubjectNumber(Integer subjectNumber){
        return jdbc.query("SELECT * FROM sphere WHERE subject_number=? AND name IS NOT NULL", new BeanPropertyRowMapper<>(Sphere.class),subjectNumber);
    }
    public Sphere findCurrentSphereBySubjectNumberAndSphereNumber(Integer subjectNumber, Integer sphereNumber){
        return jdbc.queryForObject("SELECT * FROM sphere WHERE subject_number = ? AND number=?", new BeanPropertyRowMapper<>(Sphere.class),subjectNumber,sphereNumber);
    } 

    public void UpdateSphere(String sphereName, Integer sphereNumber,Integer subjectNumber ){
         jdbc.update("UPDATE sphere set name=? where number=? AND name IS NULL AND subject_number=?", sphereName,sphereNumber,subjectNumber);
    }
    


    
    
}
