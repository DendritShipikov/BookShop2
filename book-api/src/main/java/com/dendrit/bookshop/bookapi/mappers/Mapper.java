package com.dendrit.bookshop.bookapi.mappers;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<E, D> {

    E toEntity(D data);

    D toData(E entity);

    default List<E> toEntityList(Iterable<D> dataList) {
        List<E> entityList = new ArrayList<>();
        for (D data : dataList) {
            E entity = toEntity(data);
            entityList.add(entity);
        }
        return entityList;
    }

    default List<D> toDataList(Iterable<E> entityList) {
        List<D> dataList = new ArrayList<>();
        for (E entity : entityList) {
            D data = toData(entity);
            dataList.add(data);
        }
        return dataList;
    }
}
