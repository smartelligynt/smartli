package com.st.services.oauth.util;

/**
 * @author mpala
 * This class has various util methods for OAuth URL/Http handling
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.st.services.oauth.OAuthConstants;
import com.st.services.util.Util;

public class URLUtil {

	private static final Logger _logger = Logger.getLogger(URLUtil.class
			.getName());

	private static final Pattern OAUTH_HEADER = Pattern
			.compile("\\s*(\\w*)\\s+(.*)");
	private static final Pattern NVP = Pattern
			.compile("(\\S*)\\s*\\=\\s*\"([^\"]*)\"");
	private static final String AUTH_SCHEME = "OAuth";
	private static final String ENCODING = "UTF-8";
	private static final char PARAM_SEPARATOR = '&';
	private static final char PAIR_SEPARATOR = '=';

	private static final Set<EncodingRule> ENCODING_RULES;

	static {
		Set<EncodingRule> rules = new HashSet<EncodingRule>();
		rules.add(new EncodingRule("*", "%2A"));
		rules.add(new EncodingRule("+", "%20"));
		rules.add(new EncodingRule("%7E", "~"));
		ENCODING_RULES = Collections.unmodifiableSet(rules);
	}

	/**
	 * This method takes in the full OAuth header string and decodes it into
	 * Name value pairs
	 * 
	 * @param String
	 *            header
	 * @return Map<String, String>
	 */
	public static Map<String, String> decodeOAuthHeader(String header) {
		_logger.fine("Decoding OAuth Header");

		Map<String, String> headerValues = new HashMap<String, String>();
		if (header != null) {
			Matcher m = OAUTH_HEADER.matcher(header);
			if (m.matches()) {
				if (AUTH_SCHEME.equalsIgnoreCase(m.group(1))) {
					for (String nvp : m.group(2).split("\\s*,\\s*")) {
						m = NVP.matcher(nvp);
						if (m.matches()) {
							String name = decodePercent(m.group(1));
							String value = decodePercent(m.group(2));
							headerValues.put(name, value);
						}
					}
				}
			}
		}
		return headerValues;
	}

	/**
	 * This method takes in % encoded url String decodes them into normal UTF-8
	 * String
	 * 
	 * @param String
	 * @return String
	 */
	public static String decodePercent(String s) {
		_logger.fine("Decoding % encoded URL");
		try {
			return URLDecoder.decode(s, ENCODING);
		} catch (java.io.UnsupportedEncodingException wow) {
			throw new RuntimeException(wow.getMessage(), wow);
		}
	}

	/**
	 * This method takes in the header attributes as Name Value Pairs and
	 * converts into OAuth Header string
	 * 
	 * @param Map
	 *            <String, String>
	 * @return String
	 */
	public static String encodeOAuthHeader(Map<String, String> entries) {
		_logger.fine("Encoding OAuth Header");
		StringBuffer sb = new StringBuffer();
		sb.append(OAuthConstants.OAUTH_HEADER_NAME).append(" ");
		for (Map.Entry<String, String> entry : entries.entrySet()) {
			if (!Util.isEmpty(entry.getKey())
					&& !Util.isEmpty(entry.getValue())) {
				sb.append(entry.getKey());
				sb.append("=\"");
				sb.append(entry.getValue());
				sb.append("\",");
			}
		}

		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * This method takes Map of form attribute and converts in a String of
	 * request parameters * converts into OAuth Header string
	 * 
	 * @param Map
	 *            <String, String>
	 * @return String
	 */
	public static String doFormUrlEncode(Map<String, String> map) {
		_logger.fine("Encoding Form URL");
		StringBuilder encodedString = new StringBuilder(map.size() * 20);
		for (String key : map.keySet()) {
			if (encodedString.length() > 0) {
				encodedString.append(PARAM_SEPARATOR);
			}

			encodedString.append(percentEncode(key)).append(PAIR_SEPARATOR)
					.append(percentEncode(map.get(key)));
		}
		return encodedString.toString();
	}

	/**
	 * This method takes in a String and encodes with % codes where applicable
	 * 
	 * @param String
	 * @return String
	 */
	public static String percentEncode(String string) {
		_logger.fine("Encoding % to URL");
		try {
			String encoded = URLEncoder.encode(string, ENCODING);
			for (EncodingRule rule : ENCODING_RULES) {
				encoded = rule.apply(encoded);
			}
			return encoded;
		} catch (UnsupportedEncodingException uee) {
			throw OAuthUtil.getOAuthExceptionFromUnknown(uee);
		}
	}

	private static final class EncodingRule {
		private final String ch;
		private final String toCh;

		EncodingRule(String ch, String toCh) {
			this.ch = ch;
			this.toCh = toCh;
		}

		String apply(String string) {
			return string.replace(ch, toCh);
		}
	}

}
