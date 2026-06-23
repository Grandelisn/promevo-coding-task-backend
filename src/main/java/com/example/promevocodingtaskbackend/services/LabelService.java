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
            Label googleLabel =
                    gmailClient.users().labels().get(userId, id).execute();

            Label labelDto = new Label();
            labelDto.setId(googleLabel.getId());
            labelDto.setName(googleLabel.getName());

            if (googleLabel.getMessageListVisibility() != null) {
                labelDto.setMessageListVisibility(String.valueOf(MessageListVisibility.valueOf(googleLabel.getMessageListVisibility().toUpperCase())));
            }
            if (googleLabel.getLabelListVisibility() != null) {
                labelDto.setLabelListVisibility(String.valueOf(LabelListVisibility.valueOf(googleLabel.getLabelListVisibility().replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase())));
            }
            if (googleLabel.getType() != null) {
                labelDto.setType(String.valueOf(Type.valueOf(googleLabel.getType().toUpperCase())));
            }

            labelDto.setMessagesTotal(googleLabel.getMessagesTotal());
            labelDto.setMessagesUnread(googleLabel.getMessagesUnread());
            labelDto.setThreadsTotal(googleLabel.getThreadsTotal());
            labelDto.setThreadsUnread(googleLabel.getThreadsUnread());

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
            Label googleLabel = new Label();
            googleLabel.setName(labelDto.getName());
            googleLabel.setMessageListVisibility(labelDto.getMessageListVisibility().getValue());
            googleLabel.setLabelListVisibility(labelDto.getLabelListVisibility().getValue());
            Label createdGoogleLabel =
                    gmailClient.users().labels().create(userId, googleLabel).execute();
            labelDto.setId(createdGoogleLabel.getId());
            return labelDto;

        } catch (IOException e) {
            throw new RuntimeException("Failed to create Gmail label", e);
        }
    }
    public Label updateLabel(String userId, String id, @NonNull Label labelDto) {
        try {
            Label googleLabel = new Label();
            googleLabel.setId(id);
            googleLabel.setName(labelDto.getName());

            if (labelDto.getMessageListVisibility() != null) {
                googleLabel.setMessageListVisibility(labelDto.getMessageListVisibility());
            }
            if (labelDto.getLabelListVisibility() != null) {
                googleLabel.setLabelListVisibility(labelDto.getLabelListVisibility());
            }

            Label updated =
                    gmailClient.users().labels().update(userId, id, googleLabel).execute();

            return labelDto;

        } catch (IOException e) {
            throw new RuntimeException("Failed to update Gmail label: " + id, e);
        }
    }
    public void deleteLabel(String userId, String id) {
        try {
            gmailClient.users().labels().delete(userId, id).execute();

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete Gmail label: " + id, e);
        }
    }
    public ListLabelsResponse listLabels(String userId) {
        try {
            ListLabelsResponse googleResponse =
                    gmailClient.users().labels().list(userId).execute();

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
