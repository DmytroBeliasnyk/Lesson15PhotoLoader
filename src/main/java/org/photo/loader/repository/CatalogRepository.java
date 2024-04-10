package org.photo.loader.repository;

import org.photo.loader.model.CatalogName;
import org.photo.loader.model.CustomCatalog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CustomCatalog, Long> {
    CustomCatalog findByName(CatalogName name);

    long countByName(CatalogName name);
}
