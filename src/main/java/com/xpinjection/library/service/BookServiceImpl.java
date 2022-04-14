package com.xpinjection.library.service;

import com.xpinjection.library.adaptors.persistence.BookDao;
import com.xpinjection.library.domain.Book;
import com.xpinjection.library.domain.Books;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.stream.Collectors.toList;

/**
 * @author Alimenkou Mikalai
 */
@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final ConcurrentMap<String, List<Book>> cache = new ConcurrentHashMap<>();

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> addBooks(Books books) {
        LOG.info("Adding books: {}", books);
        return books.asList().stream()
                .map(bookDao::save)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findBooksByAuthor(String author) {
        LOG.info("Try to find books by author: {}", author);
        Assert.hasText(author, "Author is empty!");
        var normalizedAuthor = normalizeAuthorName(author);
        return cache.computeIfAbsent(normalizedAuthor, bookDao::findByAuthor);
    }

    private String normalizeAuthorName(String author) {
        var authorName = StringUtils.normalizeSpace(author);
        return isSingleWord(authorName) ? splitOnFirstAndLastNames(authorName) : authorName;
    }

    private boolean isSingleWord(String correctAuthor) {
        return !StringUtils.containsWhitespace(correctAuthor);
    }

    private String splitOnFirstAndLastNames(String author) {
        var parts = StringUtils.splitByCharacterTypeCamelCase(author);
        var firstName = parts[0];
        if (parts.length == 1) {
            return firstName;
        }
        var lastName = StringUtils.substringAfter(author, firstName);
        return String.join(" ", firstName, lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllBooks() {
        LOG.info("Finding all books");
        return bookDao.findAll();
    }
}
