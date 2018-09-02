package club.gorpg.helper.service.impl;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;

public interface IJsonFileService {
	public boolean accept(String fileName);

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn);

	public void afterHandle(GameMeta gm, JsonNode tn);
}
