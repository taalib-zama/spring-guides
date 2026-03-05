package com.sample.electronicStore.electronicStore.exceptions;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String code, String message, HttpStatus status) {
    // Records are automatically instantiable
}




/*
What it does:

Record = Immutable data class (Java 14+)

Automatically generates constructor, getters, equals(), hashCode(), toString()

Replaces boilerplate code

Why:

Consistent error format across all API responses

Type safety instead of raw strings/maps
Less code than traditional class

*/

