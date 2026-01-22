package com.cloudmonitor.app.repository;

import com.cloudmonitor.app.model.CloudResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudResourceRepository extends JpaRepository<CloudResource, Long> {
    List<CloudResource> findByResourceType(String resourceType);
    List<CloudResource> findByStatus(String status);
}
