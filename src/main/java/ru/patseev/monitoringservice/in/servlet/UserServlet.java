package ru.patseev.monitoringservice.in.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.context.MonitoringApplicationContext;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.operation.manager.OperationManager;

/**
 * The UserServlet class handles HTTP POST requests related to user operations.
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

	/**
	 * The operation manager responsible for choosing the appropriate operation handler.
	 */
	private final OperationManager operationManager;

	/**
	 * Constructs a UserServlet instance with the operation manager obtained from the application context.
	 */
	public UserServlet() {
		this.operationManager = MonitoringApplicationContext.getContext().getUserOperationManager();
	}

	/**
	 * Constructs a UserServlet object with the provided OperationManager.
	 *
	 * @param operationManager The OperationManager instance to be used by the UserServlet.
	 */
	public UserServlet(OperationManager operationManager) {
		this.operationManager = operationManager;
	}

	/**
	 * Handles HTTP POST requests.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String operationName = req.getParameter("operation");
		OperationHandler operationHandler = operationManager.chooseOperation(operationName);
		operationHandler.handleRequest(req, resp);
	}
}