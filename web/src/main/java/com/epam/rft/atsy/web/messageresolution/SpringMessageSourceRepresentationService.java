package com.epam.rft.atsy.web.messageresolution;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringMessageSourceRepresentationService implements MessageSourceRepresenationService {

	private AtsyReloadableResourceBundleMessageSource internalMessageSource;

	@Autowired
	public SpringMessageSourceRepresentationService(AtsyReloadableResourceBundleMessageSource internalMessageSource) {
		this.internalMessageSource = internalMessageSource;
	}

	@Override
	public boolean isSupportedLocale(String locale) {
		return this.internalMessageSource.isSupportedLocale(new Locale(locale));
	}

	@Override
	public String getLocalePropertiesAsString(String string) {
		return null;
	}

}
