/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_14062696.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
public class start extends HttpServlet {
    


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
            

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            if (session.getCreationTime() > 30000) {
                session.invalidate();
                session = request.getSession();
            }
            
            
//          Sets up persistant wins and games
            String relativeWebPath = "/json/stats.json";
            String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
            String games = Read.Json(1,absoluteDiskPath);
            String wins = Read.Json(2,absoluteDiskPath);
            int g = Integer.parseInt(games);
            g ++;
            session.setAttribute("games", g);  
            session.setAttribute("wins", wins);
            Write.delete(absoluteDiskPath);
            Write.JSON(g, Integer.parseInt(wins),absoluteDiskPath);
//          SECTION 1 -- Initializes/gets any variables            
//            Initialize the deck
            Collection deck = new LinkedHashSet();
            Integer position []  = {};
            ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(position));
            for (int i = 0; i < 52; i++) {
                arrayList.add(i);
            }
            Collections.shuffle(arrayList);
            for (int i = 0; i < 52; i++) {
                deck.add(arrayList.get(i));
            }          
//            Initialize the players hand drawing two cards                        
            Collection playerHand = new LinkedHashSet();                       
//            Initialize the dealers hand drawing two cards          
            Collection dealHand = new LinkedHashSet();                         
//            Initialize a card counter so I can keep track of how many cards have been drawn                 
            Collection cardCounter = new LinkedHashSet();
            cardCounter.add(0);                      
//            Initialize a card counter so I can keep track of how many cards have been drawn             
            Collection whoseTurn = new LinkedHashSet();
            whoseTurn.add("user");           
                     
//          SECTION 2 -- Calculations.             
//            Sets data to all the attributes for hand dealer deck and cardDrawn.  
            int count;
            dealHand = addCards(2, dealHand, deck.toArray(), cardCounter.toArray());
            count = incriDeck(2, cardCounter.toArray());
            cardCounter.clear();
            cardCounter.add(count);
            playerHand = addCards(2, playerHand, deck.toArray(), cardCounter.toArray());
            count = incriDeck(2, cardCounter.toArray());
            cardCounter.clear();
            cardCounter.add(count);


//          SECTION 3 -- Set all attributes  
            session.setAttribute("hideTotal", "none");
            session.setAttribute("won", "none");            
//            Sets attributes for above contructers
            session.setAttribute("deck", deck);
            session.setAttribute("playerHand", playerHand);
            session.setAttribute("dealerHand", dealHand);
            session.setAttribute("cardCounter", cardCounter);
            session.setAttribute("whoseTurn", whoseTurn);            
//            Sets Total.
            session.setAttribute("dealerTotal", getTotal(dealHand.toArray()));
            session.setAttribute("playerTotal", getTotal(playerHand.toArray()));                     
//            Sets the attributes of the cards to resemble there values.
            for (int i = 0; i < playerHand.toArray().length; i++) {
                String num = getCard(playerHand.toArray()[i]);
                session.setAttribute("card"+playerHand.toArray()[i].toString(), num);
            }
            for (int i = 0; i < dealHand.toArray().length; i++) {
                String num = getCard(dealHand.toArray()[i]);
                session.setAttribute("card"+dealHand.toArray()[i].toString(), num);
            }
//            Sets all the attributes for the actions on the buttons            
            session.setAttribute("actionStart", "start;jsessionid="+session.getId());
            session.setAttribute("actionHit", "move/hit;jsessionid="+session.getId());
            session.setAttribute("actionStand", "move/stand;jsessionid="+session.getId());
            session.setAttribute("actionStats", "stats;jsessionid="+session.getId());               
 
//          SECTION 4 -- Routing           
            request.getRequestDispatcher("/jsp/blackjack.jsp").forward(request, response);          
            
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

