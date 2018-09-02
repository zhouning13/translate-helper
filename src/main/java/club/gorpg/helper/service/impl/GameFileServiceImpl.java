package club.gorpg.helper.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import club.gorpg.helper.exceptions.TranslateException;
import club.gorpg.helper.service.IGameFileService;

@Service
public class GameFileServiceImpl implements IGameFileService {
	@Value("${game.base.path}")
	private String basePath;

	private ObjectMapper om = new ObjectMapper();

	public void translate(String gameId, String fileName, String path, String value) throws TranslateException {
		File f = null;

		try {
			f = ResourceUtils.getFile(basePath + gameId + "/chinese/www/" + fileName);

		} catch (FileNotFoundException e2) {
			throw new TranslateException("翻译失败，被翻译文件缺失！");
		}

		String json = null;
		try (FileReader fr = new FileReader(f)) {
			json = IOUtils.toString(fr);
		} catch (IOException e) {
			throw new TranslateException("翻译失败，被翻译文件读取失败！");
		}

		try {

			JsonPath jsonPath = JsonPath.compile(path);
			DocumentContext dc = JsonPath.parse(json);
			dc.set(jsonPath, value);
			TreeNode tn = om.readTree(dc.jsonString());
			om.writerWithDefaultPrettyPrinter().writeValue(f, tn);

		} catch (FileNotFoundException e1) {
			throw new TranslateException("翻译失败，被翻译文件缺失！");
		} catch (IOException e) {
			throw new TranslateException("翻译失败，被翻译文件读取失败！");
		}

	}

}
