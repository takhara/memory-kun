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
        return jdbc.queryForObject("SELECT * FROM subject WHERE id=?",
                new BeanPropertyRowMapper<>(Subject.class), subjectNumber);
    }

    public void updateSubject(String subjectName, int size){
         jdbc.update("UPDATE subject set name=? where id=? And name IS NULL", subjectName,size);
    }
    public void deleteSubject(int subjectNumber){
        jdbc.update("UPDATE subject set name=NULL where id=?", subjectNumber);
    }
    

    public void insertSubject(int id,String subjectName){
         jdbc.update("INSERT INTO subject VALUES(?,?)", id,subjectName);
    }
}


//SQLの変更はDAOのみ
//各テーブルに対して発行されるSQLすべて確認できる