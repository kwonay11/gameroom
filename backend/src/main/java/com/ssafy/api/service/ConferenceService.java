package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

public interface ConferenceService {

    int register (ConferenceRegisterPostReq dto);


}
