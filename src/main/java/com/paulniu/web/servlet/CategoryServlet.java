package com.paulniu.web.servlet;

import com.paulniu.domain.Category;
import com.paulniu.service.CategoryService;
import com.paulniu.service.impl.ICategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new ICategoryServiceImpl();

    /**
     * 查询所有分类
     * @param request
     * @param response
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Category> cs = service.findAll();
        writeValue(cs, response);
    }

}
