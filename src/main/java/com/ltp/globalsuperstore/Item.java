package com.ltp.globalsuperstore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private String category;
    private String name;
    private Double price;
    private Double discount;
    private String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    
    public Item(){
        this.id=UUID.randomUUID().toString();
    }
    public String getFormatDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
