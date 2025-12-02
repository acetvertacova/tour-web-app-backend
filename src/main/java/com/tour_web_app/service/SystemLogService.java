package com.tour_web_app.service;

import com.tour_web_app.entity.SystemLog;
import com.tour_web_app.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemLogService {
    private final SystemLogRepository systemLogRepository;

    public SystemLog create(SystemLog systemLog) {
        return systemLogRepository.save(systemLog);
    }
}
