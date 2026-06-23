package com.example.promevocodingtaskbackend.controllers;

import com.example.promevocodingtaskbackend.models.LabelDTO;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.promevocodingtaskbackend.services.LabelService;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api/v1/users/{userId}/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping
    public ResponseEntity<LabelDTO> createLabel(
            @PathVariable String userId,
            @RequestBody LabelDTO label) {

        LabelDTO createdLabel = labelService.createLabel(userId, label);

        return new ResponseEntity<>(createdLabel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Label> getLabel(
            @PathVariable String userId,
            @PathVariable String id) {

        Label retrievedLabel = labelService.getLabel(userId, id);
        if (retrievedLabel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(retrievedLabel);
    }

    @GetMapping
    public ResponseEntity<ListLabelsResponse> listLabels(
            @PathVariable String userId) {
        ListLabelsResponse response = labelService.listLabels(userId);
        if (response == null || response.getLabels() == null || response.getLabels().isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns a 204
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Label> updateLabel(
            @PathVariable String userId,
            @PathVariable String id,
            @RequestBody Label label) {

        Label updatedLabel = labelService.updateLabel(userId, id, label);

        return ResponseEntity.ok(updatedLabel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(
            @PathVariable String userId,
            @PathVariable String id) {

        labelService.deleteLabel(userId, id);

        return ResponseEntity.noContent().build();
    }
}
