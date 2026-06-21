package services;

import com.example.promevocodingtaskbackend.PromevoCodingTaskBackendApplication;
import com.google.api.services.gmail.Gmail;
import models.Label;
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
            com.google.api.services.gmail.model.Label googleLabel = new com.google.api.services.gmail.model.Label();
            googleLabel.setName(labelDto.getName());
            googleLabel.setMessageListVisibility(labelDto.getMessageListVisibility().getValue());
            googleLabel.setLabelListVisibility(labelDto.getLabelListVisibility().getValue());

            // Note: Color mapping would go here if type is USER

            // Execute the API call
            com.google.api.services.gmail.model.Label createdGoogleLabel =
                    gmailClient.users().labels().create(userId, googleLabel).execute();

            // Map the Google response back to your DTO
            labelDto.setId(createdGoogleLabel.getId());
            return labelDto;

        } catch (IOException e) {
            throw new RuntimeException("Failed to create Gmail label", e);
        }
    }
}
