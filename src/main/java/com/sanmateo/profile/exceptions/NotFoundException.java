package com.sanmateo.profile.exceptions;


import com.sanmateo.profile.models.BaseModel;

/**
 * Created by rsbulanon on 6/11/17.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<? extends BaseModel> clazz, String id) {
        super("Resource " + clazz.getSimpleName() + " ID not found: " + id);
    }
}
