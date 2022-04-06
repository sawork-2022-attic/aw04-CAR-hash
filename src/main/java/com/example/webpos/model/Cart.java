package com.example.webpos.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Cart implements Serializable {

    private List<Item> items = new ArrayList<>();
    private  int total=0;

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        return total;
    }

    public boolean decrease(String productId,int amount){
        Item toDel=null;
        boolean del=false;
        for (Item item:
                items) {
            if(item.getProduct().getId().equals(productId)){
                if(amount<item.getQuantity()){
                    item.setQuantity(item.getQuantity()-amount);
                    total-=item.getProduct().getPrice()*amount;
                    return true;
                }else{
                    del=true;
                    toDel=item;
                }
            }
        }
        if(del){
            total-=toDel.getQuantity()*toDel.getProduct().getPrice();
            items.remove(toDel);
            return true;
        }
        return false;
    }

    public boolean increase(String productId,int amount){
        for (Item item:
                items) {
            if(item.getProduct().getId().equals(productId)){
                total+=item.getProduct().getPrice();
                item.setQuantity(item.getQuantity()+amount);
                return true;
            }
        }
        return false;
    }

    public boolean del(String productId){
        Item toDel=null;
        for (Item item:
                items) {
            if(item.getProduct().getId().equals(productId)){
                toDel=item;
            }
        }
        if(toDel!=null){
            total-=toDel.getQuantity()*toDel.getProduct().getPrice();
            items.remove(toDel);
            return true;
        }
        return false;
    }

    public void clear(){
        total=0;
        items.clear();
    }

}
