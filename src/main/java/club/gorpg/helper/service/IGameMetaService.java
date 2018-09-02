package club.gorpg.helper.service;

import club.gorpg.helper.exceptions.TranslateException;
import club.gorpg.helper.model.TranslateResult;

public interface IGameMetaService {
	public void init(String gameId) throws TranslateException;

	public TranslateResult translate(String gameId, String fileName, String path, String value)
			throws TranslateException;
}
