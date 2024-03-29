package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.dao.BookDao;
import uz.pdp.domain.Book;
import uz.pdp.dto.BookCreationDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;

    @GetMapping
    public String booksPage(Model model) {
        model.addAttribute("books", bookDao.getAll());
        return "book-register";
    }

    @PostMapping
    public String save(
            @ModelAttribute BookCreationDto bookCreationDto,
            Model model
    ) {
        Book book = bookDao.save(
                Book.builder()
                        .title(bookCreationDto.title())
                        .description(bookCreationDto.description())
                        .price(bookCreationDto.price())
                        .build()
        );
        return "redirect:/books";
    }


    @GetMapping("/all/by_desc")
    public String findAllBooksByDescription(@RequestParam("word") String word
            , Model model) {
        List<Book> books = bookDao.getAllByDescriptionPartialContains(word);
        model.addAttribute("books", books);
        return "books";
    }


}
