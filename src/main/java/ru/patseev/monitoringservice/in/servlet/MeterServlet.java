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
 * The MeterServlet class handles HTTP GET and POST requests related to meter operations.
 */
@WebServlet("/meters")
@AllArgsConstructor
public class MeterServlet extends HttpServlet {

	/** The name of the parameter indicating the operation to perform. */
	private static final String OPERATION_PARAM = "operation";

	/** The operation manager responsible for choosing the appropriate operation handler. */
	private final OperationManager operationManager;

	/**
	 * Constructs a MeterServlet instance with the operation manager obtained from the application context.
	 */
	public MeterServlet() {
		this.operationManager = MonitoringApplicationContext.getContext().getMeterOperationManager();
	}

	/**
	 * Handles HTTP GET requests.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String operationName = req.getParameter(OPERATION_PARAM);
		OperationHandler operationHandler = operationManager.chooseOperation(operationName);
		operationHandler.handleRequest(req, resp);
	}

	/**
	 * Handles HTTP POST requests.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String operationName = req.getParameter(OPERATION_PARAM);
		OperationHandler operationHandler = operationManager.chooseOperation(operationName);
		operationHandler.handleRequest(req, resp);
	}
}