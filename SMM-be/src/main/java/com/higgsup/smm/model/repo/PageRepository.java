package com.higgsup.smm.model.repo;

import com.higgsup.smm.model.entity.Page;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by DangThanhLinh on 06/01/2017.
 */
public interface PageRepository extends CrudRepository<Page, String> {
    Page findByPageId(String pageId);
}
