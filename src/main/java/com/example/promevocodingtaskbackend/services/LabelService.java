package com.example.promevocodingtaskbackend.services;

import com.example.promevocodingtaskbackend.PromevoCodingTaskBackendApplication;
import com.example.promevocodingtaskbackend.models.LabelDTO;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.LabelColor;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.example.promevocodingtaskbackend.models.enums.LabelListVisibility;
import com.example.promevocodingtaskbackend.models.enums.MessageListVisibility;
import com.example.promevocodingtaskbackend.models.enums.Type;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class LabelService {

    private final Gmail gmailClient;

    public LabelService(PromevoCodingTaskBackendApplication gmailService) {
        this.gmailClient = gmailService.getGmailClient();
    }
    public Label getLabel(String userId, String id) {
        try {
            // Execute the GET request via the Gmail API
            Label googleLabel =
                    gmailClient.users().labels().get(userId, id).execute();

            // Map the official Google API model back to your DTO
            Label labelDto = new Label();
            labelDto.setId(googleLabel.getId());
            labelDto.setName(googleLabel.getName());

            // Handle Enums (Ensure null checks if these aren't guaranteed on every label)
            if (googleLabel.getMessageListVisibility() != null) {
                labelDto.setMessageListVisibility(String.valueOf(MessageListVisibility.valueOf(googleLabel.getMessageListVisibility().toUpperCase())));
            }
            if (googleLabel.getLabelListVisibility() != null) {
                labelDto.setLabelListVisibility(String.valueOf(LabelListVisibility.valueOf(googleLabel.getLabelListVisibility().replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase())));
            }
            if (googleLabel.getType() != null) {
                labelDto.setType(String.valueOf(Type.valueOf(googleLabel.getType().toUpperCase())));
            }

            // Map standard integer fields
            labelDto.setMessagesTotal(googleLabel.getMessagesTotal());
            labelDto.setMessagesUnread(googleLabel.getMessagesUnread());
            labelDto.setThreadsTotal(googleLabel.getThreadsTotal());
            labelDto.setThreadsUnread(googleLabel.getThreadsUnread());

            // Map color if present
            if (googleLabel.getColor() != null) {
                LabelColor colorDto = new LabelColor();
                colorDto.setTextColor(googleLabel.getColor().getTextColor());
                colorDto.setBackgroundColor(googleLabel.getColor().getBackgroundColor());
                labelDto.setColor(colorDto);
            }

            return labelDto;

        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw new RuntimeException("Gmail API returned an error for label " + id + ": " + e.getDetails().getMessage(), e);

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve Gmail label: " + id, e);
        }
    }
    public LabelDTO createLabel(String userId, @NonNull LabelDTO labelDto) {
        try {
            // Map your custom DTO to the Google API Label class
            Label googleLabel = new Label();
            googleLabel.setName(labelDto.getName());
            googleLabel.setMessageListVisibility(labelDto.getMessageListVisibility().getValue());
            googleLabel.setLabelListVisibility(labelDto.getLabelListVisibility().getValue());

            // Note: Color mapping would go here if type is USER

            // Execute the API call
            Label createdGoogleLabel =
                    gmailClient.users().labels().create(userId, googleLabel).execute();

            // Map the Google response back to your DTO
            labelDto.setId(createdGoogleLabel.getId());
            return labelDto;

        } catch (IOException e) {
            throw new RuntimeException("Failed to create Gmail label", e);
        }
    }
    public Label updateLabel(String userId, String id, @NonNull Label labelDto) {
        try {
            // Map your DTO to the official Google API Label model
            Label googleLabel = new Label();

            // The ID must be set on the payload for the update operation
            googleLabel.setId(id);
            googleLabel.setName(labelDto.getName());

            if (labelDto.getMessageListVisibility() != null) {
                googleLabel.setMessageListVisibility(labelDto.getMessageListVisibility());
            }
            if (labelDto.getLabelListVisibility() != null) {
                googleLabel.setLabelListVisibility(labelDto.getLabelListVisibility());
            }

            // Execute the PUT request via the Gmail API
            Label updated =
                    gmailClient.users().labels().update(userId, id, googleLabel).execute();

            // Return the updated state
            return labelDto;

        } catch (IOException e) {
            throw new RuntimeException("Failed to update Gmail label: " + id, e);
        }
    }
    public void deleteLabel(String userId, String id) {
        try {
            // Execute the DELETE request via the Gmail API
            gmailClient.users().labels().delete(userId, id).execute();

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete Gmail label: " + id, e);
        }
    }
    public ListLabelsResponse listLabels(String userId) {
        try {
            // Execute the GET request via the Gmail API
            ListLabelsResponse googleResponse =
                    gmailClient.users().labels().list(userId).execute();

            // Check for null to avoid NullPointerExceptions if the user has no labels
            List<Label> googleLabels = googleResponse.getLabels();
            if (googleLabels == null || googleLabels.isEmpty()) {
                return new ListLabelsResponse();
            }

            return googleResponse;

        } catch (IOException e) {
            throw new RuntimeException("Failed to list Gmail labels for user: " + userId, e);
        }
    }
}
