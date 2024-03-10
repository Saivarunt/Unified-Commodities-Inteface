package com.example.unifiedcommoditiesinterface.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.unifiedcommoditiesinterface.models.TransporterProposal;
import com.example.unifiedcommoditiesinterface.models.dto.ListTransportationalRequest;
import com.example.unifiedcommoditiesinterface.models.dto.TransporterProposalListing;
import com.example.unifiedcommoditiesinterface.services.TransportationalRequestsService;
import com.example.unifiedcommoditiesinterface.services.TransporterProposalService;
import com.example.unifiedcommoditiesinterface.services.UserService;
import com.example.unifiedcommoditiesinterface.services.implementation.AuthenticationService;
import com.example.unifiedcommoditiesinterface.services.implementation.TokenService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("transport")
@CrossOrigin(origins = "http://localhost:4200/")
public class TransportationController {

    @Autowired
    TransportationalRequestsService transportationalRequestsService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    TransporterProposalService transporterProposalService;

    @Autowired
    AuthenticationService authenticationService;

    // getall requests 
    @PreAuthorize("@permissionsService.hasAccess('VIEW_TRANSPORT_REQUESTS')")
    @GetMapping("/requests")
    public ResponseEntity<Page<ListTransportationalRequest>> viewTransportRequests(@RequestParam Integer page) {
        return new ResponseEntity<Page<ListTransportationalRequest>>(transportationalRequestsService.fetchTransportationalRequests(page), HttpStatus.OK);
    }
    
    @PreAuthorize("@permissionsService.hasAccess('VIEW_TRANSPORT_REQUESTS')")
    @GetMapping("/requests-by-id")
    public ResponseEntity<ListTransportationalRequest> viewTransportRequestsById(@RequestParam String _id) {
        return new ResponseEntity<ListTransportationalRequest>(transportationalRequestsService.fetchTransportationalRequestsById(_id), HttpStatus.OK);
    }
    
    // get made requests
    @PreAuthorize("@permissionsService.hasAccess('MAKE_TRANSPORTATION_REQUEST')")
    @GetMapping("/requests-made")
    public ResponseEntity<Page<ListTransportationalRequest>> viewMadeTransportRequests(@RequestParam Integer page, HttpServletRequest request) {
        return new ResponseEntity<Page<ListTransportationalRequest>>(transportationalRequestsService.fetchRequestsMade(page, authenticationService.fetchUserFromToken(request)), HttpStatus.OK);
    }
    
    // proposal
    @PreAuthorize("@permissionsService.hasAccess('MAKE_TRANSPORTATION_PROPOSAL')")
    @PostMapping("/make-proposal")
    public ResponseEntity<TransporterProposalListing> makeProposal(@RequestParam String _id, HttpServletRequest request) {
        return new ResponseEntity<>(transporterProposalService.proposalListing(_id, authenticationService.fetchUserFromToken(request)), HttpStatus.OK);
    }

    // get proposal by id
    @PreAuthorize("@permissionsService.hasAccess('MAKE_TRANSPORTATION_PROPOSAL') or @permissionsService.hasAccess('MAKE_TRANSPORTATION_REQUEST') ")
    @GetMapping("/proposal/{_id}")
    public ResponseEntity<TransporterProposalListing> viewProposal(@PathVariable String _id) {
        return new ResponseEntity<>(transporterProposalService.fetchProposal(_id), HttpStatus.OK);
    }
    
    // get proposals made
    @PreAuthorize("@permissionsService.hasAccess('MAKE_TRANSPORTATION_PROPOSAL')")
    @GetMapping("/proposals-made")
    public ResponseEntity<Page<TransporterProposalListing>> viewProposalsMade(@RequestParam Integer page, HttpServletRequest request) {
        return new ResponseEntity<Page<TransporterProposalListing>>(transporterProposalService.fetchMadeProposals(authenticationService.fetchUserFromToken(request), page), HttpStatus.OK);
    }
    
    // get proposal by request id
    @PreAuthorize("@permissionsService.hasAccess('MAKE_TRANSPORTATION_REQUEST')")
    @GetMapping("/proposal-request/{_id}")
    public ResponseEntity<Page<TransporterProposalListing>> viewProposalByRequest(@PathVariable String _id, @RequestParam Integer page) {
        return new ResponseEntity<>(transporterProposalService.fetchProposalsByRequest(_id, page), HttpStatus.OK);
    }
    
    
    // approve reject
    @PreAuthorize("@permissionsService.hasAccess('ACCEPT_OR_REJECT_TRANSPORT_REQUEST')")
    @PostMapping("/approve-proposal")
    public ResponseEntity<Boolean> approveProposal(@RequestParam String _id) {
        return new ResponseEntity<Boolean>(transporterProposalService.approveTransporterProposal(_id), HttpStatus.OK);
    }

    @PreAuthorize("@permissionsService.hasAccess('ACCEPT_OR_REJECT_TRANSPORT_REQUEST')")
    @PostMapping("/reject-proposal")
    public ResponseEntity<Boolean> rejectProposal(@RequestParam String _id) {
        return new ResponseEntity<Boolean>(transporterProposalService.rejectTransporterProposal(_id), HttpStatus.OK);
    }

    @GetMapping("/proposals")
    public ResponseEntity<Page<TransporterProposal>> fetchAllProposals(@RequestParam Integer page) {
        return new ResponseEntity<Page<TransporterProposal>>(transporterProposalService.findAllProposals(page), HttpStatus.OK);
    }
    
}
