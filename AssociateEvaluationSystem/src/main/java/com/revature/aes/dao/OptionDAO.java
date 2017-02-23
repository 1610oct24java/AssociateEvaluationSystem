package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Option;

/**
 * Created by Nick on 1/19/2017.
 */
@Repository
public interface OptionDAO extends JpaRepository<Option, Integer> {

    public Option findOptionByOptionId(int id);

}
