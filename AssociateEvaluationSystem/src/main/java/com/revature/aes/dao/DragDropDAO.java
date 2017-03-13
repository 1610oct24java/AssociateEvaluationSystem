package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.aes.beans.DragDrop;

/**
 * Created by Nick on 1/19/2017.
 */
public interface DragDropDAO extends JpaRepository<DragDrop, Integer> {

    public DragDrop findDragDropByDragDropId(int id);

}
