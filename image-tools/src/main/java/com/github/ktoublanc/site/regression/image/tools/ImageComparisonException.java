package com.github.ktoublanc.site.regression.image.tools;

/**
 * Image comparison exception class used to wrap exception raised within this class
 * Created by kevin on 08/05/2016.
 */
public class ImageComparisonException extends Exception {

    /**
     * Builds an {@link ImageComparisonException} instance initialized with the custom message
     *
     * @param message The custom message
     */
    public ImageComparisonException(final String message) {
        super(message);
    }
}
