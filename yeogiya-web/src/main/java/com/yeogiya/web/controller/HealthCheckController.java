package com.yeogiya.web.controller;

import com.yeogiya.web.logger.HealthCheckLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HealthCheckController {

    private final DataSource dataSource;
    private final HealthCheckLogger healthCheckLogger;

    @GetMapping("/health-check")
    public void healthCheck() {
        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        healthCheckLogger.log();
    }
}
