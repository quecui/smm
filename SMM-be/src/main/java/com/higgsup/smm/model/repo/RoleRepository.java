package com.higgsup.smm.model.repo;

import com.higgsup.smm.model.entity.OAuthUser;
import com.higgsup.smm.model.entity.Page;
import com.higgsup.smm.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DangThanhLinh on 06/01/2017.
 */

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByUserAndPage(OAuthUser oAuthUser, Page page);
    List <Role> findByUser(OAuthUser user);
    Role findByUserProviderUserIdAndPagePageId(String userId, String pageId);
    List<Role> findByPagePageId(String pageId);
    Role findByUserProviderUserId(String userId);
}
