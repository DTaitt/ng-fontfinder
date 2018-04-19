package com.example.setsapi.models;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "font_sets")
public class FontSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "favoriteId")
    private Long favoriteId;

    @Column(name = "set_name")
    private String setName;


    public FontSet(Long favoriteId, String setName) {
        this.setName = setName;
        this.favoriteId = favoriteId;
        this.setName = setName;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public String getSetName() {
        return setName;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }
}
