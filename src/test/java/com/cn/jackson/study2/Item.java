package com.cn.jackson.study2;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:35
 * @Description:
 */
//@JsonSerialize(using = ItemSerializer.class)
public class Item {
    private int id;
    private String itemName;
    private User owner;

    public Item() {
    }

    public Item(int id, String itemName, User owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", owner=" + owner +
                '}';
    }
}
