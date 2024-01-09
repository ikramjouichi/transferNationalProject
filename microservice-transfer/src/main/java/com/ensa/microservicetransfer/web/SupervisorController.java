package com.ensa.microservicetransfer.web;

import com.ensa.microservicetransfer.entities.Supervisor;
import com.ensa.microservicetransfer.enums.SupervisorRole;
import com.ensa.microservicetransfer.services.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/supervisors")
@RestController
public class SupervisorController {
    private SupervisorService supervisorService;

    @Autowired
    public void setSupervisorService(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Supervisor loginRequest){
       String result = supervisorService.login(loginRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public Supervisor create(@RequestBody Supervisor supervisor) {
        return this.supervisorService.create(supervisor);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Supervisor> findSupervisorById(@PathVariable long id) {
        Supervisor supervisor =  this.supervisorService.getSupervisorById(id);
        return new ResponseEntity<>(supervisor,HttpStatus.OK);
    }
    @GetMapping("/findRole/{id}")
    public ResponseEntity<SupervisorRole> findRoleSupervisorById(@PathVariable long id) {
        SupervisorRole supervisorrole =  this.supervisorService.getRoleSupervisorByid(id);
        return new ResponseEntity<>(supervisorrole,HttpStatus.OK);
    }
}
