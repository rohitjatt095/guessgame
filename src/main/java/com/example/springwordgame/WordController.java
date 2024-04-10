package com.example.springwordgame;

import com.example.springwordgame.WordRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class WordController {
    @Autowired
    private WordRepository wordRepository;
    private WordService wordService;




    @GetMapping("/khush")
    public String showForm(Model model) {
        model.addAttribute("levels", new String[]{"Easy", "Medium", "Hard"});
        model.addAttribute("selectedLevel", "");
        return "wordForm";
    }

    @PostMapping("/word")
    public String getWord(@ModelAttribute("selectedLevel") String selectedLevel,
                          Model model, HttpSession session) {
        Word wordObj = wordRepository.findRandomWordByLevel(selectedLevel);
        model.addAttribute("word", wordObj);
        session.setAttribute("word", wordObj);
        session.setAttribute("chancesLeft", 5); // Set initial chances
        return "redirect:/showWord";
    }

    @GetMapping("/showWord")
    public String showWord(HttpSession session, Model model) {
        Word wordarray = (Word) session.getAttribute("word");
        model.addAttribute("GivenHints", wordarray.getHints());
        return "wordInput";
    }



    @PostMapping("/getWord")
    public String login(@RequestParam String word, HttpSession session, Model model) {
        Word wordObj = (Word) session.getAttribute("word");
        int tries = (int) session.getAttribute("chancesLeft");
        model.addAttribute("GivenHints", wordObj.getHints());
        User user = (User) session.getAttribute("user");

        if (word != null && wordObj.getWord().equalsIgnoreCase(word)) {
            // Update the score when the user wins
            if (user != null) {
                int currentScore = user.getScore();
                user.setScore(currentScore + 1);
                userservice.updateScore(user.getId(), user.getScore());
            }

            model.addAttribute("message", "Congratulations! You win");
            session.removeAttribute("chancesLeft");
            userservice.updateScore(user.getId(), user.getScore()); // Save the score again
            return "redirect:/khush";
        } else {
            tries--;
            if (tries == 0) {
                model.addAttribute("message", "Sorry! You Lose.");
                session.removeAttribute("chancesLeft");
                return "redirect:/khush";
            }
            else {
                model.addAttribute("message", "Please Try again. Left Chances: " + tries);
                session.setAttribute("chancesLeft", tries);
                return "/wordInput";
            }
            // Return the same page to play again

        }
    }




    @Autowired
    private UserService userservice;
    @GetMapping("/Score/{userId}/{newScore}")
    @ResponseBody()
    public String updateScore(@PathVariable Long userId, @PathVariable int
            newScore) {
        userservice.updateScore(userId, newScore);
        return "Score updated successfully";
    }
}





