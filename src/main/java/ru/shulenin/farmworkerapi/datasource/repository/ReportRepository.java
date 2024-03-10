package ru.shulenin.farmworkerapi.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shulenin.farmworkerapi.datasource.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
