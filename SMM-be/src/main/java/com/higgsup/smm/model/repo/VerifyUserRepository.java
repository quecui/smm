package com.higgsup.smm.model.repo;

import com.higgsup.smm.model.entity.VerifyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by StormSpirit on 1/11/2017.
 */
@Repository
public interface VerifyUserRepository extends CrudRepository<VerifyUser, Integer> {
     VerifyUser findByTokenVerify(String tokenVerify);
}
