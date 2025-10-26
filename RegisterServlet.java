// public class RegisterServlet {
    
// }

// import jakarta.servlet.*;
// import jakarta.servlet.http.*;
// import java.io.IOException;

// public class RegisterServlet extends HttpServlet {
//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {

//         String firstName = request.getParameter("firstName");
//         String lastName = request.getParameter("lastName");
//         String name = firstName + " " + lastName;
//         String username = request.getParameter("username");
//         String password = request.getParameter("password");
//         double balance = 0;

//         // By default, create Savings account
//         BankAccount account = new SavingsAccount(name, username, password, balance);
//         if (account.saveToDB()) {
//             response.getWriter().write("Account created successfully!");
//         } else {
//             response.getWriter().write("Error creating account. Username may already exist.");
//         }
//     }
// }
