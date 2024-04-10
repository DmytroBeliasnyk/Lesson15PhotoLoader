package org.photo.loader.model;

import jakarta.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Lob
    private String base64String;
    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private CustomCatalog catalog;

    public Photo() {
    }

    public Photo(String name, String base64String, CustomCatalog catalog) {
        this.name = name;
        this.base64String = base64String;
        this.catalog = catalog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    public CustomCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(CustomCatalog catalog) {
        this.catalog = catalog;
    }
}

