package ru.shulenin.farmworkerapi.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shulenin.farmworkerapi.datasource.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
