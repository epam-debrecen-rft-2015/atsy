package com.epam.rft.atsy.web.messageresolution;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class AtsyReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

	public boolean isSupportedLocale(Locale locale) {
		return false;
	}
	
}
