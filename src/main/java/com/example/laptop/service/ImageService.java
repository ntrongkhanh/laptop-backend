package com.example.laptop.service;

import com.example.laptop.db.entity.Image;
import com.example.laptop.db.model.ImageModel;
import com.example.laptop.db.model.ModelPage;
import com.example.laptop.db.repository.ImageRepository;
import com.example.laptop.utils.Constants;
import com.example.laptop.utils.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public ResponseEntity<?> create(MultipartFile file) {
        Image image = new Image();
        try {
            image.setData(file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        image = imageRepository.save(image);
        ModelPage data = new ModelPage(new ImageModel(image.getId()));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long imageId) {
        Image image = imageRepository.findByIdAndActiveTrue(imageId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.IMAGE_NOT_FOUND));

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
                .header(com.google.common.net.HttpHeaders.CONTENT_DISPOSITION, Constants.getHeaderValues(image.getId() + ".jpg"))
                .body(image.getData());
    }

    public ResponseEntity<?> update(MultipartFile file, Long imageId) {
        Image image = imageRepository.findByIdAndActiveTrue(imageId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.IMAGE_NOT_FOUND));
        try {
            image.setData(file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        image.setUpdatedDate(new Date());
        image = imageRepository.save(image);

        ModelPage data = new ModelPage(new ImageModel(image.getId()));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long imageId) {
        Image image = imageRepository.findByIdAndActiveTrue(imageId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstants.IMAGE_NOT_FOUND));
        image.setActive(false);
        imageRepository.save(image);

        ModelPage data = new ModelPage(MessageConstants.SUCCESS);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
