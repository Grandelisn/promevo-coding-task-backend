package services;

import com.example.promevocodingtaskbackend.PromevoCodingTaskBackendApplication;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class LabelService {

    private final Gmail gmailClient;

    public LabelService(PromevoCodingTaskBackendApplication gmailService) {
        this.gmailClient = gmailService.getGmailClient();
    }

    public Label createLabel(String userId, Label labelDto) {
        try {
            // Map your custom DTO to the Google API Label class
            Label googleLabel = new Label();
            googleLabel.setName(labelDto.getName());
            googleLabel.setMessageListVisibility(labelDto.getMessageListVisibility());
            googleLabel.setLabelListVisibility(labelDto.getLabelListVisibility());

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
    public Label updateLabel(String userId, String id, Label labelDto) {
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
}
