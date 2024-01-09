package com.ensa.microservicetransfer.services;
import com.ensa.microservicetransfer.dao.*;
import com.ensa.microservicetransfer.dto.CreateEmailTransfer;
import com.ensa.microservicetransfer.dto.CreateTransferMultiple;
import com.ensa.microservicetransfer.entities.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ensa.microservicetransfer.dto.CreateTransferDto;
import com.ensa.microservicetransfer.dto.UpdateStatusDTO;
import com.ensa.microservicetransfer.enums.FeesType;
import com.ensa.microservicetransfer.enums.TransferStatus;
import com.ensa.microservicetransfer.enums.TransferType;
import com.ensa.microservicetransfer.models.ProspectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.List;

@Data
@Service
@Slf4j
public class TransferService {


    private static final Long TRANSFERFEE = 10L;

    private TransferDAO transferDAO;
    private ClientDAO clientDAO;
    private ProspectDAO prospectDAO;
    private SupervisorDAO supervisorDAO;
    //de transfer multiple
    private TransferMultipleDAO transferMultipleDAO;

    @Autowired
    private final RestTemplate restTemplate;
    // de transfer multiple
    @Autowired
    public void setTransferMultipleDAO(TransferMultipleDAO transferMultipleDAO){this.transferMultipleDAO =transferMultipleDAO;}

    public TransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setTransferDAO(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }
    @Autowired
    public void setSupervisorDAO(SupervisorDAO supervisorDAO) {
        this.supervisorDAO=supervisorDAO;
    }

    @Autowired
    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Autowired
    public void setProspectDAO(ProspectDAO prospectDAO) {
        this.prospectDAO = prospectDAO;
    }
    //    public Transfer create(Transfer transfer) {
//        return this.transferDAO.save(transfer);
//    }

    public Optional<Transfer> findById(String id) {
        return this.transferDAO.findById(id);
    }
    public Optional<TransferMultiple> findTransferMultipleById(String id) {
        return this.transferMultipleDAO.findById(id);
    }
    //localhost:8020 microservicewallet
    public String testautherServiceResponse(){
        String chainetest=restTemplate.getForObject("http://microservicewallet/api/client/", String.class);
        System.out.println("je suis dans testautherServiceResponse voici la reponse "+chainetest);
        return chainetest;
    }

    public Transfer create(CreateTransferDto ctDto ,long supervisorId) {
        System.out.println("bugg:je suis pas dans le controller je suis dans service");
        //on crerr un transfer mais on a pas priciser le type de supervisor et son role
        Transfer transfer = new Transfer();
        String generatedIdTransfer =generateRandomId();
        transfer.setTransferId(generatedIdTransfer);
        Supervisor supervisor= this.supervisorDAO.findById(supervisorId).orElse(null);
        System.out.println("bugg:voici le supervisor"+supervisor);
        transfer.setSupervisor(supervisor);
        System.out.println("bugg:voici le transfer avec  supervisor"+transfer);

        if (ctDto.sender.type.equals(CreateTransferDto.TransferClientType.CLIENT)) {
            CreateEmailTransfer createEmailTransfer= new CreateEmailTransfer();
            Client sender = restTemplate.getForObject(
                    "http://microservicewallet/api/client/find/"+ctDto.sender.id, Client.class);
            System.out.println("d'apres  http://microservicewallet/api/client/find/+ctDto.sender.id = "+sender.getClientId());
            System.out.println("voici mon client recupérer"+ sender);
            System.out.println("voici mon client wallet recupérer"+ sender.getWallet());
            System.out.println("voici mon client user info recupérer"+ sender.getUser());
            System.out.println("bug: je suis avant checkValidity(ctDto, sender)");
            checkValidity(ctDto, sender);
            System.out.println("bug: je suis apres checkValidity(ctDto, sender)");
            clientDAO.save(new Client(sender.getClientId(),sender.getUser(),sender.getWallet()));
            transfer.setAmount(ctDto.amount);
            transfer.setTransfer_date(new Date());
            transfer.setStatus(TransferStatus.PENDING);
            transfer.setSenderClient(new Client(ctDto.sender.id));
            System.out.println("bug: maintenant pour recipient");
            if (ctDto.recipient.type.equals(CreateTransferDto.TransferClientType.CLIENT)) {
                System.out.println("bug:si recipient de type client");
                Client recipient = restTemplate
                        .getForObject(
                        "http://microservicewallet/api/client/find/" + ctDto.recipient.id, Client.class);

                if (recipient == null) {
                    throw new RuntimeException("Recipient client not found");
                }


                final String URLbalance = "http://microservicewallet/api/wallet/update/"+recipient.getWallet().getId()+"/"+ctDto.getAmount();
                System.out.println("bug:voici recipient avant changer le solde "+recipient);
                //il faut verifier le type des farais ici!
                //create header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                System.out.println("bug:URLbalance "+URLbalance);
                ResponseEntity<Wallet> newBalancewallet = restTemplate.exchange(
                        URLbalance,
                        HttpMethod.PUT,
                        null,
                        Wallet.class
                );
                System.out.println("bug:URLbalance "+URLbalance);
                if (newBalancewallet.getStatusCode().is2xxSuccessful()) {
                    // Successfully updated
                    Wallet updatedWallet = newBalancewallet.getBody();
                    System.out.println("Updated wallet: " + updatedWallet);
                    recipient.setWallet(updatedWallet);
                } else {
                    // Handle errors
                    System.err.println("Error updating wallet. Status code: " + newBalancewallet.getStatusCodeValue());
                }

                System.out.println("bug:new balance de recipient est :"+newBalancewallet);
                System.out.println("bug:si recipient ces donné sont "+recipient);
                //log.info("balance: {}", newBalancewallet);

                clientDAO.save(new Client(recipient.getId(),recipient.getUser(),recipient.getWallet()));

                transfer.setRecipientClient(new Client(ctDto.recipient.id));
            } else {
                System.out.println("bug:si recipient de type PROSPECT");

                Prospect recipient = restTemplate.getForObject(
                        "http://microservicewallet/api/prospect/find/" + ctDto.recipient.id, Prospect.class);
                System.out.println("bug:voici recipient retourner par ID :"+recipient.getId());
                System.out.println("bug:voici recipient retourner par ID :"+recipient.getLastName()+recipient.getFirstName());

                if (recipient == null) {
                    throw new RuntimeException("Recipient client not found");
                }
                prospectDAO.save(recipient);

                transfer.setRecipientProspect(recipient);
            }

            if (ctDto.transferType.equals(TransferType.DEBIT)) {
                Long fee;
                if (ctDto.feesType.equals(FeesType.ORDERINGCLIENT)) {
                    fee = TRANSFERFEE;
                } else if (ctDto.feesType.equals(FeesType.BENEFITINGCLIENT)) {
                    fee = 0L;
                } else {
                    fee = TRANSFERFEE / 2;
                }

                restTemplate.put("http://microservicewallet/api/wallet/debit/" + sender.getWallet().getId(),
                        ctDto.amount + fee);
            }
            createEmailTransfer.setEmail(sender.getUser().getEmail());
            createEmailTransfer.setUrlPdf("vous avez fait un transfer de montant de: "+ctDto.amount+" avec refference : "+transfer.getTransferId()+ "  n'essayez pas de partager ce code que avec le binificière");
            //microservicenotification localhost:8070
            restTemplate.postForObject(
                    "http://microservicenotification/api/notification/TransferInfoPdf-notification",
                    createEmailTransfer, CreateEmailTransfer.class
            );

            return this.transferDAO.save(transfer);


        } else {
            System.out.println("sender pas de type client : ");
            Prospect sender = restTemplate.getForObject(
                    "http://microservicewallet/api/prospect/find/" + ctDto.sender.id, Prospect.class);

            if (sender == null) {
                throw new RuntimeException("Sender not found");
            }

            prospectDAO.save(sender);
            transfer.setAmount(ctDto.amount);
            transfer.setTransfer_date(new Date());
            transfer.setStatus(TransferStatus.PENDING);
            transfer.setSenderProspect(sender);

            if (ctDto.recipient.type.equals(CreateTransferDto.TransferClientType.CLIENT)) {
                Client recipient = restTemplate.getForObject(
                        "http://microservicewallet/api/client/find/" + ctDto.recipient.id, Client.class);

                if (recipient == null) {
                    throw new RuntimeException("Recipient client not found");
                }
                clientDAO.save(new Client(recipient.getId()));

                transfer.setRecipientClient(new Client(ctDto.recipient.id));
            } else {
                Prospect recipient = restTemplate.getForObject(
                        "http://microservicewallet/api/prospect/find/" + ctDto.recipient.id, Prospect.class);

                if (recipient == null) {
                    throw new RuntimeException("Recipient client not found");
                }
                prospectDAO.save(recipient);

                transfer.setRecipientProspect(recipient);
            }

            return transferDAO.save(transfer);
        }

    }

    private void checkValidity(CreateTransferDto ctDto, Client sender) {
        if (sender == null) {
            System.out.println("bug: je suis dans checkValidity condition sender == null ");
            throw new RuntimeException("Sender client not found");
        }

        if (sender.getWallet().getTransferCeiling() < ctDto.amount) {
            System.out.println("bug: je suis dans checkValidity condition sender.getWallet().getTransferCeiling() < ctDto.amount ");

            throw new RuntimeException("Transfer cap surpassed");
        }

        if (ctDto.transferType.equals(TransferType.DEBIT) && sender.getWallet().getBalance() < ctDto.amount) {
            System.out.println("bug: je suis dans checkValidity condition ctDto.transferType.equals(TransferType.DEBIT)"+ctDto.transferType.equals(TransferType.DEBIT));
            System.out.println("bug: je suis dans checkValidity condition sender.getWallet().getBalance()"+sender.getWallet().getBalance());
            System.out.println("bug: je suis dans checkValidity condition ctDto.amount"+ctDto.amount);


            throw new RuntimeException("Net enough balance");
        }

        // get today's date at midnight
        System.out.println("bug: je suis dans checkValidity apres condition ");

        Calendar today = new GregorianCalendar();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        System.out.println("bug: je suis dans checkValidity tody is :"+today);

        Client client = new Client(sender.getId(),sender.getUser(),sender.getWallet());
        System.out.println("bug: je suis dans checkValidity apres client :"+client);
        Double dailyTransfersSum = this.transferDAO.getTranferSumBySenderAndStartingDate(client, today.getTime());
        if (dailyTransfersSum == null) {
            dailyTransfersSum = 0d;
        }

        if (dailyTransfersSum + ctDto.amount > sender.getWallet().getDailyCeiling()) {
            throw new RuntimeException("Daily cap surpassed");
        }
    }

    public Transfer update(UpdateStatusDTO updateStatusDTO) {
        Optional<Transfer> transfer = transferDAO.findById(updateStatusDTO.getId());
        System.out.println("bug: transfer to update :"+transfer.get());
        if (transfer.isEmpty()) {
            throw new RuntimeException("Transfer not found");
        } else if (
                //transfer.get().getStatus().equals(updateStatusDTO.getTransferStatus()) ||
                transfer.get().getStatus().equals(TransferStatus.COMPLETED) ||
                transfer.get().getStatus().equals(TransferStatus.FAILED) ||
                transfer.get().getStatus().equals(TransferStatus.EXPIRED) ||
                transfer.get().getStatus().equals(TransferStatus.CANCELED)
        ) {
          throw new RuntimeException("Transfer closed and can't be modified");
        } else if (
            updateStatusDTO.getTransferStatus().equals(TransferStatus.CANCELED) ||
            updateStatusDTO.getTransferStatus().equals(TransferStatus.FAILED) ||
            updateStatusDTO.getTransferStatus().equals(TransferStatus.EXPIRED)
        ) {
            if (transfer.get().getSenderClient() != null){
                System.out.println("bug: dans ligne 278");

                Client sender = restTemplate.getForObject(
                        "http://microservicewallet/api/client/find/" + transfer.get().getSenderClient().getClientId(), Client.class);

                if (sender == null) {
                    throw new RuntimeException("Sender not found");
                }

                restTemplate.put("http://microservicewallet/api/wallet/credit/" + sender.getWallet().getId(),
                        transfer.get().getAmount());
            }

        } else if(updateStatusDTO.getTransferStatus() == TransferStatus.COMPLETED) {
            System.out.println("bug: dans ligne 292");
            if(transfer.get().getRecipientClient() != null) {
                Client recipient = restTemplate.getForObject(
                        "http://microservicewallet/api/client/find/" + transfer.get().getRecipientClient().getClientId(), Client.class);

                if (recipient == null) {
                    throw new RuntimeException("Recipient not found");
                }

                restTemplate.put("http://microservicewallet/api/wallet/credit/" + recipient.getWallet().getId(),
                        transfer.get().getAmount());

            }
        }

        transfer.get().setStatus(updateStatusDTO.getTransferStatus());
        return transferDAO.save(transfer.get());
    }

    //pour transfer multiple :
    public TransferMultiple createTransferMultiple(CreateTransferMultiple ctDto , long supervisorId){
        TransferMultiple savedTransfe = null;
        System.out.println("je suis dans service transfer dans la methode createTransferMultiple");
        TransferMultiple transferMultiple= new TransferMultiple();
        String generatedMultipleTransferId = generateRandomId();
        transferMultiple.setTransferId(generatedMultipleTransferId);
        Supervisor supervisor= this.supervisorDAO.findById(supervisorId).orElse(null);
        transferMultiple.setSupervisor(supervisor);
        if (ctDto.sender.type.equals(CreateTransferMultiple.TransferClientType.CLIENT)){
            Client sender = restTemplate.getForObject(
                    "http://microservicewallet/api/client/find/"+ctDto.sender.id, Client.class);
            System.out.println("d'apres  http://microservicewallet/api/client/find/+ctDto.sender.id = "+sender.getClientId());
            System.out.println("voici mon client recupérer"+ sender);
            System.out.println("voici mon client wallet recupérer"+ sender.getWallet());
            System.out.println("voici mon client user info recupérer"+ sender.getUser());
            System.out.println("bug: je suis avant checkValidity(ctDto, sender)");
            checkValidityTransferMultiple(ctDto, sender);
            System.out.println("bug: je suis apres checkValidity(ctDto, sender)");
            clientDAO.save(sender);
            transferMultiple.setSenderClient(sender);
            transferMultiple.setAmount(ctDto.amount);
            transferMultiple.setTransfer_date(new Date());
            transferMultiple.setStatus(TransferStatus.PENDING);
            transferMultiple.setSenderClient(sender);
            System.out.println("bug: maintenant pour recipient");
            List<Client> receptionDeTransferMultiple = new ArrayList<>();
            for(String idRecipient: ctDto.recipientId){
                System.out.println("bug:si recipients dans transfer multiple de type client");
                Client recipient = restTemplate
                        .getForObject(
                                "http://microservicewallet/api/client/find/" + idRecipient, Client.class);
                System.out.println("bug:boucle for : recipient numéro : "+recipient);

                if (recipient == null) {
                    throw new RuntimeException("Recipient client not found");
                }
                final String URLbalance = "http://microservicewallet/api/wallet/update/"+recipient.getWallet().getId()+"/"+ctDto.getAmount();
                System.out.println("bug:voici recipient avant changer le solde "+recipient);
                //create header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                System.out.println("bug:URLbalance "+URLbalance);
                ResponseEntity<Wallet> newBalancewallet = restTemplate.exchange(
                        URLbalance,
                        HttpMethod.PUT,
                        null,
                        Wallet.class
                );
                System.out.println("bug:URLbalance "+URLbalance);
                if (newBalancewallet.getStatusCode().is2xxSuccessful()) {
                    // Successfully updated
                    Wallet updatedWallet = newBalancewallet.getBody();
                    System.out.println("Updated wallet: " + updatedWallet);
                    recipient.setWallet(updatedWallet);
                } else {
                    // Handle errors
                    System.err.println("Error updating wallet. Status code: " + newBalancewallet.getStatusCodeValue());
                }

                System.out.println("bug:new balance de recipient est :"+newBalancewallet);
                System.out.println("bug:si recipient ces donné sont "+recipient);
                //log.info("balance: {}", newBalancewallet);

                clientDAO.save(new Client(recipient.getId(),recipient.getUser(),recipient.getWallet()));
                 receptionDeTransferMultiple.add(recipient);
                //transferMultiple.setRecipientClients();
                //transfer.setRecipientClient(new Client(ctDto.recipient.id));

            }
            System.out.println("bug:tester typpe de trnsfer");
            //une fois compte les binificieres remple la liste
            transferMultiple.setRecipientClients(receptionDeTransferMultiple);
            System.out.println("bug:voici recipient apres l'ajout de la liste de recipients "+transferMultiple);
            if (ctDto.transferType.equals(TransferType.DEBIT)) {
                Long fee;
                if (ctDto.feesType.equals(FeesType.ORDERINGCLIENT)) {
                    fee = TRANSFERFEE;
                } else if (ctDto.feesType.equals(FeesType.BENEFITINGCLIENT)) {
                    fee = 0L;
                } else {
                    fee = TRANSFERFEE / 2;
                }
                int nbrOfRecipient=ctDto.recipientId.size();
                System.out.println("bug:voici nbr de recipient"+nbrOfRecipient);
                restTemplate.put("http://microservicewallet/api/wallet/debit/" + sender.getWallet().getId(),
                        (ctDto.amount + fee)*nbrOfRecipient);
                System.out.println("avant savedTransfe");
                savedTransfe =transferMultipleDAO.save(transferMultiple);
                System.out.println("apres savedTransfe"+savedTransfe);
            }
        }
        return savedTransfe;


    }

    //pour transfer multiplr :
    private void checkValidityTransferMultiple(CreateTransferMultiple ctDto, Client sender) {
        if (sender == null) {
            System.out.println("bug: je suis dans checkValidity condition sender == null ");
            throw new RuntimeException("Sender client not found");
        }

        if (sender.getWallet().getTransferCeiling() < ctDto.amount) {
            System.out.println("bug: je suis dans checkValidity condition sender.getWallet().getTransferCeiling() < ctDto.amount ");

            throw new RuntimeException("Transfer cap surpassed");
        }

        if (ctDto.transferType.equals(TransferType.DEBIT) && sender.getWallet().getBalance() < ctDto.amount) {
            System.out.println("bug: je suis dans checkValidity condition ctDto.transferType.equals(TransferType.DEBIT)"+ctDto.transferType.equals(TransferType.DEBIT));
            System.out.println("bug: je suis dans checkValidity condition sender.getWallet().getBalance()"+sender.getWallet().getBalance());
            System.out.println("bug: je suis dans checkValidity condition ctDto.amount"+ctDto.amount);


            throw new RuntimeException("Net enough balance");
        }

        // get today's date at midnight
        System.out.println("bug: je suis dans checkValidity apres condition ");

        Calendar today = new GregorianCalendar();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        System.out.println("bug: je suis dans checkValidity tody is :"+today);

        Client client = new Client(sender.getId(),sender.getUser(),sender.getWallet());
        System.out.println("bug: je suis dans checkValidity apres client :"+client);
        Double dailyTransfersSum = this.transferDAO.getTranferSumBySenderAndStartingDate(client, today.getTime());
        if (dailyTransfersSum == null) {
            dailyTransfersSum = 0d;
        }

        if (dailyTransfersSum + ctDto.amount > sender.getWallet().getDailyCeiling()) {
            throw new RuntimeException("Daily cap surpassed");
        }
    }
    public PdfPTable export(HttpServletResponse response,String idTransfer) throws IOException {
        //recupérer un transfer
        Transfer transfer= this.transferDAO.findById(idTransfer).orElse(null);
        System.out.println("bug: transfer by id est  :"+transfer.getAmount());

        PdfPTable table = null;
        PdfPTable tablerecipient= null;

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Image logo = Image.getInstance("https://maroc-diplomatique.net/wp-content/uploads/2020/02/Bank-Of-Africa.png");
        logo.scaleAbsolute(250, 120); // Ajustez la taille de l'image selon vos besoins
        logo.setAlignment(Image.LEFT); // Ajustez l'alignement de l'image
        document.add(logo);

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(22);


        Paragraph paragraph = new Paragraph("Reçu de transfert",fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(12);

        Paragraph paragraph2 = new Paragraph("Below  is some Transfer Informations ",fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph2);

        Font fontParagraph3 = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(12);

        Paragraph paragraph3 = new Paragraph("  ",fontParagraph);
        paragraph3.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph3);

        table = new PdfPTable(2);
        tablerecipient = new PdfPTable(2);

        if (transfer != null) {
            table.addCell("Transfer Reference: ");
            table.addCell(""+ transfer.getTransferId());
            table.addCell("Amount: ");
            table.addCell(""+ transfer.getAmount());

            table.addCell("Sender: ");
            table.addCell("" + transfer.getSenderClient().getUser().getEmail());

            table.addCell("Code Sender Wallet: ");
            table.addCell("" + transfer.getSenderClient().getWallet().getNumber());

            table.addCell("identifiant CIN sender: ");
            table.addCell(""+ transfer.getSenderClient().getId());

            table.addCell("Recipient: ");
            table.addCell("" + transfer.getRecipientClient().getUser().getEmail());

            table.addCell("Code Recipient Wallet : ");
            table.addCell("" + transfer.getRecipientClient().getWallet().getNumber());

            table.addCell("identifiant CIN recipient: ");
            table.addCell(""+ transfer.getRecipientClient().getId());

        }
        //add table to pdf
        document.add(table);
        document.add(paragraph3);
        document.add(tablerecipient);
        document.close();
        return table;
    }
    public PdfPTable exportForTransferMultiple(HttpServletResponse response,String idTransfer) throws IOException {
        //recupérer un transfer
        TransferMultiple transferMultiple= this.transferMultipleDAO.findById(idTransfer).orElse(null);
        System.out.println("bug: transfer by id est  :"+transferMultiple.getAmount());

        PdfPTable table = null;
        PdfPTable tablerecipient= null;

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        Image logo = Image.getInstance("https://maroc-diplomatique.net/wp-content/uploads/2020/02/Bank-Of-Africa.png");
        logo.scaleAbsolute(250, 120); // Ajustez la taille de l'image selon vos besoins
        logo.setAlignment(Image.LEFT); // Ajustez l'alignement de l'image
        document.add(logo);

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(22);


        Paragraph paragraph = new Paragraph("Reçu  transfert Multiple",fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(12);

        Paragraph paragraph2 = new Paragraph("Below  is some Transfer Informations ",fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph2);

        Font fontParagraph3 = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(12);

        Paragraph paragraph3 = new Paragraph("  ",fontParagraph);
        paragraph3.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph3);

        table = new PdfPTable(2);
        tablerecipient = new PdfPTable(2);

        if (transferMultiple != null) {
            table.addCell("Transfer Reference: ");
            table.addCell(""+ transferMultiple.getTransferId());
            table.addCell("Amount: ");
            table.addCell(""+ transferMultiple.getAmount());

            table.addCell("Sender: ");
            table.addCell("" + transferMultiple.getSenderClient().getUser().getEmail());

            table.addCell("Code Sender Wallet: ");
            table.addCell("" + transferMultiple.getSenderClient().getWallet().getNumber());

            table.addCell("identifiant CIN sender: ");
            table.addCell(""+ transferMultiple.getSenderClient().getId());
            List<Client> receptionDeTransferMultiple = new ArrayList<>();
            int recipientNumber = 1;
            for(Client recipient: transferMultiple.getRecipientClients()) {
                int totale = transferMultiple.getRecipientClients().size();
                System.out.println("bug: dpf de transfer multiple recipient est :"+recipient);
                table.addCell("Recipient " + recipientNumber + " : ");
                table.addCell("" + recipient.getUser().getEmail());
                System.out.println("bug: dpf de transfer multiple recipient email est :"+recipient.getUser().getEmail());

                table.addCell("Code Recipient Wallet : ");
                table.addCell("" +  recipient.getWallet().getNumber());

                table.addCell("identifiant CIN recipient: ");
                table.addCell("" +  recipient.getId());
                recipientNumber++;
            }
        }
        //add table to pdf
        document.add(table);
        document.add(paragraph3);
        document.add(tablerecipient);
        document.close();
        return table;
    }
    public final  String generateRandomId() {
        // Générer un nombre aléatoire de 10 chiffres
        long randomPart = new Random().nextLong() % 10000000000L;

        // Formater le nombre aléatoire pour avoir exactement 10 chiffres
        String formattedRandomPart = String.format("%010d", Math.abs(randomPart));

        // Concaténer "837" avec le nombre aléatoire
        String randomId = "837" + formattedRandomPart;

        return randomId;
    }
}
/*
CreateEmailTransfer createEmailTransfer = new CreateEmailTransfer();
        createEmailTransfer.setEmail(sender.getUser().getEmail());
        createEmailTransfer.setUrlPdf("vous avez fait un transfer avec refference : "+transfer.getTransferId());
        //microservicenotification localhost:8070
        restTemplate.postForObject(
                "http://localhost:8070/api/notification/TransferInfoPdf-notification",
                createEmailTransfer, CreateEmailTransfer.class
        );
 */