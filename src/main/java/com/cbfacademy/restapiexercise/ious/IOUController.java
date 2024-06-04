package com.cbfacademy.restapiexercise.ious;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/ious")
public class IOUController {
    IOUService iouService; // - in instance of the service interface
    
    public IOUController(IOUService iouService) {
        //- constructor that accepts an instance of the service interface
        this.iouService = iouService;
    }

    /**
     * Retrieve a list of all IOUs.
     *
     * @return A list of all IOUs and HttpStatus OK if successful.
     */
    @GetMapping("")
    public List<IOU> getAllIOUs() {
    // public ResponseEntity<List<IOU>> getAllIOUs() {
        try {
            List<IOU> ious = iouService.getAllIOUs();
            return ious;
            // return ResponseEntity.status(HttpStatus.FOUND).body(ious);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }

    /**
     * Retrieve a specific IOU by its ID.
     *
     * @param id The ID of the IOU to retrieve.
     * @return The requested IOU and HttpStatus OK if found, or HttpStatus NOT_FOUND
     *         if the ID is not found.
     */
    @GetMapping("/{id}")
    public IOU getIOU(@PathVariable UUID id) {
        try {
            IOU iou = iouService.getIOU(id);

            return iou;
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "IOU Not Found", exception);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }

    /**
     * Create a new IOU.
     *
     * @param iou The IOU object to create.
     * @return The created IOU and HttpStatus CREATED if successful.
     */
    @PostMapping
    public ResponseEntity<IOU> createIOU(@RequestBody IOU iou) {
        try {
            IOU createdIOU = iouService.createIOU(iou);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdIOU);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }

    /**
     * Update an existing IOU by ID.
     *
     * @param id         The ID of the IOU to update.
     * @param updatedIOU The updated IOU object.
     * @return The updated IOU and HttpStatus OK if successful, or HttpStatus
     *         NOT_FOUND if the ID is not found.
     */
    @PutMapping("/{id}")
    public IOU updateIOU(@PathVariable UUID id, @RequestBody IOU updatedIOU) {
        try {
            IOU iou = iouService.updateIOU(id, updatedIOU);

            return iou;
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "IOU Not Found", exception);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }

    /**
     * Delete an IOU by ID.
     *
     * @param id The ID of the IOU to delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIOU(@PathVariable UUID id) {
        try {
            iouService.deleteIOU(id);

            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "IOU Not Found", exception);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }
}
