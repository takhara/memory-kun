package com.example.memorykun;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDao {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Map<String,Object>> findAll(){
        return jdbc.queryForList("SELECT * FROM subject");
    }

    public List<Map<String,Object>> findById(int id){
        return jdbc.queryForList("SELECT * FROM subject WHERE id=?",id);
    }
}
//SQLの変更はDAOのみ
//各テーブルに対して発行されるSQLすべて確認できる
