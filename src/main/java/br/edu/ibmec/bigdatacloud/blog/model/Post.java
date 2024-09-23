package br.edu.ibmec.bigdatacloud.blog.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String article;

    @Column
    private LocalDateTime dfCreateDate;

    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "post_id")
    private List<Comment> comments;

}
