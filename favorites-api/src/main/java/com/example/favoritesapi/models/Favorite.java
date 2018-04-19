package com.example.favoritesapi.models;

import lombok.*;

import javax.persistence.*;


@Data
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Favorites")
public class Favorite {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "family")
    private String family;

    @Column(name = "category")
    private String category;

    @Column(name = "url")
    private String url;

    public Favorite(String id, String family, String category, String url) {
        this.id = id;
        this.family = family;
        this.category = category;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getFamily() {
        return family;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
