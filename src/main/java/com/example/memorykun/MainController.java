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

    @GetMapping("/form") /* HTML 14 (th:action="@{/form})、メソッド */
    public String input(String subjectName, String sphereName, String word, String mean, Model model) {
        // どこまでデータが入っているかを確かめる,get(0)は一つのデータを持ってくるという意味、
        int size = 0;
        for (int i = 1; i <= 3; i++) {
            if (jdbc.queryForList("SELECT * FROM subject WHERE id = ?", i).get(0).get("name") == null) {
                size = i - 1;
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
        model.addAttribute("subjects", subjects);

        size = 0;
        int number = 1;
        for (int i = 1; i <= 6; i++) {
            if (jdbc.queryForList("SELECT * FROM sphere WHERE id=? AND subject_number=?", i, number).get(0)
                    .get("name") == null) {
                size = i - 1;
                break;
            }
        }
        jdbc.update("UPDATE sphere set name=? where id=? AND name IS NULL", sphereName, size + 1);

        List<Map<String, Object>> spheres = jdbc.queryForList("SELECT * FROM sphere WHERE name IS NOT NULL AND subject_number= ?",number);

        model.addAttribute("spheres", spheres);

        return "memory-kun";
    }

    // @Value("${app.name}")//注釈
    // private String appName;
}
