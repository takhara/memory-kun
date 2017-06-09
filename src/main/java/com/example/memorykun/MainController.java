package com.example.memorykun;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller //クラス
public class MainController {

    @GetMapping("/memory-kun")
    public String hello(Model model) {
        // model.addAttribute("path", "javaScript:treeMenu('treeMenu11',
        // 'none')");

        return "memory-kun";

    }

    @GetMapping("/form") // HTML 14 (th:action="@{/form})、メソッド
    public String sample(String name11, String name111,String name1111, Model model) {

        model.addAttribute("name11", name11);
        model.addAttribute("name111", name111);
        model.addAttribute("name1111", name1111);

        return "memory-kun";
    }
    
    
    
    
    @Autowired //springコンテナ
    private JdbcTemplate jdbc;
    @GetMapping("/h2")
    
    public String h2(String name1,Model model){
        model.addAttribute("name1", name1);
        jdbc.update("UPDATE subject set subname=? where subno=1",name1 );;
        return "memory-kun";
    }
    @GetMapping("/h3")
    public String h3(String name11,Model model){
        model.addAttribute("name11",name11);
    jdbc.update("UPDATE subject set sphname=? where supno=1 and subno=(select subno from subject where subname=name1)",name11);
    return "memory-kun";
    }
    //@Value("${app.name}")//注釈
    //private String appName;
    
    
}
