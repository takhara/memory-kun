package com.example.memorykun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WordDao {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    public List<Word> findWord(int subjectNumber, int sphereNumber){
        return jdbc.query("SELECT * FROM word WHERE subject_number=? AND sphere_number=? Order by id ASC", new BeanPropertyRowMapper<>(Word.class),subjectNumber,sphereNumber);
    }
    public Word findCurrentWord(int wordNumber){
        return jdbc.queryForObject("SELECT * FROM word WHERE id=?", new BeanPropertyRowMapper<>(Word.class),wordNumber);
    }
    
    public int countWordsBySubjectNumber(int subjectNumber){
        return jdbc.queryForObject("SELECT count(id) FROM word where subject_number=?", Integer.class, subjectNumber);
    }
    
    public int countWordsBySubjectNumberChecked(int subjectNumber, boolean checked){
        return jdbc.queryForObject("SELECT count(id) FROM word where subject_number=? AND checked = ?", Integer.class, subjectNumber, checked);
    }
    

    public int countWordsBySubjectNumberAndSphereNumber(int subjectNumber ,int sphereNumber){
        return jdbc.queryForObject("SELECT count(id) FROM word where subject_number=? AND sphere_number=?", Integer.class, subjectNumber, sphereNumber);
    }
    
    public int countWordsByAndSubjectNumberSphereNumberChecked(int subjectNumber, int sphereNumber, boolean checked){
        return jdbc.queryForObject("SELECT count(checked) FROM word where subject_number=? AND sphere_number=? AND checked = ?", Integer.class, subjectNumber, sphereNumber,checked);
    }
    
    public void insertWord(String name, int count, int subjectNumber, int sphereNumber){
        jdbc.update("INSERT INTO word VALUES(?,?,?,?,?,?)", count, name, null,
                subjectNumber, sphereNumber, false);
   }

    public void updateMean(String mean, int wordNumber){
         jdbc.update("UPDATE word set mean=? where id=?",mean, wordNumber);
    }
    
    public List<Word> findMean(int wordNumber){
        return jdbc.query("SELECT * FROM word WHERE id=?", new BeanPropertyRowMapper<>(Word.class), wordNumber);
    }
    
}
