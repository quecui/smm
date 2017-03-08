package com.higgsup.smm.service;



import com.higgsup.smm.dto.AccessTokenDTO;
import com.higgsup.smm.dto.AccessTokenValidationResultDTO;
import com.higgsup.smm.model.entity.VerifyUser;

import java.io.IOException;

/**
 * Created by DangThanhLinh on 27/12/2016.
 */
public interface IAccessTokenService {
    AccessTokenValidationResultDTO exchange(AccessTokenDTO accessToken) throws IOException;
}
