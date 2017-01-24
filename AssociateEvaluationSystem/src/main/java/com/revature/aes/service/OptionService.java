package com.revature.aes.service;

import com.revature.aes.beans.Option;

/**
 * Created by Nick on 1/19/2017.
 */
public interface OptionService {

    public Option getOptionById(int id);
    public void removeOptionById(int id);

}
