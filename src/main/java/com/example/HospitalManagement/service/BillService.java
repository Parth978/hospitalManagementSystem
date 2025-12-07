package com.example.HospitalManagement.service;

import com.example.HospitalManagement.models.Bill;
import com.example.HospitalManagement.models.Doctor;
import com.example.HospitalManagement.repository.BillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillService.class);

    @Autowired
    private BillRepository _billRepository;


    public Page<Bill> getAllPaymentReceipts(int page, int size){
        try{
            Pageable pageable = PageRequest.of(page, size);
            return _billRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Facing some issue in fetching all Bills : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Bill getPaymentReceiptById(long id){
        try{
            Optional<Bill> bill = _billRepository.findById(id);
            return bill.orElse(null);
        }catch (Exception e){
            logger.error("Facing Issue in fetching Bill with {} : {}", id, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Bill createPaymentReceipt(Bill newBill){
        try{
            _billRepository.save(newBill);
            return newBill;
        } catch (Exception e) {
            logger.error("Facing Issue while creating a new payment receipt : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Bill updatePaymentProfile(long id, Bill newBill){
        try{
            Optional<Bill> existingBill = _billRepository.findById(id);
            if(existingBill.isPresent()){
                Bill b = existingBill.get();
                b.setAmount(newBill.getAmount());
                b.setPatientId(newBill.getPatientId());
                b.setDues(newBill.isDues());
                _billRepository.save(b);
                return newBill;
            }else{
                logger.error("Unable to find Bill with id : {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Facing issue while updating Bill {} : {}", id, e.getMessage());
            return null;
        }
    }

    public void deletePaymentReceipt(long id){
        try{
            _billRepository.deleteById(id);
        }catch (Exception e){
            logger.error("Facing some issue while deleting Payment Receipt {} : {}", id, e.getMessage());
        }
    }
}
