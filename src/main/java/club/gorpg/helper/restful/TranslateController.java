package club.gorpg.helper.restful;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import club.gorpg.helper.exceptions.TranslateException;
import club.gorpg.helper.model.RestResponse;
import club.gorpg.helper.model.TranslateResult;
import club.gorpg.helper.service.IGameFileService;
import club.gorpg.helper.service.IGameMetaService;

@RestController()
@RequestMapping(value = "/api/v1/")
public class TranslateController {
	@Autowired
	private IGameFileService gameFileService;
	@Autowired
	private IGameMetaService gameMetaService;

	/**
	 * 
	 * @param gameId
	 */
	@RequestMapping(value = "init")
	@ResponseBody
	public RestResponse<Object> init(// 游戏id
			@RequestParam(value = "gameId") String gameId) {
		try {
			gameMetaService.init(gameId);
		} catch (TranslateException e) {
			return new RestResponse<>(500, e.getMessage());
		}
		return new RestResponse<>(200, "初始化成功");
	}

	/**
	 * 
	 * @param gameId
	 *            游戏id
	 * @param fileName
	 *            文件名
	 * @param path
	 *            json路径
	 * @param value
	 *            值
	 * @throws IOException
	 */
	@RequestMapping(value = "translate")
	@ResponseBody
	public RestResponse<TranslateResult> translate(
			// 游戏id
			@RequestParam(value = "gameId") String gameId,
			// 文件名，相对于/www/目录的文件名，形如data/Actor.json，针对的是因为体积过大而拆解的meta文件的名称
			@RequestParam(value = "fileName") String fileName,
			// 文件名，相对于/www/目录的文件名，针对的是原始的json文件名。
			@RequestParam(value = "sourceName") String sourceName,
			// json path,要更新的字段相对于json文件的jsonpath
			@RequestParam(value = "path") String path,
			//
			@RequestParam(value = "value") String value) {
		try {
			gameFileService.translate(gameId, sourceName, path, value);
			TranslateResult result = gameMetaService.translate(gameId, fileName, path, value);
			return new RestResponse<TranslateResult>(200, "翻译成功", result);
		} catch (TranslateException e) {
			return new RestResponse<>(500, e.getMessage());
		}

	}
}
