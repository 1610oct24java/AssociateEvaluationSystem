package com.revature.aes.service;

import com.revature.aes.beans.Option;
import com.revature.aes.dao.OptionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Nick on 1/19/2017.
 */
@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDAO optDao;

    public Option getOptionById(int id){

        return optDao.findOptionByOptionId(id);

    }

	@Override
	public void removeOptionById(int id) {

		optDao.delete(id);
		
	}

}
