package org.dreven.quello.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreven.quello.controller.dto.dashboard.DashboardSearchReq;
import org.dreven.quello.controller.dto.question.QuestionDTO;
import org.dreven.quello.dao.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final QuestionMapper questionMapper;

    public QuestionDTO getOverview(DashboardSearchReq req) {
        return null;
    }
}
