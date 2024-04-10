package org.photo.loader.controller;

import org.photo.loader.model.CatalogName;
import org.photo.loader.model.Photo;
import org.photo.loader.service.GeneralService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MyController {
    private final int PAGE_SIZE = 5;
    private final GeneralService generalService;

    public MyController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @GetMapping("/images")
    public List<Photo> getImages(@RequestParam(name = "page", defaultValue = "0", required = false)
                                 int page) {
        if (page < 0) page = 0;
        return generalService.getAll(PageRequest.of(page, PAGE_SIZE));
    }

    @GetMapping("/catalog")
    public List<Photo> getCatalog(@RequestParam(name = "page", defaultValue = "0", required = false)
                                  int page,
                                  @RequestParam(name = "name", required = false)
                                  String name) {
        if (page < 0) page = 0;
        if (name != null && !name.isEmpty()) {
            CatalogName catalog = CatalogName.valueOf(name.toUpperCase());
            return generalService.findByCatalog(catalog, PageRequest.of(page, PAGE_SIZE));
        }
        return generalService.getAll(PageRequest.of(page, PAGE_SIZE));
    }

    @GetMapping("/pages")
    public Map<String, Long> pages(@RequestParam(name = "catalog", required = false)
                                   String catalogName) {
        long count = 1;
        if (catalogName == null || catalogName.isEmpty()) {
            count = generalService.count();
        }
        return Map.of("pageCount", count, "pageSize", (long) PAGE_SIZE);
    }
}
