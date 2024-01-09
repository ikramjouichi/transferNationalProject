package com.ensa.microservicetransfer.services;

import com.ensa.microservicetransfer.dao.SupervisorDAO;
import com.ensa.microservicetransfer.entities.Supervisor;
import com.ensa.microservicetransfer.enums.SupervisorRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupervisorService {
    //@Autowired
    private SupervisorDAO supervisorDAO;

    @Autowired
    public void setSupervisorDAO(SupervisorDAO supervisorDAO) {
        this.supervisorDAO = supervisorDAO;
    }

    public Supervisor create(Supervisor supervisor) {
        return this.supervisorDAO.save(supervisor);
    }
    public Supervisor getSupervisorById(Long id) {
        return this.supervisorDAO.findById(id).orElse(null);
    }
    public SupervisorRole getRoleSupervisorByid(Long id){
        Supervisor supervisor= this.supervisorDAO.findById(id).orElse(null);
        SupervisorRole supervisorRole=supervisor.getRole();
      return supervisorRole;
    }
    public String login(Supervisor loginRequest) {
        Long supervisorId = loginRequest.getSupervisorId();
        Supervisor supervisor = supervisorDAO.findById(supervisorId)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));

        if ("ADMIN".equals(loginRequest.getRole())) {
            // Perform additional checks for ADMIN role if needed
            return supervisorId.toString();
        } else {
            return supervisorId.toString();
        }
    }
}
