package club.gorpg.helper.service;

import club.gorpg.helper.exceptions.TranslateException;

public interface IGameFileService {
	/**
	 * 翻译，将内容写到翻译后的chinese文件中
	 * 
	 * @param gameId
	 * @param fileName
	 * @param path
	 * @param value
	 * @throws TranslateException 翻译异常
	 */
	public void translate(String gameId, String fileName, String path, String value) throws TranslateException;
}
