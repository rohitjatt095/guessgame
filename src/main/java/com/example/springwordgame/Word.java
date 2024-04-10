package com.example.springwordgame;
import jakarta.persistence.*;


@Entity
@Table(name = "db_word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private String hints;
    private String level;

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getHints() {
        return hints;
    }

    public String getLevel() {
        return level;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
