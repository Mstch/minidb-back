package com.tiddar.minidb.core.model;

import lombok.Data;

import java.util.List;

/**
 * @author zhangweichen
 * @date 2019/9/6 11:36 上午
 */
@Data
public class Table {
    private List<Col> columns;
    private Col key;
    private String name;

    public Col getCol(String name){
        for (Col column : columns) {
            if(column.getName().equals(name)){
                return column;
            }
        }
        return null;
    }
}
