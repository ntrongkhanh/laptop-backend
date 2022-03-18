package com.example.laptop.service;

import com.example.laptop.db.entity.Image;
import com.example.laptop.db.entity.Manufacturer;
import com.example.laptop.db.mapper.ManufacturerMapper;
import com.example.laptop.db.model.ManufacturerModel;
import com.example.laptop.db.model.ModelPage;
import com.example.laptop.db.repository.ImageRepository;
import com.example.laptop.db.repository.ManufacturerRepository;
import com.example.laptop.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ManufacturerMapper manufacturerMapper;

    @Autowired
    private ImageRepository imageRepository;

    public ResponseEntity<?> getAll() {
        List<Manufacturer> manufacturerEntities = manufacturerRepository.findByActiveTrueOrderByIdDesc();

        return new ResponseEntity<>(new ModelPage<>(manufacturerMapper.toModel(manufacturerEntities)), HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long manufacturerId) {
        Manufacturer manufacturer = manufacturerRepository.findByIdAndActiveTrue(manufacturerId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.MANUFACTURER_NOT_FOUND));

        return new ResponseEntity<>(new ModelPage<>(manufacturerMapper.toModel(manufacturer)), HttpStatus.OK);
    }

    public ResponseEntity<?> create(ManufacturerModel manufacturerModel) {
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerModel);

        Image image = imageRepository.findByIdAndActiveTrue(manufacturerModel.getImageId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.IMAGE_NOT_FOUND));

        manufacturer.setImage(image);
        manufacturer = manufacturerRepository.save(manufacturer);

        return new ResponseEntity<>(new ModelPage<>(manufacturerMapper.toModel(manufacturer)), HttpStatus.OK);
    }

    public ResponseEntity<?> update(ManufacturerModel manufacturerModel) {
        Manufacturer manufacturer = manufacturerRepository.findByIdAndActiveTrue(manufacturerModel.getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.MANUFACTURER_NOT_FOUND));
        manufacturer.setName(manufacturerModel.getName() != null ? manufacturerModel.getName() : manufacturer.getName());
        manufacturer.setNational(manufacturerModel.getNational() != null ? manufacturerModel.getNational() : manufacturer.getNational());
        manufacturer.setProductType(manufacturerModel.getProductType() != null ? manufacturerModel.getProductType() : manufacturer.getProductType());
        manufacturer.setUpdatedDate(new Date());
        manufacturer = manufacturerRepository.save(manufacturer);

        return new ResponseEntity<>(new ModelPage<>(manufacturerMapper.toModel(manufacturer)), HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long manufacturerId) {
        Optional<Manufacturer> manufacturerEntity = manufacturerRepository.findByIdAndActiveTrue(manufacturerId);
        if (!manufacturerEntity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        manufacturerEntity.get().setActive(false);
        manufacturerRepository.save(manufacturerEntity.get());

        return new ResponseEntity<>(new ModelPage<>(MessageConstants.SUCCESS), HttpStatus.OK);
    }
}
