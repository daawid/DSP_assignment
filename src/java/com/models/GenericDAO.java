package com.models;

import java.util.List;


/**
 *
 * @author dawid
 */
/*
 * GenericDAO used for CRUD operations
 *  - Create 
 *  - Retrieve 
 *  - Update 
 *  - Delete - won't be needed in this program
 */
public interface GenericDAO<E, T> { // 'E' - type of bean to use, 'T' - the ID
    public boolean create(E e); //Create
    public List<E> getAll(); //Return all
    public E getByID(T id); //Return by ID
}
