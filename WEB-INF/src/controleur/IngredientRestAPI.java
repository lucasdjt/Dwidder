package controleurs;

import java.io.*;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.*;
import dto.Ingredient;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ingredients/*")
public class IngredientRestAPI extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        DAOIngredient ingredientDAO = choixBase();
        ObjectMapper objectMapper = new ObjectMapper();

        String info = req.getPathInfo();

        if(info == null || info.equals("/")) {
            List<Ingredient> i = ingredientDAO.findAll();
            out.println(objectMapper.writeValueAsString(i));
            out.close();
            return;
        }

        String[] splits = info.split("/");
        
        if (splits.length != 2 || !isInteger(splits[1])) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            out.close();
            return;
        }

        String id = splits[1];
        System.out.println("Recherche de l'ingrédient avec ID : " + id);
        Ingredient i = ingredientDAO.findById(Integer.parseInt(id));
        if (i==null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            out.close();
            return;
        }
        out.println(objectMapper.writeValueAsString(i));
        out.close();
        return;

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Ingredient ingredient = objectMapper.readValue(data.toString(), Ingredient.class);

        DAOIngredient ingredientDAO = choixBase();
        ingredientDAO.save(ingredient);
        
        out.println("sucess - \"ingredient\": " + objectMapper.writeValueAsString(ingredient));
        out.close();
    }

    private boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private DAOIngredient choixBase(){
        return new IngredientDAODatabase();
    }
    
}