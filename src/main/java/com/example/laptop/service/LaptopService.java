package com.example.laptop.service;

import com.example.laptop.db.entity.*;
import com.example.laptop.db.enums.ProductType;
import com.example.laptop.db.mapper.LaptopMapper;
import com.example.laptop.db.model.ImageModel;
import com.example.laptop.db.model.LaptopModel;
import com.example.laptop.db.model.ModelPage;
import com.example.laptop.db.model.Pagination;
import com.example.laptop.db.repository.*;
import com.example.laptop.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private LaptopMapper laptopMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public ResponseEntity<?> getAll() {
        List<Product> laptopEntities = productRepository.findByProductTypeAndActiveTrueOrderByIdDesc(ProductType.LAPTOP);

        return new ResponseEntity<>(new ModelPage<>(laptopMapper.productToModel(laptopEntities)), HttpStatus.OK);
    }

    public ResponseEntity<?> filter(String keyword, int page, int pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        Page<Product> laptopEntities;
        if (keyword != null) {
            laptopEntities = productRepository.findByNameContainingAndProductTypeAndActiveTrueOrderByIdDesc(keyword, ProductType.LAPTOP, paging);
        } else {
            laptopEntities = productRepository.findByProductTypeAndActiveTrueOrderByIdDesc(ProductType.LAPTOP, paging);
        }

        return new ResponseEntity<>(new ModelPage<>(laptopMapper.productToModel(laptopEntities.getContent()),
                new Pagination((int) laptopEntities.getTotalElements(), page)), HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long productId) {
        Product product = productRepository.findByIdAndActiveTrue(productId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND));
        LaptopModel laptopModel = laptopMapper.productToModel(product);

        return new ResponseEntity<>(new ModelPage<>(laptopModel), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> create(LaptopModel laptopModel) {
        Product product = laptopMapper.modelToEntity(laptopModel);

        Category category = categoryRepository.findByIdAndActiveTrue(laptopModel.getCategoryId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.IMAGE_NOT_FOUND));
        product.setCategory(category);

        Manufacturer manufacturer = manufacturerRepository.findByIdAndActiveTrue(laptopModel.getManufacturerId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.IMAGE_NOT_FOUND));
        product.setManufacturer(manufacturer);
        product = productRepository.saveAndFlush(product);

        for (ImageModel imageModel : laptopModel.getImages()) {
            Image image = imageRepository.findByIdAndActiveTrue(imageModel.getId()).orElseThrow(()
                    -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.IMAGE_NOT_FOUND));
            image.setProduct(product);
            imageRepository.saveAndFlush(image);
        }

        Laptop laptop = laptopMapper.toEntity(laptopModel);
        laptop.setProduct(product);
        laptopRepository.saveAndFlush(laptop);

        product = productRepository.findByIdAndActiveTrue(product.getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.IMAGE_NOT_FOUND));
        LaptopModel model = laptopMapper.productToModel(product);

        return new ResponseEntity<>(new ModelPage<>(model), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> update(LaptopModel laptopModel) {
        Product product = productRepository.findByIdAndActiveTrue(laptopModel.getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND));
        Laptop laptop = product.getLaptop();

        Date currentDate = new Date();
        laptop.setStorage(laptopModel.getStorage() != null ? laptopModel.getStorage() : laptop.getStorage());
        laptop.setScreen(laptopModel.getScreen() != null ? laptopModel.getScreen() : laptop.getScreen());
        laptop.setRam(laptopModel.getRam() != null ? laptopModel.getRam() : laptop.getRam());
        laptop.setPort(laptopModel.getPort() != null ? laptopModel.getPort() : laptop.getPort());
        laptop.setOS(laptopModel.getOS() != null ? laptopModel.getOS() : laptop.getOS());
        laptop.setGraphicCard(laptopModel.getGraphicCard() != null ? laptopModel.getGraphicCard() : laptop.getGraphicCard());
        laptop.setCpu(laptopModel.getCpu() != null ? laptopModel.getCpu() : laptop.getCpu());
        laptop.setBattery(laptopModel.getBattery() != null ? laptopModel.getBattery() : laptop.getBattery());
        laptop.setUpdatedDate(currentDate);
        laptopRepository.save(laptop);

        product.setYear(laptopModel.getYear() != null ? laptopModel.getYear() : product.getYear());
        product.setWeight(laptopModel.getWeight() != null ? laptopModel.getWeight() : product.getWeight());
        product.setStatus(laptopModel.getStatus() != null ? laptopModel.getStatus() : product.getStatus());
        product.setProductType(laptopModel.getProductType() != null ? laptopModel.getProductType() : product.getProductType());
        product.setPrice(laptopModel.getPrice() != null ? laptopModel.getPrice() : product.getPrice());
        product.setName(laptopModel.getName() != null ? laptopModel.getName() : product.getName());
        product.setModelCode(laptopModel.getModelCode() != null ? laptopModel.getModelCode() : product.getModelCode());
        product.setGuarantee(laptopModel.getGuarantee() != null ? laptopModel.getGuarantee() : product.getGuarantee());
        product.setDescription(laptopModel.getDescription() != null ? laptopModel.getDescription() : product.getDescription());
        product.setColor(laptopModel.getColor() != null ? laptopModel.getColor() : product.getColor());

        if (laptopModel.getManufacturerId() != null) {
            Manufacturer manufacturer = manufacturerRepository
                    .findByIdAndActiveTrue(laptopModel.getManufacturerId()).orElseThrow(()
                            -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND));
            product.setManufacturer(manufacturer);
        }
        product.setUpdatedDate(currentDate);
        product = productRepository.save(product);

        return new ResponseEntity<>(new ModelPage<>(laptopMapper.productToModel(product)), HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long productId) {
        Optional<Product> product = productRepository.findByIdAndActiveTrue(productId);
        if (!product.isPresent()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        product.get().setActive(false);
        product.get().getLaptop().setActive(false);
        laptopRepository.save(product.get().getLaptop());
        productRepository.save(product.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
