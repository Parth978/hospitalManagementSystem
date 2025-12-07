package com.example.HospitalManagement.controllers;


import com.example.HospitalManagement.models.Bill;
import com.example.HospitalManagement.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping
    public Page<Bill> getAllBills(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return billService.getAllPaymentReceipts(page, size);
    }

    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable long id){
        return billService.getPaymentReceiptById(id);
    }

    @PostMapping
    public Bill createPaymentReceipt(@RequestBody Bill newBill){
        return billService.createPaymentReceipt(newBill);
    }
    @PutMapping("/{id}")
    public Bill updatePaymentReceipt(@PathVariable long id, @RequestBody Bill updatedBill){
        return billService.updatePaymentProfile(id, updatedBill);
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable long id){
        billService.deletePaymentReceipt(id);
    }
}
