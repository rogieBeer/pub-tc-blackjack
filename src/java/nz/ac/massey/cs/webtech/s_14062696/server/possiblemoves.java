/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_14062696.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Tong - 14062696
 */
public class possiblemoves extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
//        Checks to see if the game is running, I dont know why I have it in there twice but im to scarede to delete it.
        if ("start".equals(request.getParameter("game"))){
            try (PrintWriter out = response.getWriter()) {
                if ("start".equals(request.getParameter("game"))){
                    HttpSession session = request.getSession();
                    Collection playerHand = (Collection)session.getAttribute("playerHand");
                    if (playerHand==null) {
                        playerHand = new LinkedHashSet();
                    }
                    int total = getTotal(playerHand.toArray());
//                    If the players hand a certian value it will disable the buttons
                    if (total < 17){
                        session.setAttribute("possibleHit", " ");
                    }
                    if (total < 21){
                        session.setAttribute("possibleHit", " ");
                    }
                    if (total == 21){
                        session.setAttribute("possibleHit", "disabled");
                    }
                    if (total > 21){
                        session.setAttribute("possibleHit", "disabled");
                    }

                    request.getRequestDispatcher("/jsp/blackjack.jsp").forward(request, response);

                }
                else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,"There is no active game");
                }
            }
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

//    Gets the total value of the hand
        public static int getTotal(Object [] cards){
//        Builds a static set of 52 cards.
        int Total = 0;
        int VALUE = 2;
        List<List<String>> DECK = Deck.buildDeck(); 
        for (int i = 0; i < cards.length; i++) {
            String temp = cards[i].toString();
            int position = Integer.parseInt(temp);
            List<String> card = DECK.get(position);
            String value = card.get(VALUE);
            Total = Total + Integer.parseInt(value);
        }
        return Total;    
    }
    
}
