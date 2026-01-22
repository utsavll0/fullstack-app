package com.cloudmonitor.app.service;

import com.cloudmonitor.app.model.CloudResource;
import com.cloudmonitor.app.repository.CloudResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CloudResourceService {

    @Autowired
    private CloudResourceRepository cloudResourceRepository;

    public List<CloudResource> getAllResources() {
        return cloudResourceRepository.findAll();
    }

    public Optional<CloudResource> getResourceById(Long id) {
        return cloudResourceRepository.findById(id);
    }

    public CloudResource createResource(CloudResource resource) {
        resource.setCreatedAt(LocalDateTime.now());
        resource.setLastUpdated(LocalDateTime.now());
        return cloudResourceRepository.save(resource);
    }

    public CloudResource updateResource(Long id, CloudResource resourceDetails) {
        CloudResource resource = cloudResourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));

        resource.setResourceName(resourceDetails.getResourceName());
        resource.setResourceType(resourceDetails.getResourceType());
        resource.setStatus(resourceDetails.getStatus());
        resource.setRegion(resourceDetails.getRegion());
        resource.setCpuUsage(resourceDetails.getCpuUsage());
        resource.setMemoryUsage(resourceDetails.getMemoryUsage());
        resource.setDiskUsage(resourceDetails.getDiskUsage());
        resource.setLastUpdated(LocalDateTime.now());

        return cloudResourceRepository.save(resource);
    }

    public void deleteResource(Long id) {
        cloudResourceRepository.deleteById(id);
    }

    public List<CloudResource> getResourcesByType(String type) {
        return cloudResourceRepository.findByResourceType(type);
    }

    public List<CloudResource> getResourcesByStatus(String status) {
        return cloudResourceRepository.findByStatus(status);
    }
}
