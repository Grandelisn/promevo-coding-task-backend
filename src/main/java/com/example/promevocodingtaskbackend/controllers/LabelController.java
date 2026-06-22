package com.example.promevocodingtaskbackend.controllers;

import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.promevocodingtaskbackend.services.LabelService;

@RestController
@RequestMapping("/api/v1/users/{userId}/labels")
public class LabelController {

    private final LabelService labelService;

    // Constructor injection (Best practice for Spring Boot dependencies)
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    /**
     * Creates a new label in the user's mailbox.
     *
     * @param userId The user's email address. The special value "me" can be used to indicate the authenticated user.
     * @param label  The Label object containing the details of the label to create.
     * @return A ResponseEntity containing the newly created Label and a 201 CREATED status.
     */
    @PostMapping
    public ResponseEntity<Label> createLabel(
            @PathVariable String userId,
            @RequestBody Label label) {

        // Delegate the actual Gmail API execution to the Service layer
        Label createdLabel = labelService.createLabel(userId, label);

        return new ResponseEntity<>(createdLabel, HttpStatus.CREATED);
    }
    /**
     * Retrieves a specific label from the user's mailbox.
     *
     * @param userId The user's email address. Use "me" for the authenticated user.
     * @param id     The ID of the label to retrieve.
     * @return A ResponseEntity containing the requested Label and a 200 OK status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Label> getLabel(
            @PathVariable String userId,
            @PathVariable String id) {

        // Delegate the retrieval logic to the Service layer
        Label retrievedLabel = labelService.getLabel(userId, id);

        // Return 200 OK with the label payload
        return ResponseEntity.ok(retrievedLabel);
    }
    /**
     * Lists all labels in the user's mailbox.
     *
     * @param userId The user's email address. Use "me" for the authenticated user.
     * @return A ResponseEntity containing the ListLabelsResponse wrapper and a 200 OK status.
     */
    @GetMapping
    public ResponseEntity<ListLabelsResponse> listLabels(
            @PathVariable String userId) {
        // Delegate the retrieval of the label list to the Service layer
        ListLabelsResponse response = labelService.listLabels(userId);

        // Return 200 OK with the wrapper payload
        return ResponseEntity.ok(response);
    }
    /**
     * Updates an existing label in the user's mailbox.
     *
     * @param userId The user's email address. Use "me" for the authenticated user.
     * @param id     The ID of the label to update.
     * @param label  The Label object payload containing the updated configuration.
     * @return A ResponseEntity containing the updated Label and a 200 OK status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Label> updateLabel(
            @PathVariable String userId,
            @PathVariable String id,
            @RequestBody Label label) {

        // Delegate the update logic and Google API execution to the Service layer
        Label updatedLabel = labelService.updateLabel(userId, id, label);

        // Return 200 OK for a successful update operation
        return ResponseEntity.ok(updatedLabel);
    }
    /**
     * Immediately and permanently deletes the specified label.
     *
     * @param userId The user's email address. Use "me" for the authenticated user.
     * @param id     The ID of the label to delete.
     * @return A ResponseEntity with no content and a 204 No Content status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(
            @PathVariable String userId,
            @PathVariable String id) {

        // Delegate the deletion logic to the Service layer
        labelService.deleteLabel(userId, id);

        // Return 204 No Content for a successful deletion with an empty body
        return ResponseEntity.noContent().build();
    }
}
