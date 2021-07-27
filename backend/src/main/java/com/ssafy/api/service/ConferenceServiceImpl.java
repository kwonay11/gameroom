package com.ssafy.api.service;

import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */

@Service("roomService")
public class RoomServiceImpl implements RoomService{
    @Autowired


    @Autowired
    UserRepository userRepository;
    @Override
    public void removeRoom(String userId){
        Optional<User> user = userRepository.findByUserId(userId);

    }
}
