package com.epam.rft.atsy.web.messageresolution;

public interface MessageSourceRepresenationService {

	boolean isSupportedLocale(String locale);

	String getLocalePropertiesAsString(String string);

}
