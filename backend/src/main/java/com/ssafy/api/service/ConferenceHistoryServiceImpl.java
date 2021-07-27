package com.ssafy.api.service;

import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.repository.ConferenceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ConferenceHistoryService")
public class ConferenceHistoryServiceImpl implements ConferenceHistoryService{
    @Autowired
    ConferenceHistoryRepository conferenceHistoryRepository;
    @Override
    public void addConferenceHistory(ConferenceHistory conferenceHistory) {
        conferenceHistoryRepository.save(conferenceHistory);
    }
}
