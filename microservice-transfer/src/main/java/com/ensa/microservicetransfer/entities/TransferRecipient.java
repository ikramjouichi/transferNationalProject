package com.ensa.microservicetransfer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransferRecipient {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "transferMultiple_idforRecipeint")
    private TransferMultiple transfer;

    @ManyToOne
    @JoinColumn(name = "recipients_client_id")
    private Client recipientClient;

    // Ajoutez d'autres champs si nécessaire, par exemple, le statut du transfert pour ce destinataire
}
/*
private List<TransferRecipient> transferRecipients;
TransferMultiple transfer = new TransferMultiple();
// ... (définissez les autres propriétés du transfert)

List<TransferRecipient> recipients = new ArrayList<>();
for (Client recipientClient : recipientClients) {
    TransferRecipient transferRecipient = new TransferRecipient();
    transferRecipient.setTransfer(transfer);
    transferRecipient.setRecipientClient(recipientClient);
    // ... (définissez d'autres informations si nécessaire)
    recipients.add(transferRecipient);
}

transfer.setTransferRecipients(recipients);
// Enregistrez l'entité TransferMultiple dans la base de données

 */