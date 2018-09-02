package club.gorpg.helper.service;

import java.nio.file.Path;
import java.util.List;

import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;

public interface IFileTemplateService {

	public List<FileMeta> handle(String fileName, Path path);

	public void aferHandle(GameMeta gm, String fileName, Path path);
}
