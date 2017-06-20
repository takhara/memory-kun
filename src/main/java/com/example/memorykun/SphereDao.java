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
    
    public List<Sphere> findByNumber(int number){
        return jdbc.query("SELECT * FROM subject WHERE id=?", new BeanPropertyRowMapper<>(Sphere.class),number);
    }
    
}
