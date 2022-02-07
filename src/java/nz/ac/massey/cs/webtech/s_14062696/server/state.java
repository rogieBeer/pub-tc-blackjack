/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_14062696.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Tong - 14062696
 */
public class state extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        HttpSession session = request.getSession();
        
        JSONObject jObj = new JSONObject();
//        can be used to recieve the state of the game.
//         NOT TODO -- add 404 error as I added it twice on another servlet by accident.
        if ("start".equals(request.getParameter("game"))){    
            try {
                jObj.put("player", session.getAttribute("playerHand"));
                jObj.put("playerTotal", session.getAttribute("playerTotal"));
                jObj.put("dealer", session.getAttribute("dealerHand"));
                jObj.put("dealerTotal", session.getAttribute("dealerTotal"));
                jObj.put("cardCounter", session.getAttribute("cardCounter"));
                jObj.put("whoseTurn", session.getAttribute("whoseTurn"));
                jObj.put("games", session.getAttribute("games"));
                jObj.put("won", session.getAttribute("won"));
                jObj.put("wins", session.getAttribute("wins"));
                jObj.put("deck", session.getAttribute("deck"));
            }
            catch (Exception ex) {
                jObj.put("error", "Could not evaluate expression");
            }

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println(jObj.toString());           
            };
        }else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"There is no active game");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
