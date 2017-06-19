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
        model.addAttribute("subjects", jdbc.queryForList("SELECT * FROM subject WHERE name IS NOT null"));

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

    int count = 1;

    @PostMapping("/update/subject") /* HTML 14 (th:action="@{/form})、メソッド */

    public String updateSubject(UpdateSubjectForm form, RedirectAttributes attr) {

        // どこまでデータが入っているかを確かめる,get(0)は一つのデータを持ってくるという意味、
        int size = 0;
        for (int i = 1; i <= 5; i++) {
            if (jdbc.queryForList("SELECT * FROM subject WHERE number = ?", i).get(0).get("name") == null) {
                size = i - 1;
                break;
            }
        }

        // 2.DBを更新する
        jdbc.update("UPDATE subject set name=? where number=? And name IS NULL", form.getSubjectName(), size + 1);

        // 3.DBから値を読み込む
        List<Map<String, Object>> subjects = jdbc.queryForList("SELECT * FROM subject WHERE name IS NOT null");
        // 取ってくる情報が一つだけなら、
        // Map<String, Object> subject = jdbc.queryForList(SELECT * FROM subject
        // WHERE id =値);

        // 4.値を返してあげる、リストで返している
        attr.addFlashAttribute("subjects", subjects);

        return "redirect:/memory-kun";
    }

    @PostMapping("/to/sphere")
    public String toSphere(int subjectNumber, RedirectAttributes attr) {

        List<Map<String, Object>> spheres = jdbc
                .queryForList("SELECT * FROM sphere WHERE name IS NOT null AND subject_number = ?", subjectNumber);

        attr.addFlashAttribute("currentSubject",
                jdbc.queryForList("SELECT * FROM subject WHERE number = ?", subjectNumber).get(0).get("name"));
        attr.addFlashAttribute("spheres", spheres);
        attr.addFlashAttribute("subjectNumber", subjectNumber);

        return "redirect:/memory-kun";
    }

    @PostMapping("/update/sphere") /* HTML 14 (th:action="@{/form})、メソッド */
    public String updateSphere(String sphereName, RedirectAttributes attr, int subjectNumber) {
        System.out.println(subjectNumber);
        int size = 0;
        for (int i = 1; i <= 6; i++) {
            if (jdbc.queryForList("SELECT * FROM sphere WHERE number=? AND subject_number=?", i, subjectNumber).get(0)
                    .get("name") == null) {
                size = i - 1;
                break;
            }
        }

        jdbc.update("UPDATE sphere set name=? where number=? AND name IS NULL AND subject_number=?", sphereName,
                size + 1, subjectNumber);
        List<Map<String, Object>> spheres = jdbc
                .queryForList("SELECT * FROM sphere WHERE name IS NOT NULL AND subject_number= ?", subjectNumber);

        attr.addFlashAttribute("currentSubject",
                jdbc.queryForList("SELECT * FROM subject WHERE number = ?", subjectNumber).get(0).get("name"));
        attr.addFlashAttribute("subjectNumber", subjectNumber);
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where name IS NOT NULL"));
        attr.addFlashAttribute("spheres", spheres);

        return "redirect:/memory-kun";
    }

    // [SELECT * FROM words WHERE name IS NOT null AND subject_id=? AND
    // sphere_number=?]
    @PostMapping("/to/word")
    public String toWord(int subjectNumber, RedirectAttributes attr, int sphereNumber) {

        List<Map<String, Object>> spheres = jdbc
                .queryForList("SELECT * FROM sphere WHERE name IS NOT NULL AND subject_number=?", subjectNumber);

        List<Map<String, Object>> words = jdbc.queryForList(
                "SELECT * FROM word WHERE name IS NOT NULL AND subject_number=? AND sphere_number=?", subjectNumber,
                sphereNumber);

        attr.addFlashAttribute("currentSubject",
                jdbc.queryForList("SELECT * FROM subject WHERE number = ?", subjectNumber).get(0).get("name"));
        attr.addFlashAttribute("currentSphere", jdbc
                .queryForList("SELECT * FROM sphere WHERE subject_number = ? AND number=?", subjectNumber, sphereNumber)
                .get(0).get("name"));
        attr.addFlashAttribute("spheres", spheres);
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("subjectNumber", subjectNumber);
        attr.addFlashAttribute("sphereNumber", sphereNumber);

        return "redirect:/memory-kun";
    }

    @PostMapping("/update/word")
    public String updateWord(int sphereNumber, RedirectAttributes attr, String wordName, int subjectNumber) {
        try {
            for (int i = 1; i <= 100; i++) {
                jdbc.update("INSERT INTO word VALUES(?,?,?,?,?)", count, wordName, null, subjectNumber, sphereNumber);
                count = +1;
                break;
            }
        } catch (RuntimeException e) {
            System.out.println("同じ単語名が使用されています。");
        }

        List<Map<String, Object>> words = jdbc.queryForList(
                "SELECT * FROM word WHERE subject_number=? AND sphere_number=?", subjectNumber, sphereNumber);

        attr.addFlashAttribute("currentSubject",
                jdbc.queryForList("SELECT * FROM subject WHERE number = ?", subjectNumber).get(0).get("name"));
        attr.addFlashAttribute("currentSphere", jdbc
                .queryForList("SELECT * FROM sphere WHERE subject_number = ? AND number=?", subjectNumber, sphereNumber)
                .get(0).get("name"));
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("subjectNumber", subjectNumber);
        attr.addFlashAttribute("sphereNumber", sphereNumber);
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where name IS NOT NULL"));
        attr.addFlashAttribute("spheres",
                jdbc.queryForList("SELECT * FROM sphere where name IS NOT NULL AND subject_number=?", subjectNumber));
        return "redirect:/memory-kun";
    }

    @PostMapping("/update/mean")
    public String updateMean(String selectWord, RedirectAttributes attr, String mean, int subjectNumber,
            int sphereNumber) {
        jdbc.update("UPDATE word set mean=? where name=? AND subject_number=? AND sphere_number=?", mean, selectWord,
                subjectNumber, sphereNumber);

        List<Map<String, Object>> words = jdbc.queryForList(
                "SELECT * FROM word WHERE subject_number= ? AND  sphere_number=?", subjectNumber, sphereNumber);

        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("currentSubject",
                jdbc.queryForList("SELECT * FROM subject WHERE number = ?", subjectNumber).get(0).get("name"));
        attr.addFlashAttribute("currentSphere", jdbc
                .queryForList("SELECT * FROM sphere WHERE subject_number = ? AND number=?", subjectNumber, sphereNumber)
                .get(0).get("name"));
        attr.addFlashAttribute("currentWord",
                jdbc.queryForList("SELECT * FROM word WHERE name=?", selectWord).get(0).get("name"));
        attr.addFlashAttribute("subjectNumber", subjectNumber);
        attr.addFlashAttribute("sphereNumber", sphereNumber);
        attr.addFlashAttribute("selectWord", selectWord);
        attr.addFlashAttribute("subjects", jdbc.queryForList("SELECT * FROM subject where name IS NOT NULL"));
        attr.addFlashAttribute("spheres",
                jdbc.queryForList("SELECT * FROM sphere where name IS NOT NULL AND subject_number=?", subjectNumber));

        return "redirect:/memory-kun";
    }

    // こここここここここ
    @PostMapping("/to/mean")
    public String toMean(int subjectNumber, RedirectAttributes attr, int sphereNumber, String selectWord) {

        List<Map<String, Object>> spheres = jdbc
                .queryForList("SELECT * FROM sphere WHERE name IS NOT NULL AND subject_number=?", subjectNumber);

        List<Map<String, Object>> words = jdbc.queryForList(
                "SELECT * FROM word WHERE name IS NOT NULL AND subject_number=? AND sphere_number=?", subjectNumber,
                sphereNumber);
        // Map<String, Object> mean = jdbc
        // .queryForList("SELECT * FROM word WHERE name IS NOT NULL AND
        // subject_number=? AND sphere_number=?",
        // subjectNumber,sphereNumber).get(0);

        attr.addFlashAttribute("currentWord",
                jdbc.queryForList("SELECT * FROM word WHERE name=?", selectWord).get(0).get("name"));
        attr.addFlashAttribute("currentSphere", jdbc
                .queryForList("SELECT * FROM sphere WHERE subject_number = ? AND number=?", subjectNumber, sphereNumber)
                .get(0).get("name"));
        attr.addFlashAttribute("currentSubject",
                jdbc.queryForList("SELECT * FROM subject WHERE number = ?", subjectNumber).get(0).get("name"));
        attr.addFlashAttribute("subjectNumber", subjectNumber);
        attr.addFlashAttribute("sphereNumber", sphereNumber);
        attr.addFlashAttribute("selectWord", selectWord);
        attr.addFlashAttribute("spheres", spheres);
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("mean",
                jdbc.queryForList(
                        "SELECT * FROM word WHERE name IS NOT NULL AND subject_number=? AND sphere_number=? AND name=?",
                        subjectNumber, sphereNumber, selectWord).get(0).get("mean"));

        return "redirect:/memory-kun";
    }

    @PostMapping("/display/mean")
    public String displayMean(int subjectNumber, RedirectAttributes attr, int sphereNumber, String selectWord) {

        List<Map<String, Object>> words = jdbc.queryForList(
                "SELECT * FROM word WHERE name IS NOT NULL AND subject_number=? AND sphere_number=?", subjectNumber,
                sphereNumber);
        List<Map<String, Object>> spheres = jdbc
                .queryForList("SELECT * FROM sphere WHERE name IS NOT NULL AND subject_number=?", subjectNumber);

        attr.addFlashAttribute("currentWord",
                jdbc.queryForList("SELECT * FROM word WHERE name=?", selectWord).get(0).get("name"));
        attr.addFlashAttribute("currentSphere", jdbc
                .queryForList("SELECT * FROM sphere WHERE subject_number = ? AND number=?", subjectNumber, sphereNumber)
                .get(0).get("name"));
        attr.addFlashAttribute("currentSubject",
                jdbc.queryForList("SELECT * FROM subject WHERE number = ?", subjectNumber).get(0).get("name"));
        attr.addFlashAttribute("spheres", spheres);
        attr.addFlashAttribute("sphereNumber", sphereNumber);
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("subjectNumber", subjectNumber);
        attr.addFlashAttribute("sphereNumber", sphereNumber);
        attr.addFlashAttribute("selectWord", selectWord);
        attr.addFlashAttribute("mean",
                jdbc.queryForList("SELECT * FROM word WHERE name IS NOT NULL AND subject_number=? AND sphere_number=?",
                        subjectNumber, sphereNumber).get(0).get("mean"));

        return "redirect:/memory-kun";
    }

    @PostMapping("/curent/subject")
    public String currentSubject(int subjectNumber, RedirectAttributes attr) {

        attr.addFlashAttribute("currentSubject",
                jdbc.queryForList("SELECT * FROM subject WHERE number = ?", subjectNumber).get(0).get("name"));
        attr.addFlashAttribute("subjectNumber", subjectNumber);
        return "redirect:/memory-kun";
    }

    @PostMapping("/curent/sphere")
    public String currentSphere(int subjectNumber, int sphereNumber, RedirectAttributes attr) {

        attr.addFlashAttribute("currentSphere", jdbc
                .queryForList("SELECT * FROM sphere WHERE subject_number = ? AND number=?", subjectNumber, sphereNumber)
                .get(0).get("name"));
        attr.addFlashAttribute("subjectNumber", subjectNumber);
        attr.addFlashAttribute("sphereNumber", sphereNumber);
        return "redirect:/memory-kun";
    }

    @PostMapping("/curent/word")
    public String currentWord(int subjectNumber, int sphereNumber, String selectWord, RedirectAttributes attr) {

        attr.addFlashAttribute("currentWord",
                jdbc.queryForList("SELECT * FROM word WHERE name=?", selectWord).get(0).get("name"));
        return "redirect:/memory-kun";
    }

}
