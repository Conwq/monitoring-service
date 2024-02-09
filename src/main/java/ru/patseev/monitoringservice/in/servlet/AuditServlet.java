package ru.patseev.monitoringservice.in.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.context.MonitoringApplicationContext;
import ru.patseev.monitoringservice.in.operation.OperationHandler;
import ru.patseev.monitoringservice.manager.OperationManager;

/**
 * Servlet for handling audit-related operations.
 */
@WebServlet("/audits")
public class AuditServlet extends HttpServlet {

	/**
	 * The operation manager responsible for managing operations.
	 */
	private final OperationManager operationManager;

	/**
	 * Initializes the servlet by obtaining the OperationManager from the application context.
	 */
	public AuditServlet() {
		this.operationManager = MonitoringApplicationContext.getContext().getOperationManager();
	}

	/**
	 * Initializes the servlet with the provided OperationManager.
	 *
	 * @param operationManager The OperationManager to be used by the servlet.
	 */
	public AuditServlet(OperationManager operationManager) {
		this.operationManager = operationManager;
	}

	/**
	 * Handles GET requests by delegating the request to the appropriate operation handler.
	 *
	 * @param req  The HttpServletRequest object representing the HTTP request.
	 * @param resp The HttpServletResponse object representing the HTTP response.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String operationName = req.getParameter("operation");
		OperationHandler operationHandler = operationManager.chooseOperation(operationName);
		operationHandler.handleRequest(req, resp);
	}
}
