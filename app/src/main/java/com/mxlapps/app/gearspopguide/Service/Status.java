package com.mxlapps.app.gearspopguide.Service;

/**
 * Created by sakethramk on 2/18/18.
 * Status of a resource that is provided to the UI. These are generally created
 * by the repository classes where they return {@code LiveData<Resource<T>>} to pass
 * back the latest data to the UI with its fetch status.
 */
public enum Status {
    SUCCESS,
    ERROR,
    LOADING
}
