package com.example.memorykun;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // クラス
public class MainController {

    @GetMapping("/memory-kun")
    public String hello(Model model) {
        // model.addAttribute("subject", jdbc.queryForList("SELECT * FROM
        // subject WHERE id = 1").get(0));

        return "memory-kun";
    }

    // @Autowired // DBを使うのに必要
    /*
     * private SubjectDao subjectDao;
     * 
     * @GetMapping("/test") public String test() {
     * System.out.println(subjectDao.findById(1));
     * 
     * return ""; }
     */

    @Autowired
    private JdbcTemplate jdbc;

    Integer subjectNumber;
    Integer sphereNumber;
    int count = 1;

    @PostMapping("/update/subject") /* HTML 14 (th:action="@{/form})、メソッド */
    public String updateSubject(String subjectName, Integer radioSubject, RedirectAttributes attr) {
        // どこまでデータが入っているかを確かめる,get(0)は一つのデータを持ってくるという意味、
        int size = 0;
        for (int i = 1; i <= 3; i++) {
            if (jdbc.queryForList("SELECT * FROM subject WHERE id = ?", i).get(0).get("name") == null) {
                size = i - 1;
                break;
            }
        }

        // 2.DBを更新する
        jdbc.update("UPDATE subject set name=? where id=? And name IS NULL", subjectName, size + 1);

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
        int size = 0;
        for (int i = 1; i <= 6; i++) {
            if (jdbc.queryForList("SELECT * FROM sphere WHERE number=? AND subject_number=?", i, radioSubject).get(0)
                    .get("name") == null) {
                size = i - 1;
                break;
            }
        }
        jdbc.update("UPDATE sphere set name=? where number=? AND name IS NULL AND subject_number=?", sphereName,
                size + 1, radioSubject);

        List<Map<String, Object>> spheres = jdbc
                .queryForList("SELECT * FROM sphere WHERE name IS NOT NULL AND subject_number= ?", radioSubject);

        attr.addFlashAttribute("radioSubject", radioSubject);
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where name IS NOT NULL"));
        attr.addFlashAttribute("spheres", spheres);

        subjectNumber = radioSubject;
        return "redirect:/memory-kun";
    }

    @PostMapping("/update/word")
    public String updateWord(Integer radioSphere, RedirectAttributes attr, String wordName, Integer radioSubject) {
            try{
            for (int i = 1; i <= 100; i++) {
                jdbc.update("INSERT INTO word VALUES(?,?,?,?,?)", count, wordName, null, radioSubject, radioSphere);
                count = +1;
                break;
            }
            }catch(RuntimeException e){
                System.out.println("同じ単語名が使用されています。");
            }
        List<Map<String, Object>> words = jdbc.queryForList(
                "SELECT * FROM word WHERE name IS NOT NULL AND subject_number= ? AND  sphere_number=?", radioSubject,
                radioSphere);
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("radioSubject", radioSubject);
        attr.addFlashAttribute("radioSphere", radioSphere);
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where name IS NOT NULL"));
        attr.addFlashAttribute("spheres",
                jdbc.queryForList("SELECT * FROM sphere where number=? AND name IS NOT NULL AND subject_number=?",
                        radioSphere, subjectNumber));
        sphereNumber = radioSphere;
        return "redirect:/memory-kun";
    }

    @PostMapping("/update/mean")
    public String updateMean(String radioWord, RedirectAttributes attr, String wordName, String mean,
            Integer radioSphere, Integer radioSubject) {
        jdbc.update("UPDATE word set mean=? where name=? AND subject_number=? AND sphere_number=?", mean, radioWord,
                radioSubject, radioSphere);
        

        List<Map<String, Object>> words = jdbc.queryForList(
                "SELECT * FROM word WHERE name IS NOT NULL AND subject_number= ? AND  sphere_number=? AND name=?",
                radioSubject, radioSphere, radioWord);
       
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where name IS NOT NULL"));
        attr.addFlashAttribute("spheres",
                jdbc.queryForList("SELECT * FROM sphere where number=? AND name IS NOT NULL AND subject_number=?",
                        radioSphere, radioSubject));
        attr.addFlashAttribute("radioSubject", radioSubject);
        attr.addFlashAttribute("radioSphere", radioSphere);
        attr.addFlashAttribute("words", jdbc.queryForList("SELECT name FROM word WHERE subject_number=? AND sphere_number=?",
                 radioSubject, radioSphere));
        attr.addFlashAttribute("mean",
                jdbc.queryForList("SELECT mean FROM word WHERE name=? AND subject_number=? AND sphere_number=?",
                        wordName, radioSubject, radioSphere));
        return "redirect:/memory-kun";
    }

    // @Value("${app.name}")//注釈
    // private String appName;
}
