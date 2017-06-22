package com.example.memorykun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDao {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Subject> findAll(){
        return jdbc.query("SELECT * FROM subject WHERE name IS NOT NULL", new BeanPropertyRowMapper<>(Subject.class));
    }

    public Subject findSubjectByNumber(Integer subjectNumber){
        return jdbc.queryForObject("SELECT * FROM subject WHERE number=?",
                new BeanPropertyRowMapper<>(Subject.class), subjectNumber);
    }

    public void UpdateSubject(String subjectName, int size){
         jdbc.update("UPDATE subject set name=? where number=? And name IS NULL", subjectName,size);
    }
    
}


//SQLの変更はDAOのみ
//各テーブルに対して発行されるSQLすべて確認できる