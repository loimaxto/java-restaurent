package com.example.retaurant.interfaces;

public interface CrudInterface<T> {
    int create(T t) throws Exception; // Create a new record in the database

    int update(T t) throws Exception; // Update an existing record in the database

    int delete(int id) throws Exception; // Delete a record from the database

    T read(int id) throws Exception; // Read a record from the database
}
