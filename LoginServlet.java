// public class LoginServlet {
    
// }
// import jakarta.servlet.*;
// import jakarta.servlet.http.*;
// import java.io.IOException;

// public class LoginServlet extends HttpServlet {
//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {

//         String username = request.getParameter("username");
//         String password = request.getParameter("password");

//         BankAccount account = BankAccount.login(username, password);
//         if (account != null) {
//             request.getSession().setAttribute("account", account);
//             response.sendRedirect("dashboard.html"); // redirect to dashboard
//         } else {
//             response.getWriter().write("Invalid username or password!");
//         }
//     }
// }
