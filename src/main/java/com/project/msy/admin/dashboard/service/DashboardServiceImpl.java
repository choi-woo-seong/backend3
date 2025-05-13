package com.project.msy.admin.dashboard.service;

import com.project.msy.admin.dashboard.dto.*;
import com.project.msy.order.repository.OrderRepository;
import com.project.msy.stats.repository.StatsRepository;
import com.project.msy.stats.entity.Stats;
import com.project.msy.user.repository.UserRepository;
import com.project.msy.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrderRepository orderRepo;
    private final StatsRepository statsRepo;
    private final UserRepository userRepo;
    private final FacilityRepository facilityRepo;

    @Override
    public long getTotalFacilityCount() {
        return facilityRepo.count();
    }

    @Override
    public List<UserDailySignupDto> getDailyUserGrowth() {
        return userRepo.findDailySignupsForLast30Days();
    }
}