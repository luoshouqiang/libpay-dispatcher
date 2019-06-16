package com.libpay.dispatcher.entity;


import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
 
@MappedSuperclass
public class BaseEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3115826704402082760L;

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}