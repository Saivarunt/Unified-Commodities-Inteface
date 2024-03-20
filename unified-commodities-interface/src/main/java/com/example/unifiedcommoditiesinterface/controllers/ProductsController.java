package com.example.unifiedcommoditiesinterface.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.unifiedcommoditiesinterface.models.dto.LifecycleView;
import com.example.unifiedcommoditiesinterface.models.dto.ListingProduct;
import com.example.unifiedcommoditiesinterface.models.dto.ProductListing;
import com.example.unifiedcommoditiesinterface.services.ProductsService;
import com.example.unifiedcommoditiesinterface.services.UserService;
import com.example.unifiedcommoditiesinterface.services.implementation.AuthenticationService;
import com.example.unifiedcommoditiesinterface.services.implementation.TokenService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("products")
@CrossOrigin("*")
public class ProductsController {
    
    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    ProductsService productsService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<Page<ProductListing>> viewProducts(@RequestParam Integer page) {
        return new ResponseEntity<Page<ProductListing>>(productsService.viewAllProducts(page), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('SUPPLIER') or hasRole('ADMIN')")
    @GetMapping("/owned")
    public ResponseEntity<Page<ProductListing>> viewProductsOwned(@RequestParam Integer page, HttpServletRequest request) {
        return new ResponseEntity<Page<ProductListing>>(productsService.getAllListingByUser(authenticationService.fetchUserFromToken(request), page), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPPLIER') or hasRole('ADMIN')")
    @GetMapping("/owned-latest")
    public ResponseEntity<Page<ProductListing>> viewProductsOwnedLatest(@RequestParam Integer page, HttpServletRequest request) {
        return new ResponseEntity<Page<ProductListing>>(productsService.getAllListingByUserSortedByLatest(authenticationService.fetchUserFromToken(request), page), HttpStatus.OK);
    }
    
    @PreAuthorize("@permissionsService.hasAccess('EDIT_PRODUCTS')")
    @PostMapping("/product-listing")
    public ResponseEntity<ProductListing> listProduct(@ModelAttribute ListingProduct body, HttpServletRequest request) throws IOException {
        return new ResponseEntity<ProductListing>(productsService.newListing(body, authenticationService.fetchUserFromToken(request)), HttpStatus.OK);
    }

    @PreAuthorize("@permissionsService.hasAccess('EDIT_PRODUCTS')")
    @PutMapping("/update-product-listing")
    public ResponseEntity<ProductListing> updateListedProduct(@ModelAttribute ListingProduct body, HttpServletRequest request) throws IOException {
        return new ResponseEntity<ProductListing>(productsService.updateListing(body, authenticationService.fetchUserFromToken(request)), HttpStatus.OK);
    }

    // search api
    @GetMapping("/search")
    public ResponseEntity<Page<ProductListing>> searchProductByName(@RequestParam String productname, @RequestParam Integer page) {
        return new ResponseEntity<Page<ProductListing>>(productsService.viewSearchProduct(productname, page), HttpStatus.OK);
    }
    
    @GetMapping("/search-owned")
    public ResponseEntity<Page<ProductListing>> searchOwnedProductByName(@RequestParam String productname, @RequestParam Integer page, HttpServletRequest request) {
        return new ResponseEntity<Page<ProductListing>>(productsService.searchOwnedProduct(productname, page, authenticationService.fetchUserFromToken(request)), HttpStatus.OK);
    }

    // delete
    @PreAuthorize("@permissionsService.hasAccess('DELETE_PRODUCTS')")
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteProductListing(@RequestParam(name = "product_id") String productListingId) {
        return new ResponseEntity<Boolean>(productsService.deleteListing(productListingId), HttpStatus.OK);
    }

    @GetMapping("/lifecycle")
    public ResponseEntity<Page<LifecycleView>> viewProdutLifecycle(@RequestParam Integer page, HttpServletRequest request) {
        return new ResponseEntity<Page<LifecycleView>>(productsService.fetchLifecycle(authenticationService.fetchUserFromToken(request), page), HttpStatus.OK);
    }

    @PostMapping("/supplier-status")
    public ResponseEntity<Boolean> initiateSupplierStatusUpdate(@RequestParam String _id) {
        return new ResponseEntity<Boolean>(productsService.updateSupplierStatus(_id), HttpStatus.OK);
    }
    
    @PostMapping("/consumer-status")
    public ResponseEntity<Boolean> initiateConsumerStatusUpdate(@RequestParam String _id) {
        return new ResponseEntity<Boolean>(productsService.updateConsumerStatus(_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPPLIER') or hasRole('CONSUMER')")
    @PostMapping("/rate-transporter")
    public ResponseEntity<Boolean> rateTransporter(@RequestParam Integer rating, @RequestParam String _id,  HttpServletRequest request) {
        return new ResponseEntity<>(productsService.rateTransporter(rating, _id, authenticationService.fetchUserFromToken(request)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/rate-product")
    public ResponseEntity<Boolean> rateProduct(@RequestParam Integer rating, @RequestParam String _id,  HttpServletRequest request) {
        return new ResponseEntity<>(productsService.rateProduct(rating, _id, authenticationService.fetchUserFromToken(request)), HttpStatus.OK);
    }
}
