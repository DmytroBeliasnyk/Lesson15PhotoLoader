package org.photo.loader.service;

import org.photo.loader.model.CatalogName;
import org.photo.loader.model.CustomCatalog;
import org.photo.loader.model.Photo;
import org.photo.loader.repository.CatalogRepository;
import org.photo.loader.repository.PhotoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeneralService {
    private final PhotoRepository photoRepository;
    private final CatalogRepository catalogRepository;

    public GeneralService(PhotoRepository photoRepository, CatalogRepository catalogRepository) {
        this.photoRepository = photoRepository;
        this.catalogRepository = catalogRepository;
    }

    @Transactional
    public void save(Photo photo) {
        photoRepository.save(photo);
    }

    @Transactional
    public void save(CustomCatalog catalog) {
        catalogRepository.save(catalog);
    }

    @Transactional(readOnly = true)
    public List<Photo> getAll(Pageable pageable) {
        return photoRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<Photo> findByCatalog(CatalogName catalogName, Pageable pageable) {
        CustomCatalog catalog = findCatalog(catalogName);
        return photoRepository.findByCatalog(catalog, pageable);
    }

    @Transactional(readOnly = true)
    public CustomCatalog findCatalog(CatalogName name) {
        return catalogRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public long count() {
        return photoRepository.count();
    }

    @Transactional(readOnly = true)
    public long countByCatalog(CatalogName name) {
        return catalogRepository.countByName(name);
    }
}
