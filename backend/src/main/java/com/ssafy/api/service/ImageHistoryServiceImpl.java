package com.ssafy.api.service;

import com.ssafy.db.entity.ImageHistory;
import com.ssafy.db.repository.ImageHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ImageHistoryService")
public class ImageHistoryServiceImpl implements ImageHistoryService {

    @Autowired
    ImageHistoryRepository imageHistoryRepository;

    @Override
    public ImageHistory saveImageHistory(ImageHistory imageHistory) {
        return imageHistoryRepository.save(imageHistory);
    }
}
