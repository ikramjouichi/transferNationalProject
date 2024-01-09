package com.ensa.microservicetransfer.web;

import com.ensa.microservicetransfer.dto.CreateTransferDto;
import com.ensa.microservicetransfer.dto.CreateTransferMultiple;
import com.ensa.microservicetransfer.dto.UpdateStatusDTO;
import com.ensa.microservicetransfer.entities.Transfer;
import com.ensa.microservicetransfer.entities.TransferMultiple;
import com.ensa.microservicetransfer.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/transfer")
@RestController
public class TransferController {
    private TransferService transferService;

    @Autowired
    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

//    @PostMapping("/create")
//    public Transfer create(@RequestBody Transfer transfer) {
//        return this.transferService.create(transfer);
//    }
    @GetMapping("/")
    public String sayHello(){
        System.out.println("je suis dans transfe-service sayHello");
        String varr=this.transferService.testautherServiceResponse();
        System.out.println("je suis dans transfe-service sayHello reponse est:"+varr);
       return "hello from controller transfer";
    }
    @PostMapping("/create/{idSupervisor}")
    public ResponseEntity<Transfer> create(@RequestBody CreateTransferDto ctDto,@PathVariable long idSupervisor) {
        try {
            System.out.println("je suis dans transfe-service to create a transfer");
            System.out.println("create a transfer avec typeTransfer : "+ctDto.transferType);
            System.out.println("create a transfer from sender : "+ctDto.sender.id);
            System.out.println("create a transfer from sender : "+ctDto.sender.type);
            System.out.println("create a transfer from sender : "+ctDto.recipient);
            Transfer transfer = this.transferService.create(ctDto,idSupervisor);
            System.out.println("je suis apres this.transferService.create(ctDto)");
            return new ResponseEntity<>(transfer, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @PostMapping("/createTransferMultiple/{idSupervisor}")
    public ResponseEntity<TransferMultiple> createTransferMultiple(@RequestBody CreateTransferMultiple ctDto, @PathVariable long idSupervisor){
        try{
            System.out.println("je suis dans transfe-service to create a transfer");
            System.out.println("create a transfer avec typeTransfer : "+ctDto.transferType);
            System.out.println("create a transfer from sender : "+ctDto.sender.id);
            System.out.println("create a transfer from sender : "+ctDto.sender.type);
            System.out.println("create a transfer from sender : "+ctDto.recipientId);
            TransferMultiple transferMultiple = this.transferService.createTransferMultiple(ctDto,idSupervisor);
            System.out.println("je suis apres this.transferService.create(ctDto)");
            return new ResponseEntity<>(transferMultiple, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/find/{id}")
    public Transfer findById(@PathVariable String id) {
        Optional<Transfer> transfer = this.transferService.findById(id);
        return transfer.orElse(null);
    }
    @GetMapping("/findTransferMultiple/{id}")
    public TransferMultiple findTransferMultipleById(@PathVariable String id) {
        Optional<TransferMultiple> transfer = this.transferService.findTransferMultipleById(id);
        return transfer.orElse(null);
    }

    @PostMapping("/update-status")
    public ResponseEntity<Object> transfer(@RequestBody UpdateStatusDTO updateStatusDTO) {
        try{
            System.out.println("je suis dans transfe-service to update status of transfer");
            this.transferService.update(updateStatusDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
