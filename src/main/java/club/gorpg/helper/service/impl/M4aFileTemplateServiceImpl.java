package club.gorpg.helper.service.impl;

import org.springframework.stereotype.Service;

@Service
public class M4aFileTemplateServiceImpl extends AbstractDecryptFileTemplateServiceImpl {

	protected String getAcceptableSurffix() {
		return ".rpgmvm";
	}

	protected String getChangedSurffix() {
		return ".m4a";
	}
}
