package com.example.memorykun;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDao {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Subject> findAll(){
        return jdbc.query("SELECT * FROM subject", new BeanPropertyRowMapper<>(Subject.class));
    }

    //public List<Subject> findById(int id){
      //  return jdbc.query("SELECT * FROM subject WHERE id=?",id, new BeanPropertyRowMapper<>(Subject.class));
    }

//SQLの変更はDAOのみ
//各テーブルに対して発行されるSQLすべて確認できる