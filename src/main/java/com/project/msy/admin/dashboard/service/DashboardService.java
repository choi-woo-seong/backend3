package com.project.msy.admin.dashboard.service;

import com.project.msy.admin.dashboard.dto.*;

import java.util.List;

public interface DashboardService {

     long getTotalFacilityCount();

    List<UserDailySignupDto> getDailyUserGrowth();
}