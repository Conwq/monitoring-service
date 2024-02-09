package ru.patseev.monitoringservice.in.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.context.MonitoringApplicationContext;
import ru.patseev.monitoringservice.in.operation.OperationHandler;
import ru.patseev.monitoringservice.manager.OperationManager;

/**
 * The MeterServlet class handles HTTP GET and POST requests related to meter operations.
 */
@WebServlet("/meters")
public class MeterServlet extends HttpServlet {

	/** The name of the parameter indicating the operation to perform. */
	private static final String OPERATION_PARAM = "operation";

	/** The operation manager responsible for choosing the appropriate operation handler. */
	private final OperationManager operationManager;

	/**
	 * Constructs a MeterServlet instance with the operation manager obtained from the application context.
	 */
	public MeterServlet() {
		this.operationManager = MonitoringApplicationContext.getContext().getOperationManager();
	}

	/**
	 * Initializes the servlet with the provided OperationManager.
	 *
	 * @param operationManager The OperationManager to be used by the servlet.
	 */
	public MeterServlet(OperationManager operationManager) {
		this.operationManager = operationManager;
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