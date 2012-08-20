package eu.javaspecialists.deadlock.lab3;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private long lastPrime = 1;

    private TransactionManager manager;

    @Override
    public void init() throws ServletException {
        manager = new TransactionManager();
    }

    @Override
    public void destroy() {
        manager.stop();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Transaction transaction = manager.allocateTransaction();
        try {
            nextPrime();
            resp.getWriter().write("Next prime is " + lastPrime);
        } catch (Exception e) {
            transaction.rollback();
        }
        transaction.commit();
    }

    private void nextPrime() {
        while (true) {
            lastPrime += 2;
            for (int i = 3; i < lastPrime / 2; i += 2) {
                if (lastPrime % i == 0) {
                    break;
                }
            }
            return;
        }
    }
}
