package club.gorpg.helper.service.impl;

import org.springframework.stereotype.Service;

@Service
public class OggFileTemplateServiceImpl extends AbstractDecryptFileTemplateServiceImpl {

	protected String getAcceptableSurffix() {
		return ".rpgmvo";
	}

	protected String getChangedSurffix() {
		return ".ogg";
	}
}
