package com.example.hagimabackend.repository;

import com.example.hagimabackend.entity.DefineWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarningRepository extends JpaRepository<DefineWarning, Long> {

    @Query("select w from DefineWarning w where w.informal = :informal")
    List<DefineWarning> getWarningByInformal(Boolean informal);
}
