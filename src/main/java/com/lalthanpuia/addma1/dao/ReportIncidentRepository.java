package com.lalthanpuia.addma1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.Incident;

public interface ReportIncidentRepository extends JpaRepository<Incident, Integer> {

}
