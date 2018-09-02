package club.gorpg.helper.service;

import club.gorpg.helper.exceptions.TranslateException;

public interface IGameFileService {
	public void translate(String gameId, String fileName, String path, String value) throws TranslateException;
}
