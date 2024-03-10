package com.example.unifiedcommoditiesinterface.services.implementation;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.unifiedcommoditiesinterface.models.DeletedProducts;
import com.example.unifiedcommoditiesinterface.models.ProductLifecycle;
import com.example.unifiedcommoditiesinterface.models.ProductRating;
import com.example.unifiedcommoditiesinterface.models.Products;
import com.example.unifiedcommoditiesinterface.models.Profile;
import com.example.unifiedcommoditiesinterface.models.TransporterRating;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.LifecycleView;
import com.example.unifiedcommoditiesinterface.models.dto.ListingProduct;
import com.example.unifiedcommoditiesinterface.models.dto.ProductListing;
import com.example.unifiedcommoditiesinterface.repositories.DeletedProductsRepository;
import com.example.unifiedcommoditiesinterface.repositories.ProductLifecycleRepository;
import com.example.unifiedcommoditiesinterface.repositories.ProductRatingRepository;
import com.example.unifiedcommoditiesinterface.repositories.ProductsRepository;
import com.example.unifiedcommoditiesinterface.repositories.ProfileRepository;
import com.example.unifiedcommoditiesinterface.repositories.TransporterRatingRepository;
import com.example.unifiedcommoditiesinterface.services.ProductsService;
import com.example.unifiedcommoditiesinterface.services.TransportationalRequestsService;

@Service
public class ProductsServiceImplementation implements ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    DeletedProductsRepository deletedProductsRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    TransportationalRequestsService transportationalRequestsService;

    @Autowired
    ProductLifecycleRepository productLifecycleRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    TransporterRatingRepository transporterRatingRepository;

    @Autowired
    ProductRatingRepository productRatingRepository;

    @Autowired
    MailSenderService mailSenderService;
    
    public ProductListing newListing(ListingProduct productListing, User user) throws IOException {
        Products product = productsRepository.save(new Products(null, productListing.getProduct_name(), user, productListing.getDescription(), 
        Long.parseLong(productListing.getPrice()), Double.parseDouble(productListing.getQuantity()), imageService.imageUpload(productListing.getProduct_image(), user.getUsername()), 
        productListing.getTransportation_type()));
        
        return new ProductListing(product.get_id(), product.getProduct_name(), product.getOwner().getProfile(), product.getDescription(), 
        product.getPrice(), product.getQuantity(), product.getProduct_image(), product.getTransportation_type());
    }

    public ProductListing updateListing(ListingProduct productListing, User user) throws IOException {
        Products product = productsRepository.findById(productListing.getId()).get();
        product.setProduct_name(productListing.getProduct_name());
        product.setDescription(productListing.getDescription());
        product.setPrice(Long.parseLong(productListing.getPrice()));

        if(!productListing.getProduct_image().isEmpty()){
            product.setProduct_image(imageService.imageUpload(productListing.getProduct_image(), user.getUsername()));
        }
        
        product.setQuantity(Double.parseDouble(productListing.getQuantity()));
        product.setTransportation_type(productListing.getTransportation_type());

        productsRepository.save(product);

        return new ProductListing(product.get_id(), product.getProduct_name(), product.getOwner().getProfile(), product.getDescription(), 
        product.getPrice(), product.getQuantity(), product.getProduct_image(), product.getTransportation_type());
    }
    
    public Page<ProductListing> getAllListingByUser(User user, Integer page) {
        Page<Products> products = productsRepository.findByOwner(user, PageRequest.of(page, 10));

        List<ProductListing> response = products.getContent().stream()
        .map(product -> new ProductListing(
            product.get_id(),
            product.getProduct_name(),
            product.getOwner().getProfile(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getProduct_image(),
            product.getTransportation_type()
            ))
        .collect(Collectors.toList());

        return new PageImpl<>(response, PageRequest.of(page, 10), products.getTotalElements());
    }

    public Page<ProductListing> viewAllProducts(Integer page) {
        Page<Products> products = productsRepository.findAll(PageRequest.of(page, 10));

        List<ProductListing> response = products.getContent().stream()
        .map(product -> new ProductListing(
            product.get_id(),
            product.getProduct_name(),
            product.getOwner().getProfile(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getProduct_image(),
            product.getTransportation_type()
            ))
        .collect(Collectors.toList());

        return new PageImpl<>(response, PageRequest.of(page, 10), products.getTotalElements()); 
    }

    public Page<ProductListing> viewSearchProduct(String productname, Integer page) {
        Page<Products> products = productsRepository.findByProduct_name(productname, PageRequest.of(page, 10));
        List<ProductListing> response = products.getContent().stream()
        .map(product -> new ProductListing(
            product.get_id(),
            product.getProduct_name(),
            product.getOwner().getProfile(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getProduct_image(),
            product.getTransportation_type()
            ))
        .collect(Collectors.toList());

        return new PageImpl<>(response, PageRequest.of(page, 10), products.getTotalElements()); 
    }

    public Boolean deleteListing(String productListingId) {
        if(productsRepository.findById(productListingId).get() != null){
            DeletedProducts deletedProduct = new DeletedProducts(null, productsRepository.findById(productListingId).get());
            deletedProductsRepository.save(deletedProduct);
            productsRepository.deleteById(productListingId);
            return true;
        }
        return false;
    }
    public Page<LifecycleView> lifecycleViewDTOConverter(Page<ProductLifecycle> productLifecycle,  Integer page) {
        List<LifecycleView> lifecycleView = productLifecycle.getContent().stream()
        .map(val -> new LifecycleView(
            val.get_id(),
            val.getProduct().getProduct_name(),
            val.getProduct().getOwner().getProfile().getFull_name(), 
            val.getConsumer().getProfile().getFull_name(),
            val.getTransporter().getProfile().getFull_name(), 
            val.getQuantity(), 
            val.getSupplier_status(), 
            val.getConsumer_status())).collect(Collectors.toList());

        return new PageImpl<>(lifecycleView, PageRequest.of(page, 20), productLifecycle.getTotalElements());
        
    }

    public Page<LifecycleView> fetchLifecycle(User user, Integer page) {

        if(!productLifecycleRepository.findByConsumer(user, PageRequest.of(page,10)).isEmpty() && user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(val -> val.equals("CONSUMER"))) {
            Page<ProductLifecycle> productLifecycle = productLifecycleRepository.findByConsumer(user, PageRequest.of(page,20));
            return lifecycleViewDTOConverter(productLifecycle, page);
        }
        else if(!productLifecycleRepository.findByOwner(user, PageRequest.of(page,10)).isEmpty() && user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(val -> val.equals("SUPPLIER"))) {
            Page<ProductLifecycle> productLifecycle = productLifecycleRepository.findByOwner(user, PageRequest.of(page,20)); 
            return lifecycleViewDTOConverter(productLifecycle, page);
        }
        else if(!productLifecycleRepository.findByTransporter(user, PageRequest.of(page,10)).isEmpty() && user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(val -> val.equals("TRANSPORTER"))) {
            Page<ProductLifecycle> productLifecycle = productLifecycleRepository.findByTransporter(user, PageRequest.of(page,20));
            return lifecycleViewDTOConverter(productLifecycle, page);
        }

        return null;
    }

    public Boolean updateSupplierStatus(String _id) {
        ProductLifecycle productLifecycle = productLifecycleRepository.findById(_id).get();
        productLifecycle.setSupplier_status("DELIVERED_TO_TRANSPORTER");
        productLifecycleRepository.save(productLifecycle);

        mailSenderService.sendEmail(productLifecycle.getConsumer().getProfile().getEmail(), "Product delivered to transporter", 
        "The product " + productLifecycle.getProduct().getProduct_name() + " was deleivered to the transporter "+ 
        productLifecycle.getTransporter().getProfile().getFull_name() +" and is on its way.");

        return true;
    }


    public Boolean updateConsumerStatus(String _id) {
        ProductLifecycle productLifecycle = productLifecycleRepository.findById(_id).get();
        
        Profile transporter = productLifecycle.getTransporter().getProfile();
        transporter.setDelivery_count(transporter.getDelivery_count() + 1);
        profileRepository.save(transporter);
        
        Profile owner = productLifecycle.getOwner().getProfile();
        owner.setDelivery_count(owner.getDelivery_count() + 1);
        profileRepository.save(owner);

        productLifecycle.setConsumer_status("DELIVERY_RECIEVED");
        productLifecycleRepository.save(productLifecycle);

        mailSenderService.sendEmail(productLifecycle.getOwner().getProfile().getEmail(), "Product delivered to consumer", 
        "The product " + productLifecycle.getProduct().getProduct_name() + 
        " was successfully deleivered to consumer " + productLifecycle.getConsumer().getProfile().getFull_name()+ ".");
        return true;
    }

    public Boolean rateTransporter(Integer rating, String lifecycle_id, User user) {
        ProductLifecycle productLifecycle = productLifecycleRepository.findById(lifecycle_id).get();
        TransporterRating transporterRating = new TransporterRating(null, user, productLifecycle.getTransporter(), rating);
        transporterRatingRepository.save(transporterRating);

        List<TransporterRating> ratings = transporterRatingRepository.findByTransporter(productLifecycle.getTransporter());
        Integer sum = ratings.stream().map(val -> val.getRating()).collect(Collectors.toList()).stream().reduce(0, Integer::sum);

        if(productLifecycle.getTransporter().getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(val -> val.equals("SUPPLIER"))){
            Profile transporterProfile = productLifecycle.getTransporter().getProfile();
            transporterProfile.setRating((transporterProfile.getRating() + (sum / ratings.size())) / 2 );
            profileRepository.save(transporterProfile);            
        }
        else {
            Profile transporterProfile = productLifecycle.getTransporter().getProfile();
            transporterProfile.setRating(sum / ratings.size());
            profileRepository.save(transporterProfile);
        }

        return true;
    }
    
    public Boolean rateProduct(Integer rating, String lifecycle_id, User user) {
        ProductLifecycle productLifecycle = productLifecycleRepository.findById(lifecycle_id).get();
        ProductRating productRating = new ProductRating(null, productLifecycle.getProduct(), user, rating);
        productRatingRepository.save(productRating);

        List<ProductRating> ratings = productRatingRepository.findByProduct(productLifecycle.getProduct());
        Integer sum = ratings.stream().map(val -> val.getRating()).collect(Collectors.toList()).stream().reduce(0, Integer::sum);

        Profile ownerProfile = productLifecycle.getOwner().getProfile();
        ownerProfile.setRating(sum / ratings.size());
        profileRepository.save(ownerProfile);
        return true;
    }

}
