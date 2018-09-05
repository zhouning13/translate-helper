package club.gorpg.helper.service.impl;

import org.springframework.stereotype.Service;

@Service
public class PngFileTemplateServiceImpl extends AbstractDecryptFileTemplateServiceImpl {

	protected String getAcceptableSurffix() {
		return ".rpgmvp";
	}

	protected String getChangedSurffix() {
		return ".png";
	}
}
