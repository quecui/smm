package com.higgsup.smm.model.repo;

import com.higgsup.smm.model.entity.OAuthUser;
import com.higgsup.smm.model.entity.Page;
import com.higgsup.smm.model.entity.PageAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DangThanhLinh on 10/01/2017.
 */
@Repository
public interface PageAcessTokenRepository extends CrudRepository<PageAccessToken, Long> {
    PageAccessToken findByAccessToken(String pageAccessToken);
    PageAccessToken findByPageAndUsers(Page page, OAuthUser oAuthUser);
    PageAccessToken findByPagePageId(String pageId);
    PageAccessToken findByPage(Page page);
}
