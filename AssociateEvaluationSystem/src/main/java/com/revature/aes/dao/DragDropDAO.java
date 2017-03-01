package com.revature.aes.dao;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.DragDrop;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nick on 1/19/2017.
 */
public interface DragDropDAO extends JpaRepository<DragDrop, Integer> {

    public DragDrop findDragDropByDragDropId(int id);

}
