package edu.asu.mc29.mywardrobe.service;

import edu.asu.mc29.mywardrobe.data.Channel;

/**
 * Created by cchen211 on 10/28/2015.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
