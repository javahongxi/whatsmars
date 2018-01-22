package org.hongxi.whatsmars.boot.cache;

public interface BookRepository {

    Book getByIsbn(String isbn);

}