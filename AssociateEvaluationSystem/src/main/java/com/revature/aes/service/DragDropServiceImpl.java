package com.revature.aes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.DragDrop;
import com.revature.aes.dao.AssessmentDragDropDAO;
import com.revature.aes.dao.DragDropDAO;

/**
 * Created by Nick on 1/19/2017.
 */
@Service
public class DragDropServiceImpl implements DragDropService {

    @Autowired
    DragDropDAO ddDao;

    public DragDrop getDragDropById(int id){

        return ddDao.findDragDropByDragDropId(id);

    }

	@Override
	public void removeDragDropById(int id) {
		ddDao.delete(id);		
	}

	@Override
	public DragDrop addDragDrop(DragDrop dragdrop) {
		return ddDao.save(dragdrop);
	}

}
