package club.gorpg.helper.service;

import java.nio.file.Path;
import java.util.List;

import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;

/**
 * 文件模板
 * 
 * @author 周宁
 *
 */
public interface IFileTemplateService {

	public List<FileMeta> handle(String fileName, Path path, Path chinesePath);

	public void aferHandle(GameMeta gm, String fileName, Path path);
}
