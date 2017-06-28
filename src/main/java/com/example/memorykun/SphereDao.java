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
        return jdbc.queryForObject("SELECT * FROM sphere WHERE subject_number = ? AND id=?", new BeanPropertyRowMapper<>(Sphere.class),subjectNumber,sphereNumber);
    } 

    public void updateSphere(String sphereName, Integer sphereNumber,Integer subjectNumber ){
         jdbc.update("UPDATE sphere set name=? where id=? AND name IS NULL AND subject_number=?", sphereName,sphereNumber,subjectNumber);
    }
    
    public void deleteSphereBySubjectNumber(Integer subjectNumber){
        jdbc.update("UPDATE sphere set name=NULL where subject_number=? ", subjectNumber);
   }
    
    public void deleteSphereBySubjectNumberAndSphereNumber(Integer subjectNumber,Integer sphereNumber ){
        jdbc.update("UPDATE sphere set name=NULL where subject_number=? AND id=?", subjectNumber, sphereNumber);
   }


    public void insertSphere(int id, String sphereName,int subjectNumber){
        jdbc.update("INSERT INTO sphere VALUES(?,?,?)", id,sphereName,subjectNumber);
    }
}
