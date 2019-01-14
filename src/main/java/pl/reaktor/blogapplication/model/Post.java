package pl.reaktor.blogapplication.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_b;
   @Size(min = 3, max = 255, message = "POLE MUSI MIEĆ POMIEDZY 3 A 255 ZNAKOW")
   private String title;
   @Size(min = 3)
   private String content;
   @Size(min = 3, max = 255)
   private String author;
   private Date date_added;
   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post")
   private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
        this.date_added = new Date();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Post() {
        this.date_added = new Date();
    }

    public Long getId_b() {
        return id_b;
    }

    public void setId_b(Long id_b) {
        this.id_b = id_b;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }
}
