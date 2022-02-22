package main.by.library.util;

import main.by.library.entity.Author;
import main.by.library.entity.Genre;
import main.by.library.entity.Section;
import main.by.library.services.AuthorService;
import main.by.library.services.GenreService;
import main.by.library.services.SectionService;
import main.by.library.services.impl.AuthorServiceImpl;
import main.by.library.services.impl.GenreServiceImpl;
import main.by.library.services.impl.SectionServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static main.by.library.util.PageUtil.*;

public final class UtilCommand {

    private UtilCommand() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a list with 0 and values that are multiples of 10 (limit equal 10)
     * @param req HttpServletRequest object
     * @param countRow total number of rows in the table
     */
    public static void getListOffset(HttpServletRequest req, int countRow) {
        int countPage;
        if (countRow % LIMIT_TEN != 0) {
            countPage = countRow / LIMIT_TEN + 1;
        } else {
            countPage = countRow / LIMIT_TEN;
        }
        List<Integer> offsetList = new ArrayList<>(countPage);
        int num = 0;
        for (int i = 0; i < countPage; i++) {
            offsetList.add(num);
            num += LIMIT_TEN;
        }
        req.setAttribute(OFFSET_LIST_ATTRIBUTE, offsetList);
    }

    /**
     * Puts list of Author in the session attributes
     * @param req HttpServletRequest object
     * @param limit quantity per page
     */
    public static void showAllAuthor(HttpServletRequest req, int limit){
        AuthorService authorService = AuthorServiceImpl.getInstance();
        List<Author> allAuthor = authorService.findAllAuthor(limit, OFFSET_ZERO);
        req.setAttribute(PageUtil.AUTHORS_ATTRIBUTE, allAuthor);
    }

    /**
     * Puts list of Genre in the session attributes
     * @param req HttpServletRequest object
     * @param limit quantity per page
     */
    public static void showAllGenre(HttpServletRequest req, int limit){
        GenreService genreService = GenreServiceImpl.getInstance();
        List<Genre> allGenre = genreService.findAllGenre(limit, OFFSET_ZERO);
        req.setAttribute(PageUtil.GENRES_ATTRIBUTE, allGenre);
    }

    /**
     * Puts list of Section in the session attributes
     * @param req HttpServletRequest object
     * @param limit quantity per page
     */
    public static void showAllSection(HttpServletRequest req, int limit){
        SectionService sectionService = SectionServiceImpl.getInstance();
        List<Section> allSection = sectionService.findAllSection(limit, OFFSET_ZERO);
        req.setAttribute(PageUtil.SECTIONS_ATTRIBUTE, allSection);
    }
}
