package club.gorpg.helper.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;
import club.gorpg.helper.service.IFileTemplateService;

@Service
public class JsonFileTemplateServiceImpl implements IFileTemplateService {
	private Logger logger = LoggerFactory.getLogger(GameMetaServiceImpl.class);
	private ObjectMapper om = new ObjectMapper();
	@Autowired
	private ApplicationContext applicationContext;
	private Collection<IJsonFileService> jsonFileServices;

	public List<FileMeta> handle(GameMeta gm, String fileName, Path path, Path chinesePath) {
		if (!path.toString().endsWith(".json")) {
			return null;
		}
		for (IJsonFileService jsonFileService : jsonFileServices) {
			if (jsonFileService.accept(fileName)) {
				try {
					JsonNode tn = om.readTree(path.toFile());
					File chineseFile = chinesePath.toFile();
					DocumentContext chinese = JsonPath.parse(chineseFile);
					List<FileMeta> fms = jsonFileService.getFileMeta(gm, fileName, tn, chinese);
					if (fms == null || fms.isEmpty()) {
						logger.error("初始化文件异常，处理类表示接受文件，但缺失处理结果，文件名：" + fileName + "， 处理类："
								+ jsonFileService.getClass().getName());
						return null;
					}

					TreeNode chineseNode = om.readTree(chinese.jsonString());
					om.writerWithDefaultPrettyPrinter().writeValue(chineseFile, chineseNode);
					fms.forEach(f -> f.recount());
					return fms;
				} catch (IOException e) {
					logger.error("初始化文件异常，文件名：" + fileName + "， 处理类：" + jsonFileService.getClass().getName(), e);
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public void aferHandle(GameMeta gm, String fileName, Path path, Path chinesePath) {
		if (!path.toString().endsWith(".json")) {
			return;
		}
		for (IJsonFileService jsonFileService : jsonFileServices) {
			if (jsonFileService.accept(fileName)) {
				try {
					JsonNode tn = om.readTree(path.toFile());
					jsonFileService.afterHandle(gm, tn);
				} catch (IOException e) {
					logger.error("初始化文件异常，文件名：" + fileName + "， 处理类：" + jsonFileService.getClass().getName(), e);
					return;
				}
			}
		}
	}

	@PostConstruct
	private void init() {
		jsonFileServices = applicationContext.getBeansOfType(IJsonFileService.class).values();
	}
}
