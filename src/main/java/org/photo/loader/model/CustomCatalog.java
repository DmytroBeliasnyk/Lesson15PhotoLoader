package org.photo.loader.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomCatalog {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private CatalogName name;

    public CustomCatalog() {
    }

    public CustomCatalog(CatalogName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatalogName getName() {
        return name;
    }

    public void setName(CatalogName name) {
        this.name = name;
    }

}
