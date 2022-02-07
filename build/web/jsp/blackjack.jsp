<%-- 
    Document   : blackjack
    Created on : 9/05/2021, 9:29:02 PM
    Author     : Tong - 14062696
--%>

<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/blackjack.css">
        <link rel="stylesheet" href="/css/blackjack.css">
        <title>Black Jack</title>
    </head>
    <body>
        
        <% Collection deck = (Collection)session.getAttribute("deck"); %>
        <% Collection playerHand = (Collection)session.getAttribute("playerHand"); %>
        <% Collection dealHand = (Collection)session.getAttribute("dealerHand"); %>
        <% Collection cardCounter = (Collection)session.getAttribute("cardCounter"); %>
        <% Collection whoseTurn = (Collection)session.getAttribute("whoseTurn"); %>        
        <%
            if (dealHand != null) {
        %>    
            <p><%Object [] item = dealHand.toArray();%></p>
            <div class="dealerHand" action="<%=response.encodeURL("dealerHand")%>">
                <h3>Dealers Hand</h3>
                <ul>
                    <%
                        Object [] turn = whoseTurn.toArray();
                        String pos = turn[0].toString();
                    %>    
                    <%    
                        if (pos == "user") {
                    %>
                        <li>
                            <% Object card = session.getAttribute("card"+item[0].toString()); %>
                            <%=card%>
                        </li> 
                    <%
                        }
                    %>
                    <%
                        if (pos == "dealer") {
                    %>
                        <%for (int i=0;i<item.length;i++) { %>
                            <li>
                                <% Object card = session.getAttribute("card"+item[i].toString()); %>
                                <%=card%>
                            </li>
                        <% } %>
                    <%
                        }
                    %>
                </ul>
                <% if (whoseTurn != null) { %>
                    <% if ("dealer".equals(whoseTurn.toArray()[0])){%>
                        <p>Dealer Total: ${dealerTotal}</p>
                    <%}%> 
                <%}%> 
                    
            </div>
            
        <%
            }
        %>
        <% if (whoseTurn != null) { %>
            <% if ("dealer".equals(whoseTurn.toArray()[0])){%>
                <div  class="won" action="<%=response.encodeURL("won")%>">
                    <h2>The game winner is: ${won}</h2>
                </div>
            <%}%> 
        <%}%>    
        <%
            if (playerHand != null) {
        %>    
            <p><%Object [] item = playerHand.toArray();%></p>
            <div class="playerHand" action="<%=response.encodeURL("playerHand")%>">
                <h3>Players Hand</h3>
                <ul>
                    <%for (int i=0;i<item.length;i++) { %>
                    <li>
                        <% Object card = session.getAttribute("card"+item[i].toString()); %>
                        <%=card%>
                    </li>
                    <% } %>
                </ul>
                <p>Player Total: ${playerTotal}</p>
            </div>            
        <%
            }
        %>
        <%
            if (whoseTurn != null) {
        %>    
            <p><%Object [] item = whoseTurn.toArray();%></p>
            <div style="display: none;" class="card_counter" action="<%=response.encodeURL("whoseTurn")%>">
                <p> Current Turn: 
                    <%=item[0]%>
                </p>
            </div>            
        <%
            }
        %>
        <%
            if (cardCounter != null) {
        %>    
            <p><%Object [] item = cardCounter.toArray();%></p>
            <div style="display: none;" class="card_counter" action="<%=response.encodeURL("cardCounter")%>">
                <p>
                    <%=item[0]%>
                </p>
            </div>            
        <%
            }
        %>
        
        <%-- sets initial button action attributes--%>
        <%
            if (cardCounter == null) {
        %>
            <% request.setAttribute("actionStart", "start;jsessionid="+session.getId());%>
            <% request.setAttribute("actionHit", "move/hit;jsessionid="+session.getId());%>
            <% request.setAttribute("actionStand", "move/stand;jsessionid="+session.getId());%>
            <% request.setAttribute("actionStats", "stats;jsessionid="+session.getId());%>
        <%
            }
        %>
        <p>${possibleMoves}
        <form method="post" action=${actionStart}>
            <input type="hidden" id="whoseTurn" name="whoseTurn" value=${whoseTurn}>
            <input type="hidden" id="game" name="game" value="start">
            <input type="submit"  value="New Game">           
        </form>
        <form method="post" action=${actionHit}>
            <input type="hidden" id="whoseTurn" name="whoseTurn" value=${whoseTurn}>
            <input type="hidden" id="game" name="game" value="start">
            <input type="submit"  value="Hit" ${possibleHit}>           
        </form>
        <form method="post" action=${actionStand}>
            <input type="hidden" id="whoseTurn" name="whoseTurn" value=${whoseTurn}>
            <input type="hidden" id="game" name="game" value="start">
            <input type="submit"  value="Stand" ${possibleStand}>           
        </form>
        <form method="get" action=${actionStats}>
            <input type="hidden" id="stats" name="stats" value="stats">
            <input type="submit"  value="Stats">           
        </form>
            
        <%
        if ("stats".equals(session.getAttribute("stats"))) {
        %>
            <div>
                <h3>STATS</h3>
                <p>Games Played: ${games}</p>
                <p>Games Won: ${wins}</p>
            </div>
        <%
            }
        %>
    </body>
</html>
