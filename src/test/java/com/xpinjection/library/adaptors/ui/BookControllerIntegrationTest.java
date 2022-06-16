package com.xpinjection.library.adaptors.ui;

import com.xpinjection.library.service.BookService;
import com.xpinjection.library.service.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alimenkou Mikalai
 */
@ActiveProfiles("test")
@WebMvcTest(BookController.class)
@ExtendWith(SpringExtension.class)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final List<Book> books = asList(
            new Book("First", "author"),
            new Book("Second", "another author")
    );

    @BeforeEach
    void init() {
        when(bookService.findAllBooks()).thenReturn(books);
        webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc)
                .useMockMvcForHosts("books.com", "mylibrary.org")
        		.build();
    }

    @Test
    void ifBooksExistThenTheyAreRenderedOnTheLibraryPage() throws Exception {
        var books = asList(new BookDto(1L, "First", "author"),
                new BookDto(2L, "Second", "another author"));
        when(bookService.findAllBooks()).thenReturn(books);

        mockMvc.perform(get("/library.html")
                        .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(content().string(allOf(
                        containsString("First, <em>author</em>"),
                        containsString("Second, <em>another author</em>")))
                );
    }
}
