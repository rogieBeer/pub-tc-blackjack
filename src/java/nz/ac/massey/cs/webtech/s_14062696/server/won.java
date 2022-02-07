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
public class won extends HttpServlet {
    


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
        
        if ("start".equals(request.getParameter("game"))){
            try (PrintWriter out = response.getWriter()) {
                HttpSession session = request.getSession();


    //          SECTION 1 -- Initializes/gets any variables 
    //          Sets up persistant wins and games
                String relativeWebPath = "json/stats.json";
                String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
                String games = Read.Json(1,absoluteDiskPath);
                String wins = Read.Json(2,absoluteDiskPath);


                Collection whoseTurn = (Collection) session.getAttribute("whoseTurn");
                if (whoseTurn == null) {
                    whoseTurn = new LinkedHashSet();
                    whoseTurn.add("user");
                } 
                int P_TOTAL;
                if (session.getAttribute("playerTotal")==null){
                    P_TOTAL = 0;
                }else{
                    P_TOTAL = Integer.parseInt(session.getAttribute("playerTotal").toString());
                } 
                int D_TOTAL;
                if (session.getAttribute("dealerTotal")==null){
                    D_TOTAL = 0;
                }else{
                    D_TOTAL = Integer.parseInt(session.getAttribute("dealerTotal").toString());
                }


    //          SECTION 2 -- Calculations.  
//              Sets the attribute to won if the user or computer win aswell as if tied
                if ("dealer".equals(whoseTurn.toArray()[0])){
    //              PLAYER                
                    if (P_TOTAL<22){ 
                        if (D_TOTAL > 21) {
                            session.setAttribute("won", "user"); 
                            int g = Integer.parseInt(wins);
                            g ++;
                            session.setAttribute("wins", g);
                            Write.delete(absoluteDiskPath);
                            Write.JSON(Integer.parseInt(games), g,absoluteDiskPath);
                        }
                        else if (P_TOTAL>D_TOTAL){
                            session.setAttribute("won", "user"); 
                            int g = Integer.parseInt(wins);
                            g ++;
                            session.setAttribute("wins", g);
                            Write.delete(absoluteDiskPath);
                            Write.JSON(Integer.parseInt(games), g,absoluteDiskPath);
                        }
                        else if (D_TOTAL>P_TOTAL){
                            session.setAttribute("won", "computer");   
                        }
                        else if (D_TOTAL==P_TOTAL) {
                                session.setAttribute("won", "tie");         
                        }

                    }
                    else if (P_TOTAL>22){
                        if (D_TOTAL < 21) {
                            session.setAttribute("won", "computer");    
                        }       
                    } 
                    else {
                        session.setAttribute("won", "none");   
                    }   
                }



    //          SECTION 3 -- Set all attributes

    //            Sets all the attributes for the actions on the buttons            
                session.setAttribute("actionStart", "/assignment2_server_14062696/start;jsessionid="+session.getId());
                session.setAttribute("actionHit", "move/hit;jsessionid="+session.getId());
                session.setAttribute("actionStand", "move/stand;jsessionid="+session.getId());
                session.setAttribute("actionStats", "/assignment2_server_14062696/stats;jsessionid="+session.getId());               


    //          SECTION 4 -- Routing & Quick Testing
                if ("test".equals(request.getParameter("test"))){
                    out.println("Player Total: ");
                    out.println(P_TOTAL);
                    out.println(" ");
                    out.println("Dealer Total: ");
                    out.println(D_TOTAL);
                    out.println(" ");                
                } else {
                    request.getRequestDispatcher("/jsp/blackjack.jsp").forward(request, response); 
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

    
    public static Collection addCards(int amount, Collection hand, Object[] deck, Object[] cardCount){
        String temp = cardCount[0].toString();
        int count = Integer.parseInt(temp);
        for (int i = 0; i < amount; i++) { 
            hand.add(deck[count]);
            count ++;
            }
        return hand;
    }
    
    public static int incriDeck(int amount, Object [] cardCount){
        String temp = cardCount[0].toString();
        int count = Integer.parseInt(temp);
        count = count + amount;
        return count;
    }
    
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
    
    public static String getCard(Object cardNumber){
        String temp = cardNumber.toString();
        int position = Integer.parseInt(temp);
        List<List<String>> DECK = Deck.buildDeck(); 
        List<String> card = DECK.get(position);
        return card.get(0)+" of "+card.get(1);
    }
    
}

