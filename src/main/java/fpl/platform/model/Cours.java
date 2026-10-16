package fpl.platform.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cours")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id", nullable = false)
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
    private User enseignant;

    @OneToMany(mappedBy = "cours", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Inscription> inscriptions;
}