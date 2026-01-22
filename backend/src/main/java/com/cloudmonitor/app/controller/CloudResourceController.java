package com.cloudmonitor.app.controller;

import com.cloudmonitor.app.model.CloudResource;
import com.cloudmonitor.app.service.CloudResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CloudResourceController {

    @Autowired
    private CloudResourceService cloudResourceService;

    @GetMapping
    public ResponseEntity<List<CloudResource>> getAllResources() {
        return ResponseEntity.ok(cloudResourceService.getAllResources());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CloudResource> getResourceById(@PathVariable Long id) {
        return cloudResourceService.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CloudResource> createResource(@RequestBody CloudResource resource) {
        CloudResource createdResource = cloudResourceService.createResource(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CloudResource> updateResource(
            @PathVariable Long id,
            @RequestBody CloudResource resourceDetails) {
        try {
            CloudResource updatedResource = cloudResourceService.updateResource(id, resourceDetails);
            return ResponseEntity.ok(updatedResource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        cloudResourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<CloudResource>> getResourcesByType(@PathVariable String type) {
        return ResponseEntity.ok(cloudResourceService.getResourcesByType(type));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CloudResource>> getResourcesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(cloudResourceService.getResourcesByStatus(status));
    }
}
