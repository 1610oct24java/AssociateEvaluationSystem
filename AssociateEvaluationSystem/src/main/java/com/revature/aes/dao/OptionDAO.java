package com.revature.aes.dao;

import com.revature.aes.beans.Option;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nick on 1/19/2017.
 */
public interface OptionDAO extends JpaRepository<Option, Integer> {

    public Option findOptionByOptionId(int id);

}
