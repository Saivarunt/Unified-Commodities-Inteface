package com.example.unifiedcommoditiesinterface.services.implementation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import com.example.unifiedcommoditiesinterface.dao.PurchaseInfoDao;
import com.example.unifiedcommoditiesinterface.models.ProductLifecycle;
import com.example.unifiedcommoditiesinterface.models.Products;
import com.example.unifiedcommoditiesinterface.models.PurchaseInfo;
import com.example.unifiedcommoditiesinterface.models.User;
import com.example.unifiedcommoditiesinterface.models.dto.PurchaseRequest;
import com.example.unifiedcommoditiesinterface.models.dto.PurchaseResponse;
import com.example.unifiedcommoditiesinterface.repositories.ProductLifecycleRepository;
import com.example.unifiedcommoditiesinterface.repositories.ProductsRepository;
import com.example.unifiedcommoditiesinterface.repositories.PurchaseInfoRepository;
import com.example.unifiedcommoditiesinterface.services.PurchaseInfoService;
import com.example.unifiedcommoditiesinterface.services.TransportationalRequestsService;

@Service
public class PurchaseInfoServiceImplementation implements PurchaseInfoService{
    
    @Autowired
    PurchaseInfoRepository purchaseInfoRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    TransportationalRequestsService transportationalRequestsService;

    @Autowired
    ProductLifecycleRepository productLifecycleRepository;

    @Autowired
    PurchaseInfoDao purchaseInfoDao;

    public PurchaseResponse purchaseProduct(PurchaseRequest purchaseRequest, User user) {
        Products product = productsRepository.findById(purchaseRequest.get_id()).get();
        if(product.getQuantity() >= purchaseRequest.getQuantity() && purchaseRequest.getQuantity() > 0){
            product.setQuantity(product.getQuantity() - purchaseRequest.getQuantity());
            Products updateProduct = productsRepository.save(product);

            Long purchasePrice = (long) (purchaseRequest.getQuantity() * (updateProduct.getPrice() / updateProduct.getQuantity()));

            PurchaseInfo purchaseInfo = new PurchaseInfo(null, updateProduct, user, purchaseRequest.getQuantity(), purchasePrice, purchaseRequest.getTransportation_type());
            purchaseInfoRepository.save(purchaseInfo);

            if(!updateProduct.getTransportation_type().equals("Self")){
                String response = transportationalRequestsService.makeRequest(updateProduct, user, purchaseRequest.getTransportation_type(), purchaseRequest.getQuantity());
                
                if(response.equals("INITIATED")) {
                    ProductLifecycle productLifecycle = new ProductLifecycle(null, updateProduct, updateProduct.getOwner(), user, user, purchaseRequest.getQuantity(), "", "", new Date());
                    productLifecycleRepository.save(productLifecycle);
                }
                return new PurchaseResponse(response);
            }
            else{
                ProductLifecycle productLifecycle = new ProductLifecycle(null, updateProduct, updateProduct.getOwner(), user, updateProduct.getOwner(),  purchaseRequest.getQuantity(), "", "", new Date());
                productLifecycleRepository.save(productLifecycle);
                return new PurchaseResponse("INITIATED");
            }

        }
        else{
            throw new NotAcceptableStatusException("Purchase not acceptable due to insufficient quantity");
        }
    }

    public Page<PurchaseInfo> findAllPurchaseInfo(Integer page) {
        return purchaseInfoRepository.findAll(PageRequest.of(page, 20));
    }

    public Page<PurchaseInfo> searchByProduct(String productName, Integer page) {
        List<PurchaseInfo> purchases = purchaseInfoDao.findOrdersByProductName(productName, page);
        return new PageImpl<>(purchases, PageRequest.of(page, 20), purchases.size());
    }
}
