package pl.spring.demo.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.spring.demo.controller.BookController;
import pl.spring.demo.enumerations.BookStatus;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

	private MockMvc mockMvc;

	@Mock
	BookService bookService;

	@InjectMocks
	BookController bookController;

	@Before
	public void setup() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/templates/");
		viewResolver.setSuffix(".html");
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testBooksListPage() throws Exception {
		// given
		List<BookTo> allBooks = findAllBooks();
		Mockito.when(bookService.findAllBooks()).thenReturn(allBooks);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books"));
		// then
		resultActions.andExpect(view().name("books")).andExpect(model().attribute("bookList", allBooks));
	}

	@Test
	public void testAllBooksListPage() throws Exception {
		// given
		List<BookTo> allBooks = findAllBooks();
		Mockito.when(bookService.findAllBooks()).thenReturn(allBooks);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/all"));
		// then
		resultActions.andExpect(view().name("books")).andExpect(model().attribute("bookList", allBooks));
		;
	}

	@Test
	public void testSearchBookPage() throws Exception {
		// given
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/search"));
		// then
		resultActions.andExpect(view().name("search"));
		;
	}

	@Test
	public void testSearchResultBookPage() throws Exception {
		// given
		List<BookTo> booksByTitle = findBookByTitleOrAuthor();
		String title = "title1";
		String author = "author1";
		Mockito.when(bookService.searchByTitleOrAuthors(title, author)).thenReturn(booksByTitle);
		// when
		ResultActions resultActions = mockMvc
				.perform(get("/books/search/results").param("title", title).param("author", author));
		// then
		resultActions.andExpect(view().name("search_results")).andExpect(model().attribute("results", booksByTitle));
		;
	}

	@Test
	public void testBookPage() throws Exception {
		// given
		Long id = 1L;
		BookStatus status = BookStatus.FREE;
		BookTo book = new BookTo(id, "title1", "author1", status);
		Mockito.when(bookService.findBookById(id)).thenReturn(book);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book/" + book.getId()));
		// then
		resultActions.andExpect(view().name("book")).andExpect(model().attribute("book", book));
		;
	}

	@Test
	public void testBookAddPageMethodGet() throws Exception {
		// given
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/add"));
		// then
		resultActions.andExpect(view().name("addBook")).andExpect(model().attributeExists("newBook"));
		;
	}
	// FIX test dodawania ksiazki metoda post
	// @Test
	// public void testBookAddPageMethodPost() throws Exception {
	// // given
	// Long id=1L;
	// BookStatus status = BookStatus.FREE;
	// BookTo book=new BookTo(id, "title1", "author1", status);
	// Mockito.when( bookService.saveBook(book)).thenReturn(book);
	// // when
	// ResultActions resultActions = mockMvc.perform(post("/books/add"));
	// // then
	// resultActions.andExpect(view().name("book")).andExpect(model().attribute("book",book));
	// ;
	// }

	@Test
	public void testRemovedBookPage() throws Exception {
		// given
		Long id = 1L;
		BookStatus status = BookStatus.FREE;
		BookTo removedBook = new BookTo(id, "title1", "author1", status);
		Mockito.when(bookService.findBookById(id)).thenReturn(removedBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/removed_book/" + removedBook.getId()));
		// then
		resultActions.andExpect(view().name("removed_book")).andExpect(model().attribute("book", removedBook));
		;
		InOrder inOrder = Mockito.inOrder(bookService);
		inOrder.verify(bookService).deleteBook(id);
	}

	private List<BookTo> findAllBooks() {
		List<BookTo> allBooks = new ArrayList<BookTo>();
		BookStatus status = BookStatus.FREE;
		BookTo book1 = new BookTo(1L, "title1", "author1", status);
		BookTo book2 = new BookTo(2L, "title2", "author2", status);
		BookTo book3 = new BookTo(3L, "title3", "author3", status);
		allBooks.add(book1);
		allBooks.add(book2);
		allBooks.add(book3);
		return allBooks;
	}

	private List<BookTo> findBookByTitleOrAuthor() {
		List<BookTo> booksByTitle = new ArrayList<BookTo>();
		BookStatus status = BookStatus.FREE;
		BookTo book1 = new BookTo(1L, "title1", "author1", status);
		booksByTitle.add(book1);
		return booksByTitle;
	}
}
