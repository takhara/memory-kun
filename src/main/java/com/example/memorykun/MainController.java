package com.example.memorykun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // クラス
public class MainController {
    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/memory-kun")
    public String hello(Model model) {
        model.addAttribute("subjects", jdbc.queryForList("SELECT * FROM subject WHERE name IS NOT null"));

        return "memory-kun";
    }

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SphereDao sphereDao;

    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private CountIdDao countIdDao;
    

    @PostMapping("/update/subject") /* HTML 14 (th:action="@{/form})、メソッド */
    public String updateSubject(UpdateSubjectForm form, RedirectAttributes attr) {

        int size = 0;
        for (int i = 1; i <= 5; i++) {
            if (subjectDao.findSubjectByNumber(i).getName() == null) {
                size = i;
                break;
            }
        }

        subjectDao.updateSubject(form.getSubjectName(), size);
        List<Subject> subjects = subjectDao.findAll();
        attr.addFlashAttribute("subjects", subjects);

        return "redirect:/memory-kun";
    }

    @PostMapping("/to/sphere")
    public String toSphere(ToSphereForm form, RedirectAttributes attr) {

        List<Sphere> spheres = sphereDao.findSphereBySubjectNumber(form.getSubjectNumber());

        attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("countCheckedWords",
                wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));
        attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
        attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
        attr.addFlashAttribute("spheres", spheres);

        return "redirect:/memory-kun";
    }

    @PostMapping("/update/sphere") /* HTML 14 (th:action="@{/form})、メソッド */
    public String updateSphere(UpdateSphereForm form, RedirectAttributes attr) {

        int size = 0;
        for (int i = 1; i <= 10; i++) {
            if (sphereDao.findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), i)
                    .getName() == null) {
                size = i;
                break;
            }
        }

        sphereDao.updateSphere(form.getSphereName(), size, form.getSubjectNumber());

        List<Sphere> spheres = sphereDao.findSphereBySubjectNumber(form.getSubjectNumber());

        attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("countCheckedWords",
                wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));
        attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
        attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
        attr.addFlashAttribute("subjects", subjectDao.findAll());
        attr.addFlashAttribute("spheres", spheres);

        return "redirect:/memory-kun";
    }

    @PostMapping("/to/word")
    public String toWord(ToWordForm form, RedirectAttributes attr) {

        List<Sphere> spheres = sphereDao.findSphereBySubjectNumber(form.getSubjectNumber());

        List<Word> words = wordDao.findWordBySubjectNumberAndSphereNumber(form.getSubjectNumber(),
                form.getSphereNumber());

        attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("countCheckedWords",
                wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));
        attr.addFlashAttribute("countSphereWords",
                wordDao.countWordsBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
        attr.addFlashAttribute("countSphereCheckedWords", wordDao.countWordsByAndSubjectNumberSphereNumberChecked(
                form.getSubjectNumber(), form.getSphereNumber(), true));
        attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
        attr.addFlashAttribute("currentSphere", sphereDao
                .findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber())
                .getName());
        attr.addFlashAttribute("spheres", spheres);
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
        attr.addFlashAttribute("sphereNumber", form.getSphereNumber());

        return "redirect:/memory-kun";
    }

    @PostMapping("/update/word")
    public String updateWord(UpdateWordForm form, RedirectAttributes attr) {
        int count;

        if(countIdDao.countId() == 0){
             count = 1;
        }else{
            count= countIdDao.countId();
            count++;
        }
        jdbc.update("INSERT INTO countId VALUES(?)",count);

        
        wordDao.insertWord(form.getWordName(), count, form.getSubjectNumber(), form.getSphereNumber());
        count+=1;

        List<Word> words = wordDao.findWordBySubjectNumberAndSphereNumber(form.getSubjectNumber(),
                form.getSphereNumber());

        attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("countCheckedWords",
                wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));
        attr.addFlashAttribute("countSphereWords",
                wordDao.countWordsBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
        attr.addFlashAttribute("countSphereCheckedWords", wordDao.countWordsByAndSubjectNumberSphereNumberChecked(
                form.getSubjectNumber(), form.getSphereNumber(), true));
        attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
        attr.addFlashAttribute("currentSphere", sphereDao
                .findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber())
                .getName());
        attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
        attr.addFlashAttribute("sphereNumber", form.getSphereNumber());
        attr.addFlashAttribute("subjects", subjectDao.findAll());
        attr.addFlashAttribute("spheres", sphereDao.findSphereBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("words", words);

        return "redirect:/memory-kun";
    }

    @PostMapping("/update/mean")
    public String updateMean(UpdateMeanForm form, RedirectAttributes attr) {

        wordDao.updateMean(form.getMean(), form.getWordNumber());

        List<Word> words = wordDao.findWordBySubjectNumberAndSphereNumber(form.getSubjectNumber(),
                form.getSphereNumber());

        List<Word> means = wordDao.findMeanByWordNumber(form.getWordNumber());

        attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("countCheckedWords",
                wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));
        attr.addFlashAttribute("countSphereWords",
                wordDao.countWordsBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
        attr.addFlashAttribute("countSphereCheckedWords", wordDao.countWordsByAndSubjectNumberSphereNumberChecked(
                form.getSubjectNumber(), form.getSphereNumber(), true));
        attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
        attr.addFlashAttribute("currentSphere", sphereDao
                .findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber())
                .getName());
        attr.addFlashAttribute("currentWord", wordDao.findCurrentWordByWordNumber(form.getWordNumber()).getName());
        attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
        attr.addFlashAttribute("sphereNumber", form.getSphereNumber());
        attr.addFlashAttribute("wordNumber", form.getWordNumber());
        attr.addFlashAttribute("subjects", subjectDao.findAll());
        attr.addFlashAttribute("spheres", sphereDao.findSphereBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("means", means);
        attr.addFlashAttribute("words", words);

        return "redirect:/memory-kun";
    }

    @PostMapping("/to/mean")
    public String toMean(ToMeanForm form, RedirectAttributes attr) {

        List<Word> means = wordDao.findMeanByWordNumber(form.getWordNumber());

        List<Word> words = wordDao.findWordBySubjectNumberAndSphereNumber(form.getSubjectNumber(),
                form.getSphereNumber());

        attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("countCheckedWords",
                wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));
        attr.addFlashAttribute("countSphereWords",
                wordDao.countWordsBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
        attr.addFlashAttribute("countSphereCheckedWords", wordDao.countWordsByAndSubjectNumberSphereNumberChecked(
                form.getSubjectNumber(), form.getSphereNumber(), true));
        attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
        attr.addFlashAttribute("currentSphere", sphereDao
                .findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber())
                .getName());
        attr.addFlashAttribute("currentWord", wordDao.findCurrentWordByWordNumber(form.getWordNumber()).getName());
        attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
        attr.addFlashAttribute("sphereNumber", form.getSphereNumber());
        attr.addFlashAttribute("wordNumber", form.getWordNumber());
        attr.addFlashAttribute("subjects", subjectDao.findAll());
        attr.addFlashAttribute("spheres", sphereDao.findSphereBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("words", words);
        attr.addFlashAttribute("means", means);

        return "redirect:/memory-kun";
    }

    @PostMapping("/display/mean")
    public String displayMean(DisplayMeanForm form, RedirectAttributes attr) {

        wordDao.updateChecked(form.getChecked(), form.getWordNumber());

        List<Word> means = wordDao.findMeanByWordNumber(form.getWordNumber());

        attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("countCheckedWords",
                wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));
        attr.addFlashAttribute("countSphereWords",
                wordDao.countWordsBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
        attr.addFlashAttribute("countSphereCheckedWords", wordDao.countWordsByAndSubjectNumberSphereNumberChecked(
                form.getSubjectNumber(), form.getSphereNumber(), true));
        attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
        attr.addFlashAttribute("currentSphere", sphereDao
                .findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber())
                .getName());
        attr.addFlashAttribute("currentWord", wordDao.findCurrentWordByWordNumber(form.getWordNumber()).getName());
        attr.addFlashAttribute("sphereNumber", form.getSphereNumber());
        attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
        attr.addFlashAttribute("sphereNumber", form.getSphereNumber());
        attr.addFlashAttribute("wordNumber", form.getWordNumber());
        attr.addFlashAttribute("checked", form.getChecked());
        attr.addFlashAttribute("subjects", subjectDao.findAll());
        attr.addFlashAttribute("spheres", sphereDao.findSphereBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("words",
                wordDao.findWordBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
        attr.addFlashAttribute("means", means);

        return "redirect:/memory-kun";
    }

    @PostMapping("/current/subject")
    public String currentSubject(CurrentSubjectForm form, RedirectAttributes attr) {

        subjectDao.deleteSubject(form.getDeleteSubject());
        sphereDao.deleteSphereBySubjectNumber(form.getSubjectNumber());
        wordDao.deleteWordBySubjectNumber(form.getSubjectNumber());

        attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
        attr.addFlashAttribute("countCheckedWords",
                wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));

        if (subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName() == null) {
            return "redirect:/memory-kun";
        } else {
            attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
            attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
        }

        return "redirect:/memory-kun";

    }

    @PostMapping("/current/sphere")
    public String currentSphere(CurrentSphereForm form, RedirectAttributes attr) {

        sphereDao.deleteSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getDeleteSphere());
        wordDao.deleteWordBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber());

        attr.addFlashAttribute("countSphereWords",
                wordDao.countWordsBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
        attr.addFlashAttribute("countSphereCheckedWords", wordDao.countWordsByAndSubjectNumberSphereNumberChecked(
                form.getSubjectNumber(), form.getSphereNumber(), true));

        if (sphereDao.findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber())
                .getName() == null) {
            attr.addFlashAttribute("spheres", sphereDao.findSphereBySubjectNumber(form.getSubjectNumber()));
            attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
            attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
            attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
            attr.addFlashAttribute("countCheckedWords",
                    wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));

            return "redirect:/memory-kun";
        } else {
            attr.addFlashAttribute("currentSphere", sphereDao
                    .findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber())
                    .getName());
            attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
            attr.addFlashAttribute("sphereNumber", form.getSphereNumber());
        }

        return "redirect:/memory-kun";

    }

    @PostMapping("/current/word")
    public String currentWord(CurrentWordForm form, RedirectAttributes attr) {

        wordDao.deleteWordById(form.getDeleteWord());
        if (wordDao.findCurrentWordByWordNumber(form.getWordNumber()).getName() == null) {
            attr.addFlashAttribute("spheres", sphereDao.findSphereBySubjectNumber(form.getSubjectNumber()));
            attr.addFlashAttribute("words",
                    wordDao.findWordBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
            attr.addFlashAttribute("subjectNumber", form.getSubjectNumber());
            attr.addFlashAttribute("sphereNumber", form.getSphereNumber());
            attr.addFlashAttribute("currentSubject", subjectDao.findSubjectByNumber(form.getSubjectNumber()).getName());
            attr.addFlashAttribute("currentSphere", sphereDao
                    .findCurrentSphereBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber())
                    .getName());
            attr.addFlashAttribute("countAllWords", wordDao.countWordsBySubjectNumber(form.getSubjectNumber()));
            attr.addFlashAttribute("countCheckedWords",
                    wordDao.countWordsBySubjectNumberChecked(form.getSubjectNumber(), true));
            attr.addFlashAttribute("countSphereWords",
                    wordDao.countWordsBySubjectNumberAndSphereNumber(form.getSubjectNumber(), form.getSphereNumber()));
            attr.addFlashAttribute("countSphereCheckedWords", wordDao.countWordsByAndSubjectNumberSphereNumberChecked(
                    form.getSubjectNumber(), form.getSphereNumber(), true));
            return "redirect:/memory-kun";
        } else {
            attr.addFlashAttribute("currentWord", wordDao.findCurrentWordByWordNumber(form.getWordNumber()).getName());
        }

        return "redirect:/memory-kun";

    }

}
