package club.gorpg.helper.service.impl;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.DocumentContext;

import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;

public interface IJsonFileService {
	public boolean accept(String fileName);

	public List<FileMeta> getFileMeta(GameMeta gm, String fileName, JsonNode tn, DocumentContext chinese);

	public void afterHandle(GameMeta gm, JsonNode tn);
}
