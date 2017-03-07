package com.st.services.oauth.util;

import java.util.Comparator;
import java.util.Date;

import com.st.services.oauth.token.OAuthToken;

public class TokenSortComparatorByIssueDate implements
Comparator<OAuthToken> {

public int compare(final OAuthToken ot1, final OAuthToken ot2) {

final Date d1 = (ot1.getIssueDate() == null ? new Date() : ot1
		.getIssueDate());
final Date d2 = (ot2.getIssueDate() == null ? new Date() : ot2
		.getIssueDate());
return -d1.compareTo(d2);
}
}
