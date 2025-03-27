package org.example.spring2025demo3rest.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateBuilt;

    private int value;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateBuilt() {
        return dateBuilt;
    }

    public void setDateBuilt(LocalDate dateBuilt) {
        this.dateBuilt = dateBuilt;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
