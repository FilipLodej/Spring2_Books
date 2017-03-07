package pl.spring.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.SearchTo;

@Controller
@ResponseBody
public class BookRestService {

	@Autowired
	BookService bookService;

	@RequestMapping(value = "/books-search")
	public List<BookTo> searchBook(@RequestParam(value = "titlePrefix", required = false) String titlePrefix,
			@RequestParam(value = "authorPrefix", required = false) String authorPrefix) {
		return bookService.searchByTitleOrAuthors(titlePrefix, authorPrefix);
	}

	@RequestMapping(value = "/books-by-author/{authorPrefix}")
	public List<BookTo> sarchBookByAuthors(@PathVariable("authorPrefix") String authorPrefix) {
		return bookService.findBooksByAuthor(authorPrefix);
	}

	@RequestMapping(value = "/books-search-to", method = RequestMethod.POST)
	public List<BookTo> searchBookWithTo(@RequestBody SearchTo searchTo) {
		return bookService.searchByTitleOrAuthors(searchTo.getTitle(), searchTo.getAuthor());
	}

}
