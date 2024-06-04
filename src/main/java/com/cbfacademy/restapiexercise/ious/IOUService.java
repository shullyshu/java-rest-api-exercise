package com.cbfacademy.restapiexercise.ious;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class IOUService {
    private IOURepository iouRepository;

    public IOUService(IOURepository iouRepository){
        this.iouRepository = iouRepository;
    }

    public IOU createIOU(IOU iou) throws IllegalArgumentException, OptimisticLockingFailureException {
        return iouRepository.save(iou);
    }

    public void deleteIOU(UUID id) throws NoSuchElementException {
        if (iouRepository.findById(id).isPresent()) {
            iouRepository.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    public List<IOU> getAllIOUs() {
        return iouRepository.findAll();
    }

    public IOU getIOU(UUID id) throws NoSuchElementException {
        return iouRepository.findById(id).orElseThrow();
    }

    public IOU updateIOU(UUID id, IOU updatedIOU) throws NoSuchElementException {
        IOU iou = iouRepository.findById(id).orElseThrow(); // find the IOU

        // old IOU values = new IOU values
        iou.setBorrower(updatedIOU.getBorrower());
        iou.setLender(updatedIOU.getLender());
        iou.setAmount(updatedIOU.getAmount());
        
        // save the updated IOU
        return iouRepository.save(iou);
    }
}