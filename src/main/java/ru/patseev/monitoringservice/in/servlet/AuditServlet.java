package ru.patseev.monitoringservice.in.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ru.patseev.monitoringservice.context.MonitoringApplicationContext;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.operation.manager.OperationManager;

/**
 * Servlet for handling audit-related operations.
 */
@WebServlet("/audits")
@AllArgsConstructor
public class AuditServlet extends HttpServlet {

	/**
	 * The operation manager responsible for managing operations.
	 */
	private final OperationManager operationManager;

	/**
	 * Initializes the servlet by obtaining the OperationManager from the application context.
	 */
	public AuditServlet() {
		this.operationManager = MonitoringApplicationContext.getContext().getAuditOperationManager();
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
