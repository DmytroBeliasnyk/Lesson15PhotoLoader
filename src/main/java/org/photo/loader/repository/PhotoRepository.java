package org.photo.loader.repository;

import org.photo.loader.model.CustomCatalog;
import org.photo.loader.model.Photo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByCatalog(CustomCatalog catalog, Pageable pageable);
}
