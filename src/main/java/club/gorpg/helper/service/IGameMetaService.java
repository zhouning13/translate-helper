package club.gorpg.helper.service;

import club.gorpg.helper.exceptions.TranslateException;
import club.gorpg.helper.model.TranslateResult;

/**
 * 游戏元数据处理
 * 
 * @author 周宁
 *
 */
public interface IGameMetaService {

	/**
	 * 初始化，解析foreign文件，生成chinese文件，生成meta文件
	 * 
	 * @param gameId
	 * @throws TranslateException
	 */
	public void init(String gameId) throws TranslateException;

	/**
	 * 翻译，将内容写到meta索引文件中
	 * 
	 * @param gameId
	 * @param fileName
	 * @param path
	 * @param value
	 * @return
	 * @throws TranslateException
	 */
	public TranslateResult translate(String gameId, String fileName, String path, String value)
			throws TranslateException;
}
