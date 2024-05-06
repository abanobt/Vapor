package com.vaporapp.util;

/**
 * A generic class for pairing two objects together.
 * This class allows two objects of potentially different types to be treated as a single unit.
 * It can be useful in scenarios where you need to return two values from a method or when you want
 * to link two objects together without creating a specific class for them.
 *
 * @param <T> the type of the first object in the pair
 * @param <U> the type of the second object in the pair
 */
public class Pair<T, U> {
    // Public fields to hold the first and second items of the pair.
    public T firstItem;
    public U secondItem;

    /**
     * Constructs a new Pair with specified first and second items.
     * 
     * @param firstItem The first item of the pair, of type T.
     * @param secondItem The second item of the pair, of type U.
     */
    public Pair(T firstItem, U secondItem) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }
}
