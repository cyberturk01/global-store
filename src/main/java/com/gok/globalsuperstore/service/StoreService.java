package com.ltp.globalsuperstore.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.globalsuperstore.Constants;
import com.ltp.globalsuperstore.Item;
import com.ltp.globalsuperstore.repository.StoreRepository;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    public Item getItem(int index){
        return storeRepository.getItem(index);
    }
    public void addItem(Item item){
        storeRepository.addItem(item);
    }
    public void updateItem(int index, Item item){
        storeRepository.updateItem(index, item);
    }
    public List<Item> getItems(){
        return storeRepository.getItems();
    }
     public int getIndexNumber(String id){
        for (int i = 0; i < storeRepository.getItems().size(); i++) {
            if(storeRepository.getItem(i).getId().equals(id)){
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

    public boolean within5Days(Date newDate, Date oldDate) {
        long diff = Math.abs(newDate.getTime() - oldDate.getTime());
        return (int) (TimeUnit.MILLISECONDS.toDays(diff)) <= 5;
    }

    public Item getItemById(String id){
         int index= getIndexNumber(id);
        return 
        index==Constants.NOT_FOUND ? new Item() : storeRepository.getItem(index);
    }

    public String submitItem(Item item){
        int index=getIndexNumber(item.getId());
        String status = Constants.SUCCESS_STATUS;
        if(index==Constants.NOT_FOUND){
            addItem(item);
        }else if (within5Days(item.getDate(), getItem(index).getDate())){
            updateItem(index, item);
        } else {
            status = Constants.FAILED_STATUS;
        }
        return status;
    }
    
}