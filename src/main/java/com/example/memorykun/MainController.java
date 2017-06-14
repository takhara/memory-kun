package com.example.memorykun;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // クラス
public class MainController {

    @GetMapping("/memory-kun")
    public String hello(Model model) {
        model.addAttribute("subject", jdbc.queryForList("SELECT * FROM subject WHERE id = 1").get(0));

        return "memory-kun";
    }

    @Autowired // DBを使うのに必要
    private JdbcTemplate jdbc;
    Integer subjectNumber;
    Integer sphereNumber;
    @PostMapping("/update/subject") /* HTML 14 (th:action="@{/form})、メソッド */
    public String updateSubject(String subjectName, Integer radioSubject, RedirectAttributes attr) {
        // どこまでデータが入っているかを確かめる,get(0)は一つのデータを持ってくるという意味、
        
        

        int size = 0;
        int count = 0;
        for (int i = 1; i <= 3; i++) {
            if (jdbc.queryForList("SELECT * FROM subject WHERE id = ?", i).get(0).get("name") == null) {
                size = i - 1;
                count+=1;
                break;
            }
        }

        // 2.DBを更新する
        jdbc.update("UPDATE subject set name=? where id=?　And name IS NULL", subjectName, size + 1);

        // 3.DBから値を読み込む
        List<Map<String, Object>> subjects = jdbc.queryForList("SELECT * FROM subject WHERE name IS NOT null");
        // 取ってくる情報が一つだけなら、
        // Map<String, Object> subject = jdbc.queryForList(SELECT * FROM subject
        // WHERE id =値);

        // 4.値を返してあげる、リストで返している
        attr.addFlashAttribute("subjects", subjects);

        // Integer.parseInt(radioGroup);
        return "redirect:/memory-kun";
    }

    @PostMapping("/update/sphere") /* HTML 14 (th:action="@{/form})、メソッド */
    public String updateSphere(Integer radioSubject, String sphereName, RedirectAttributes attr) {
        System.out.println(radioSubject);

        int size=0;
        for (int i = 1; i <= 6; i++) {
            if (jdbc.queryForList("SELECT * FROM sphere WHERE number=? AND subject_number=?", i, radioSubject).get(0)
                    .get("name") == null) {
                size = i - 1;
                break;
            }
        }
        jdbc.update("UPDATE sphere set name=? where number=? AND name IS NULL AND subject_number=?", sphereName, size + 1, radioSubject);

        List<Map<String, Object>> spheres = jdbc
                .queryForList("SELECT * FROM sphere WHERE name IS NOT NULL AND subject_number= ?",radioSubject);

        attr.addFlashAttribute("radioSubject", radioSubject);
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where name IS NOT NULL"));
        attr.addFlashAttribute("spheres", spheres);

        subjectNumber = radioSubject;
        return "redirect:/memory-kun";

    }

    @PostMapping("/update/word")
    public String updateWord(Integer radioSphere, RedirectAttributes attr, String wordName){
        int size=0;
        for (int i = 1; i <= 30; i++) {
            if (jdbc.queryForList("SELECT * FROM word WHERE number=? AND subject_number=? AND sphere_number=?", i,subjectNumber,radioSphere).get(0)
                    .get("name") == null) {
                size = i - 1;
                break;
            }
        }
        jdbc.update("UPDATE word set name=? where number=? AND name IS NULL AND subject_number=? AND  sphere_number=?", wordName, size + 1, subjectNumber,radioSphere);

        List<Map<String, Object>> words = jdbc
                .queryForList("SELECT * FROM word WHERE name IS NOT NULL AND subject_number= ? AND  sphere_number=?",subjectNumber, radioSphere);
        sphereNumber=radioSphere;
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("radioSphere", radioSphere);
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where name IS NOT NULL"));
        attr.addFlashAttribute("spheres", jdbc.queryForList("SELECT * FROM sphere where number=? AND name IS NOT NULL AND subject_number=?",radioSphere,subjectNumber));



        return "redirect:/memory-kun";
    }
    @PostMapping("/update/mean")
    public String updateMean(Integer radioWord, RedirectAttributes attr, String wordName, String mean){ 
        jdbc.update("UPDATE word set mean=? where name=? AND subject_number=? AND sphere_number=?",mean,wordName,subjectNumber,sphereNumber);

        List<Map<String, Object>> words = jdbc
                .queryForList("SELECT * FROM word WHERE name IS NOT NULL AND subject_number= ? AND  sphere_number=?",subjectNumber, radioWord);
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where id=?",subjectNumber));
        attr.addFlashAttribute("spheres", jdbc.queryForList("SELECT * FROM sphere where number=? AND name IS NOT NULL AND subject_number=?",sphereNumber,subjectNumber));
        return "redirect:/memory-kun";
    }
    
    // @Value("${app.name}")//注釈
    // private String appName;
}
