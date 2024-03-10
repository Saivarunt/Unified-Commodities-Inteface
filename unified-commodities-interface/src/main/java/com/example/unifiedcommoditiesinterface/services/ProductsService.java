package com.example.unifiedcommoditiesinterface.services;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.LifecycleView;
import com.example.unifiedcommoditiesinterface.models.dto.ListingProduct;
import com.example.unifiedcommoditiesinterface.models.dto.ProductListing;

@Service
public interface ProductsService {
    public ProductListing newListing(ListingProduct productListing, User user) throws IOException;
    public Page<ProductListing> getAllListingByUser(User user, Integer page);
    public Page<ProductListing> viewSearchProduct(String productname, Integer page);
    public Boolean deleteListing(String productListingId);
    public Page<ProductListing> viewAllProducts(Integer page);
    public ProductListing updateListing(ListingProduct productListing, User user) throws IOException;
    public Page<LifecycleView> fetchLifecycle(User user, Integer page);
    public Boolean updateSupplierStatus(String _id);
    public Boolean updateConsumerStatus(String _id);
    public Boolean rateTransporter(Integer rating, String lifecycle_id, User user);
    public Boolean rateProduct(Integer rating, String lifecycle_id, User user);
}
