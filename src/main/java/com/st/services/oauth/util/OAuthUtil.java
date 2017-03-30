package com.st.services.oauth.util;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.cxf.common.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class OAuthUtil {

	private static final Logger _logger = Logger.getLogger(OAuthUtil.class
			.getName());
	private static final String errorCode = "errorCode";
	private static final String errorMessage = "errorMessage";
	private static final String errorDescription = "errorDescription";

	public static OAuthException getOAuthExceptionFromUnknown(Exception e) {
		_logger.fine("Getting OAuth Exception for Unknown");
		AuthError error = new AuthError(AuthServiceErrors.UNKNOWN_FAILUE.getCode(), AuthServiceErrors.UNKNOWN_FAILUE.getMessage());
		error.setDescription(getExceptionStack(e));
		return new OAuthException(
				error, Status.INTERNAL_SERVER_ERROR);
	}

	public static String getExceptionStack(Exception e) {
		_logger.fine("Getting Exception Stack");
		StringBuilder sb = new StringBuilder();
		sb.append(e.toString());
		for (StackTraceElement se : e.getStackTrace()) {
			sb.append("\n");
			sb.append(se.toString());
		}
		return sb.toString();
	}




	public static Map<String, String> readXmlKeyValuesWithXpath(String body,
			Map<String, String> attributesXPath)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		_logger.info("Reading Xml Key values Using XPath");

		Map<String, String> responseMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(body)) {
			return responseMap;
		}

		String value = null;
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = domFactory.newDocumentBuilder();

		Document document = builder.parse(new InputSource(
				new StringReader(body)));

		XPath xpath = XPathFactory.newInstance().newXPath();

		for (Map.Entry<String, String> entry : attributesXPath.entrySet()) {
			value = xpath.evaluate(entry.getValue(), document);
			if (!StringUtils.isEmpty(value)) {
				responseMap.put(entry.getKey(), value);

			}
		}

		return responseMap;
	}

	public static OAuthException getOAuthExceptionForSQLException(
			SQLException sqle) {
		_logger.fine("Building OAuthException from SQLException");
		AuthError tpa = new AuthError(AuthServiceErrors.DB_ACTIVIT_FAILURE.getCode(), AuthServiceErrors.DB_ACTIVIT_FAILURE.getMessage());
		tpa.setDescription(sqle.getMessage());
		return new OAuthException(tpa, Status.INTERNAL_SERVER_ERROR);
	}

	public static Date getFutureExpirationDate() {

		_logger.fine("Getting future expiration date");
		Calendar cal = Calendar.getInstance();

		if (cal.after(Calendar.getInstance())) {
			return cal.getTime();
		}
		cal.set(2999, 11, 31, 23, 59);

		return cal.getTime();
	}

	public static Map<String, String> getMapOfError(AuthError error) {
		_logger.fine("Getting Error Map");
		if (error == null) {
			return null;
		}
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put(errorCode, error.getCode());
		errorMap.put(errorMessage, error.getMessage());
		errorMap.put(errorDescription, error.getDescription());

		return errorMap;
	}

	public static Map<String, String> getMapWithUppercaseKeys(
			MultivaluedMap<String, String> params) {
		_logger.fine("Getting Map converting keys to uppercase");
		Map<String, String> changedParams = new HashMap<String, String>();
		if (params == null || params.isEmpty()) {
			return changedParams;
		}
		for (String key : params.keySet()) {
			changedParams.put(key.toUpperCase(), params.getFirst(key));
		}

		return changedParams;
	}

	public static Map<String, String> getMapWithLowercaseKeys(
			MultivaluedMap<String, String> params) {
		_logger.fine("Getting Map converting keys to lowercase");
		Map<String, String> changedParams = new HashMap<String, String>();
		if (params == null || params.isEmpty()) {
			return changedParams;
		}
		for (String key : params.keySet()) {
			changedParams.put(key.toLowerCase(), params.getFirst(key));
		}

		return changedParams;
	}
}
