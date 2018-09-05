package club.gorpg.helper.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import club.gorpg.helper.exceptions.TranslateException;
import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.FileMeta.SectionInfo;
import club.gorpg.helper.model.GameListMeta;
import club.gorpg.helper.model.GameListMeta.SingleGameMeta;
import club.gorpg.helper.model.GameMeta;
import club.gorpg.helper.model.TranslateResult;
import club.gorpg.helper.service.IFileTemplateService;
import club.gorpg.helper.service.IGameMetaService;

@Service
public class GameMetaServiceImpl implements IGameMetaService {
	private Logger logger = LoggerFactory.getLogger(GameMetaServiceImpl.class);
	private ObjectMapper om = new ObjectMapper();

	@Autowired
	private ApplicationContext applicationContext;

	private Collection<IFileTemplateService> fileTemplateServices;

	@Value("${game.base.path}")
	private String basePath;

	public void init(String gameId) throws TranslateException {
		// 准备原文文件夹
		File gameForeignFolderFile = null;
		try {
			gameForeignFolderFile = ResourceUtils.getFile(basePath + gameId + "/foreign/");
		} catch (FileNotFoundException e) {
			throw new TranslateException("初始化失败，原文文件夹缺失！");
		}
		final Path gameForeignFolder = Paths.get(gameForeignFolderFile.getAbsolutePath());
		final Path gameBaseFolder = gameForeignFolder.getParent();
		final Path gameWWWFolder = gameForeignFolder.resolve("www");

		// 准备翻译文件夹
		final Path gameChineseFolder = gameBaseFolder.resolve("chinese");
		if (!Files.exists(gameChineseFolder)) {
			try {
				Files.createDirectory(gameChineseFolder);
			} catch (IOException e) {
				throw new TranslateException("初始化失败，翻译文件夹缺失且无法创建！");
			}
		}

		// 准备
		Path gameMetaFile = gameBaseFolder.resolve("game.json");
		Path gameSimpleMetaFile = gameBaseFolder.resolve("game_simple.json");
		if (Files.isDirectory(gameMetaFile)) {
			throw new TranslateException("初始化失败，进度文件被其他文件夹占据！");
		}

		// meta文件
		final GameMeta gameMeta;
		if (Files.exists(gameMetaFile) && Files.isReadable(gameMetaFile)) {
			try {
				// 已有meta文件，读取meta文件
				gameMeta = om.readValue(gameMetaFile.toFile(), GameMeta.class);
				gameMeta.setId(gameId);
				gameMeta.setTree(new ArrayList<>());

			} catch (IOException e) {
				throw new TranslateException("初始化失败，进度文件读取失败！");
			}
		} else {
			try {
				// 没有meta文件，根据 game_list.json 生成新meta
				GameListMeta gameListMeta = om.readValue(ResourceUtils.getFile(basePath + "game_list.json"),
						GameListMeta.class);
				SingleGameMeta sgm = gameListMeta.getGame().get(gameId);

				gameMeta = new GameMeta();
				gameMeta.setId(gameId);
				gameMeta.setFileMap(new HashMap<>());
				gameMeta.setTree(new ArrayList<>());
				gameMeta.setCname(sgm.getCname());
				gameMeta.setFname(sgm.getFname());
			} catch (JsonParseException e) {
				throw new TranslateException("初始化失败，基础文件格式异常，请联系 冷凝沙漠 QQ:25817014！");
			} catch (JsonMappingException e) {
				throw new TranslateException("初始化失败，基础文件格式异常，请联系 冷凝沙漠 QQ:25817014！");
			} catch (FileNotFoundException e) {
				throw new TranslateException("初始化失败，基础文件不存在！");
			} catch (IOException e) {
				throw new TranslateException("初始化失败，基础文件读取失败！");
			}
		}

		try {
			// 首轮文件扫描
			Files.walk(gameForeignFolder).forEach(path -> {
				Path chinesePath = gameChineseFolder.resolve(gameForeignFolder.relativize(path));
				Map<String, FileMeta> fileMap = gameMeta.getFileMap();
				if (Files.isDirectory(path)) {
					// 创建对应目录
					if (Files.exists(chinesePath)) {
						return;
					}
					try {
						Files.createDirectory(chinesePath);
					} catch (IOException e) {
						logger.error("初始化复制文件异常,源目录" + path.toString() + ",目标目录" + chinesePath.toString(), e);
						return;
					}

				} else {
					// 不存在则复制文件
					if (!Files.exists(chinesePath)) {
						try {
							Files.copy(path, chinesePath);
						} catch (IOException e) {
							logger.error("初始化复制文件异常,源文件" + path.toString() + ",目标文件" + chinesePath.toString(), e);
							return;
						}
					}
					// 逐个文件寻求处理器模板。
					for (IFileTemplateService service : fileTemplateServices) {
						String fileName = gameWWWFolder.relativize(path).toString().replaceAll("\\\\", "/");
						List<FileMeta> fms = service.handle(gameMeta, fileName, path, chinesePath);
						if (fms != null) {
							fms.forEach(f -> {
								f.recount();
								fileMap.put(f.getFileName(), f);
							});
						}
					}
				}
			});
			// 二轮扫描
			Files.walk(gameForeignFolder).sorted().forEach(path -> {
				if (!Files.isDirectory(path)) {
					Path chinesePath = gameChineseFolder.resolve(gameForeignFolder.relativize(path));
					for (IFileTemplateService service : fileTemplateServices) {
						String fileName = gameWWWFolder.relativize(path).toString().replaceAll("\\\\", "/");
						service.aferHandle(gameMeta, fileName, path, chinesePath);
					}
				}
			});
			

		} catch (IOException e) {
			throw new TranslateException("初始化失败，进度文件被其他文件夹占据！");
		}

		gameMeta.recount();

		final Path gameMetaFolder = gameBaseFolder.resolve("meta");
		for (FileMeta fm : gameMeta.getFileMap().values()) {
			try {
				if (fm == null || fm.getFileName() == null) {
					continue;
				}
				Path metaFile = gameMetaFolder.resolve(fm.getFileName());
				File parent = metaFile.getParent().toFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}

				om.writerWithDefaultPrettyPrinter().writeValue(metaFile.toFile(), fm);
			} catch (IOException e) {
				throw new TranslateException("初始化失败，进度文件写入失败！");
			} catch (NullPointerException e) {
				throw new TranslateException("初始化失败，进度文件写入失败！");
			}
			fm.setSectionMap(null);
			fm.setSections(null);
		}

		try {
			om.writerWithDefaultPrettyPrinter().writeValue(gameSimpleMetaFile.toFile(), gameMeta);
		} catch (IOException e) {
			throw new TranslateException("初始化失败，进度简化文件写入失败！");
		}
	}

	public TranslateResult translate(String gameId, String fileName, String path, String value)
			throws TranslateException {

		File f = null;

		try {
			f = ResourceUtils.getFile(basePath + gameId + "/game_simple.json");
		} catch (FileNotFoundException e2) {
			throw new TranslateException("翻译失败，进度文件缺失！");
		}
		Path gameSimpleMetaFile = Paths.get(f.getAbsolutePath()).resolveSibling("game_simple.json");
		File fileMetaFile = gameSimpleMetaFile.resolveSibling("meta").resolve(fileName).toFile();
		try {

			GameMeta gm = om.readValue(f, GameMeta.class);
			FileMeta fm = om.readValue(fileMetaFile, FileMeta.class);
			if (fm == null || fm.getSectionMap() == null || fm.getSections() == null) {
				throw new TranslateException("翻译失败，进度文件中找不到当前翻译文件！");
			}

			SectionInfo section = fm.getSectionMap().get(path);
			if (section == null) {
				throw new TranslateException("翻译失败，进度文件中找不到当前翻译点！");
			}
			section.setTransContent(value);
			section.setTransWords(StringUtils.length(value));
			section.setTrans(true);

			fm.recount();

			gm.getFileMap().put(fileName, fm);
			gm.recount();

			om.writerWithDefaultPrettyPrinter().writeValue(fileMetaFile, fm);

			for (FileMeta m : gm.getFileMap().values()) {
				m.setSectionMap(null);
			}

			om.writerWithDefaultPrettyPrinter().writeValue(gameSimpleMetaFile.toFile(), gm);

			return new TranslateResult(gm.getAllTotal(), gm.getAllForeignWords(), gm.getAllTransWords(),
					gm.getAllTrans(), fm.getTotal(), fm.getTrans(), fm.getForeignWords(), fm.getTransWords());
		} catch (IOException e) {
			throw new TranslateException("翻译失败，进度文件内容异常！");
		}

	}

	@PostConstruct
	private void init() {
		fileTemplateServices = applicationContext.getBeansOfType(IFileTemplateService.class).values();
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
}
